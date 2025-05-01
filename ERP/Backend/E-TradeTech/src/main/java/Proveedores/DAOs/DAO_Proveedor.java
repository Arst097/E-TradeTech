/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.ListaContactos;
import Proveedores.Modelos.Ofertas;
import Proveedores.Modelos.Proveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Proveedor implements Serializable {

    public DAO_Proveedor(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws RollbackFailureException, Exception {
        if (proveedor.getOfertasCollection() == null) {
            proveedor.setOfertasCollection(new ArrayList<Ofertas>());
        }
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            ListaContactos listaContactosID = proveedor.getListaContactosID();
            if (listaContactosID != null) {
                listaContactosID = em.getReference(listaContactosID.getClass(), listaContactosID.getListaContactosID());
                proveedor.setListaContactosID(listaContactosID);
            }
            Collection<Ofertas> attachedOfertasCollection = new ArrayList<Ofertas>();
            for (Ofertas ofertasCollectionOfertasToAttach : proveedor.getOfertasCollection()) {
                ofertasCollectionOfertasToAttach = em.getReference(ofertasCollectionOfertasToAttach.getClass(), ofertasCollectionOfertasToAttach.getOfertasID());
                attachedOfertasCollection.add(ofertasCollectionOfertasToAttach);
            }
            proveedor.setOfertasCollection(attachedOfertasCollection);
            em.persist(proveedor);
            if (listaContactosID != null) {
                listaContactosID.getProveedorCollection().add(proveedor);
                listaContactosID = em.merge(listaContactosID);
            }
            for (Ofertas ofertasCollectionOfertas : proveedor.getOfertasCollection()) {
                Proveedor oldProveedorIDOfOfertasCollectionOfertas = ofertasCollectionOfertas.getProveedorID();
                ofertasCollectionOfertas.setProveedorID(proveedor);
                ofertasCollectionOfertas = em.merge(ofertasCollectionOfertas);
                if (oldProveedorIDOfOfertasCollectionOfertas != null) {
                    oldProveedorIDOfOfertasCollectionOfertas.getOfertasCollection().remove(ofertasCollectionOfertas);
                    oldProveedorIDOfOfertasCollectionOfertas = em.merge(oldProveedorIDOfOfertasCollectionOfertas);
                }
            }
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProveedorID());
            ListaContactos listaContactosIDOld = persistentProveedor.getListaContactosID();
            ListaContactos listaContactosIDNew = proveedor.getListaContactosID();
            Collection<Ofertas> ofertasCollectionOld = persistentProveedor.getOfertasCollection();
            Collection<Ofertas> ofertasCollectionNew = proveedor.getOfertasCollection();
            List<String> illegalOrphanMessages = null;
            for (Ofertas ofertasCollectionOldOfertas : ofertasCollectionOld) {
                if (!ofertasCollectionNew.contains(ofertasCollectionOldOfertas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ofertas " + ofertasCollectionOldOfertas + " since its proveedorID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (listaContactosIDNew != null) {
                listaContactosIDNew = em.getReference(listaContactosIDNew.getClass(), listaContactosIDNew.getListaContactosID());
                proveedor.setListaContactosID(listaContactosIDNew);
            }
            Collection<Ofertas> attachedOfertasCollectionNew = new ArrayList<Ofertas>();
            for (Ofertas ofertasCollectionNewOfertasToAttach : ofertasCollectionNew) {
                ofertasCollectionNewOfertasToAttach = em.getReference(ofertasCollectionNewOfertasToAttach.getClass(), ofertasCollectionNewOfertasToAttach.getOfertasID());
                attachedOfertasCollectionNew.add(ofertasCollectionNewOfertasToAttach);
            }
            ofertasCollectionNew = attachedOfertasCollectionNew;
            proveedor.setOfertasCollection(ofertasCollectionNew);
            proveedor = em.merge(proveedor);
            if (listaContactosIDOld != null && !listaContactosIDOld.equals(listaContactosIDNew)) {
                listaContactosIDOld.getProveedorCollection().remove(proveedor);
                listaContactosIDOld = em.merge(listaContactosIDOld);
            }
            if (listaContactosIDNew != null && !listaContactosIDNew.equals(listaContactosIDOld)) {
                listaContactosIDNew.getProveedorCollection().add(proveedor);
                listaContactosIDNew = em.merge(listaContactosIDNew);
            }
            for (Ofertas ofertasCollectionNewOfertas : ofertasCollectionNew) {
                if (!ofertasCollectionOld.contains(ofertasCollectionNewOfertas)) {
                    Proveedor oldProveedorIDOfOfertasCollectionNewOfertas = ofertasCollectionNewOfertas.getProveedorID();
                    ofertasCollectionNewOfertas.setProveedorID(proveedor);
                    ofertasCollectionNewOfertas = em.merge(ofertasCollectionNewOfertas);
                    if (oldProveedorIDOfOfertasCollectionNewOfertas != null && !oldProveedorIDOfOfertasCollectionNewOfertas.equals(proveedor)) {
                        oldProveedorIDOfOfertasCollectionNewOfertas.getOfertasCollection().remove(ofertasCollectionNewOfertas);
                        oldProveedorIDOfOfertasCollectionNewOfertas = em.merge(oldProveedorIDOfOfertasCollectionNewOfertas);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getProveedorID();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getProveedorID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ofertas> ofertasCollectionOrphanCheck = proveedor.getOfertasCollection();
            for (Ofertas ofertasCollectionOrphanCheckOfertas : ofertasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Ofertas " + ofertasCollectionOrphanCheckOfertas + " in its ofertasCollection field has a non-nullable proveedorID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ListaContactos listaContactosID = proveedor.getListaContactosID();
            if (listaContactosID != null) {
                listaContactosID.getProveedorCollection().remove(proveedor);
                listaContactosID = em.merge(listaContactosID);
            }
            em.remove(proveedor);
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
