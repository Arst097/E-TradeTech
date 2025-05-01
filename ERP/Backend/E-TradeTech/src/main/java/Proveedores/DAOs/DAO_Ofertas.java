/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import Proveedores.Modelos.Ofertas;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
public class DAO_Ofertas implements Serializable {

    public DAO_Ofertas(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ofertas ofertas) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            Proveedor proveedorID = ofertas.getProveedorID();
            if (proveedorID != null) {
                proveedorID = em.getReference(proveedorID.getClass(), proveedorID.getProveedorID());
                ofertas.setProveedorID(proveedorID);
            }
            em.persist(ofertas);
            if (proveedorID != null) {
                proveedorID.getOfertasCollection().add(ofertas);
                proveedorID = em.merge(proveedorID);
            }
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOfertas(ofertas.getOfertasID()) != null) {
                throw new PreexistingEntityException("Ofertas " + ofertas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ofertas ofertas) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ofertas persistentOfertas = em.find(Ofertas.class, ofertas.getOfertasID());
            Proveedor proveedorIDOld = persistentOfertas.getProveedorID();
            Proveedor proveedorIDNew = ofertas.getProveedorID();
            if (proveedorIDNew != null) {
                proveedorIDNew = em.getReference(proveedorIDNew.getClass(), proveedorIDNew.getProveedorID());
                ofertas.setProveedorID(proveedorIDNew);
            }
            ofertas = em.merge(ofertas);
            if (proveedorIDOld != null && !proveedorIDOld.equals(proveedorIDNew)) {
                proveedorIDOld.getOfertasCollection().remove(ofertas);
                proveedorIDOld = em.merge(proveedorIDOld);
            }
            if (proveedorIDNew != null && !proveedorIDNew.equals(proveedorIDOld)) {
                proveedorIDNew.getOfertasCollection().add(ofertas);
                proveedorIDNew = em.merge(proveedorIDNew);
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
                Integer id = ofertas.getOfertasID();
                if (findOfertas(id) == null) {
                    throw new NonexistentEntityException("The ofertas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            Ofertas ofertas;
            try {
                ofertas = em.getReference(Ofertas.class, id);
                ofertas.getOfertasID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ofertas with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedorID = ofertas.getProveedorID();
            if (proveedorID != null) {
                proveedorID.getOfertasCollection().remove(ofertas);
                proveedorID = em.merge(proveedorID);
            }
            em.remove(ofertas);
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

    public List<Ofertas> findOfertasEntities() {
        return findOfertasEntities(true, -1, -1);
    }

    public List<Ofertas> findOfertasEntities(int maxResults, int firstResult) {
        return findOfertasEntities(false, maxResults, firstResult);
    }

    private List<Ofertas> findOfertasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ofertas.class));
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

    public Ofertas findOfertas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ofertas.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfertasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ofertas> rt = cq.from(Ofertas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
