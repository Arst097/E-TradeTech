/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.RollbackFailureException;
import Proveedores.Modelos.ListaContactos;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.UsuarioCompras;
import java.util.ArrayList;
import java.util.Collection;
import Proveedores.Modelos.Proveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_ListaContactos implements Serializable {

    public DAO_ListaContactos(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaContactos listaContactos) throws RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        if (listaContactos.getUsuarioComprasCollection() == null) {
            listaContactos.setUsuarioComprasCollection(new ArrayList<UsuarioCompras>());
        }
        if (listaContactos.getProveedorCollection() == null) {
            listaContactos.setProveedorCollection(new ArrayList<Proveedor>());
        }

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            em = getEntityManager();
            Collection<UsuarioCompras> attachedUsuarioComprasCollection = new ArrayList<UsuarioCompras>();
            for (UsuarioCompras usuarioComprasCollectionUsuarioComprasToAttach : listaContactos.getUsuarioComprasCollection()) {
                usuarioComprasCollectionUsuarioComprasToAttach = em.getReference(usuarioComprasCollectionUsuarioComprasToAttach.getClass(), usuarioComprasCollectionUsuarioComprasToAttach.getUsuarioComprasID());
                attachedUsuarioComprasCollection.add(usuarioComprasCollectionUsuarioComprasToAttach);
            }
            listaContactos.setUsuarioComprasCollection(attachedUsuarioComprasCollection);
            Collection<Proveedor> attachedProveedorCollection = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionProveedorToAttach : listaContactos.getProveedorCollection()) {
                proveedorCollectionProveedorToAttach = em.getReference(proveedorCollectionProveedorToAttach.getClass(), proveedorCollectionProveedorToAttach.getProveedorID());
                attachedProveedorCollection.add(proveedorCollectionProveedorToAttach);
            }
            listaContactos.setProveedorCollection(attachedProveedorCollection);
            em.persist(listaContactos);
            for (UsuarioCompras usuarioComprasCollectionUsuarioCompras : listaContactos.getUsuarioComprasCollection()) {
                ListaContactos oldListaContactosIDOfUsuarioComprasCollectionUsuarioCompras = usuarioComprasCollectionUsuarioCompras.getListaContactosID();
                usuarioComprasCollectionUsuarioCompras.setListaContactosID(listaContactos);
                usuarioComprasCollectionUsuarioCompras = em.merge(usuarioComprasCollectionUsuarioCompras);
                if (oldListaContactosIDOfUsuarioComprasCollectionUsuarioCompras != null) {
                    oldListaContactosIDOfUsuarioComprasCollectionUsuarioCompras.getUsuarioComprasCollection().remove(usuarioComprasCollectionUsuarioCompras);
                    oldListaContactosIDOfUsuarioComprasCollectionUsuarioCompras = em.merge(oldListaContactosIDOfUsuarioComprasCollectionUsuarioCompras);
                }
            }
            for (Proveedor proveedorCollectionProveedor : listaContactos.getProveedorCollection()) {
                ListaContactos oldListaContactosIDOfProveedorCollectionProveedor = proveedorCollectionProveedor.getListaContactosID();
                proveedorCollectionProveedor.setListaContactosID(listaContactos);
                proveedorCollectionProveedor = em.merge(proveedorCollectionProveedor);
                if (oldListaContactosIDOfProveedorCollectionProveedor != null) {
                    oldListaContactosIDOfProveedorCollectionProveedor.getProveedorCollection().remove(proveedorCollectionProveedor);
                    oldListaContactosIDOfProveedorCollectionProveedor = em.merge(oldListaContactosIDOfProveedorCollectionProveedor);
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

    public void edit(ListaContactos listaContactos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ListaContactos persistentListaContactos = em.find(ListaContactos.class, listaContactos.getListaContactosID());
            Collection<UsuarioCompras> usuarioComprasCollectionOld = persistentListaContactos.getUsuarioComprasCollection();
            Collection<UsuarioCompras> usuarioComprasCollectionNew = listaContactos.getUsuarioComprasCollection();
            Collection<Proveedor> proveedorCollectionOld = persistentListaContactos.getProveedorCollection();
            Collection<Proveedor> proveedorCollectionNew = listaContactos.getProveedorCollection();
            List<String> illegalOrphanMessages = null;
            for (Proveedor proveedorCollectionOldProveedor : proveedorCollectionOld) {
                if (!proveedorCollectionNew.contains(proveedorCollectionOldProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Proveedor " + proveedorCollectionOldProveedor + " since its listaContactosID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioCompras> attachedUsuarioComprasCollectionNew = new ArrayList<UsuarioCompras>();
            for (UsuarioCompras usuarioComprasCollectionNewUsuarioComprasToAttach : usuarioComprasCollectionNew) {
                usuarioComprasCollectionNewUsuarioComprasToAttach = em.getReference(usuarioComprasCollectionNewUsuarioComprasToAttach.getClass(), usuarioComprasCollectionNewUsuarioComprasToAttach.getUsuarioComprasID());
                attachedUsuarioComprasCollectionNew.add(usuarioComprasCollectionNewUsuarioComprasToAttach);
            }
            usuarioComprasCollectionNew = attachedUsuarioComprasCollectionNew;
            listaContactos.setUsuarioComprasCollection(usuarioComprasCollectionNew);
            Collection<Proveedor> attachedProveedorCollectionNew = new ArrayList<Proveedor>();
            for (Proveedor proveedorCollectionNewProveedorToAttach : proveedorCollectionNew) {
                proveedorCollectionNewProveedorToAttach = em.getReference(proveedorCollectionNewProveedorToAttach.getClass(), proveedorCollectionNewProveedorToAttach.getProveedorID());
                attachedProveedorCollectionNew.add(proveedorCollectionNewProveedorToAttach);
            }
            proveedorCollectionNew = attachedProveedorCollectionNew;
            listaContactos.setProveedorCollection(proveedorCollectionNew);
            listaContactos = em.merge(listaContactos);
            for (UsuarioCompras usuarioComprasCollectionOldUsuarioCompras : usuarioComprasCollectionOld) {
                if (!usuarioComprasCollectionNew.contains(usuarioComprasCollectionOldUsuarioCompras)) {
                    usuarioComprasCollectionOldUsuarioCompras.setListaContactosID(null);
                    usuarioComprasCollectionOldUsuarioCompras = em.merge(usuarioComprasCollectionOldUsuarioCompras);
                }
            }
            for (UsuarioCompras usuarioComprasCollectionNewUsuarioCompras : usuarioComprasCollectionNew) {
                if (!usuarioComprasCollectionOld.contains(usuarioComprasCollectionNewUsuarioCompras)) {
                    ListaContactos oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras = usuarioComprasCollectionNewUsuarioCompras.getListaContactosID();
                    usuarioComprasCollectionNewUsuarioCompras.setListaContactosID(listaContactos);
                    usuarioComprasCollectionNewUsuarioCompras = em.merge(usuarioComprasCollectionNewUsuarioCompras);
                    if (oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras != null && !oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras.equals(listaContactos)) {
                        oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras.getUsuarioComprasCollection().remove(usuarioComprasCollectionNewUsuarioCompras);
                        oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras = em.merge(oldListaContactosIDOfUsuarioComprasCollectionNewUsuarioCompras);
                    }
                }
            }
            for (Proveedor proveedorCollectionNewProveedor : proveedorCollectionNew) {
                if (!proveedorCollectionOld.contains(proveedorCollectionNewProveedor)) {
                    ListaContactos oldListaContactosIDOfProveedorCollectionNewProveedor = proveedorCollectionNewProveedor.getListaContactosID();
                    proveedorCollectionNewProveedor.setListaContactosID(listaContactos);
                    proveedorCollectionNewProveedor = em.merge(proveedorCollectionNewProveedor);
                    if (oldListaContactosIDOfProveedorCollectionNewProveedor != null && !oldListaContactosIDOfProveedorCollectionNewProveedor.equals(listaContactos)) {
                        oldListaContactosIDOfProveedorCollectionNewProveedor.getProveedorCollection().remove(proveedorCollectionNewProveedor);
                        oldListaContactosIDOfProveedorCollectionNewProveedor = em.merge(oldListaContactosIDOfProveedorCollectionNewProveedor);
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
                Integer id = listaContactos.getListaContactosID();
                if (findListaContactos(id) == null) {
                    throw new NonexistentEntityException("The listaContactos with id " + id + " no longer exists.");
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
            
            ListaContactos listaContactos;
            try {
                listaContactos = em.getReference(ListaContactos.class, id);
                listaContactos.getListaContactosID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaContactos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Proveedor> proveedorCollectionOrphanCheck = listaContactos.getProveedorCollection();
            for (Proveedor proveedorCollectionOrphanCheckProveedor : proveedorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListaContactos (" + listaContactos + ") cannot be destroyed since the Proveedor " + proveedorCollectionOrphanCheckProveedor + " in its proveedorCollection field has a non-nullable listaContactosID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioCompras> usuarioComprasCollection = listaContactos.getUsuarioComprasCollection();
            for (UsuarioCompras usuarioComprasCollectionUsuarioCompras : usuarioComprasCollection) {
                usuarioComprasCollectionUsuarioCompras.setListaContactosID(null);
                usuarioComprasCollectionUsuarioCompras = em.merge(usuarioComprasCollectionUsuarioCompras);
            }
            em.remove(listaContactos);
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

    public List<ListaContactos> findListaContactosEntities() {
        return findListaContactosEntities(true, -1, -1);
    }

    public List<ListaContactos> findListaContactosEntities(int maxResults, int firstResult) {
        return findListaContactosEntities(false, maxResults, firstResult);
    }

    private List<ListaContactos> findListaContactosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaContactos.class));
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

    public ListaContactos findListaContactos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaContactos.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaContactosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaContactos> rt = cq.from(ListaContactos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
