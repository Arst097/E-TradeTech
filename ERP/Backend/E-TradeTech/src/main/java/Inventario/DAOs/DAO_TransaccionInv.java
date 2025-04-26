/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.DAOs;

import Inventario.Modelos.HistorialTransaccInv;
import Inventario.Modelos.Inventario;
import Inventario.Modelos.TransaccionInv;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_TransaccionInv implements Serializable {

    public DAO_TransaccionInv(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_TransaccionInv() {
        this.emf = Persistence.createEntityManagerFactory("ETradeTech_PU");
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransaccionInv model_TransaccionInv) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistorialTransaccInv historialTransaccInvID = model_TransaccionInv.getHistorialTransaccInvID();
            if (historialTransaccInvID != null) {
                historialTransaccInvID = em.getReference(historialTransaccInvID.getClass(), historialTransaccInvID.getHistorialTransaccInvID());
                model_TransaccionInv.setHistorialTransaccInvID(historialTransaccInvID);
            }
            Inventario inventarioEmisorID = model_TransaccionInv.getInventarioEmisorID();
            if (inventarioEmisorID != null) {
                inventarioEmisorID = em.getReference(inventarioEmisorID.getClass(), inventarioEmisorID.getInventarioID());
                model_TransaccionInv.setInventarioEmisorID(inventarioEmisorID);
            }
            Inventario inventarioReceptorD = model_TransaccionInv.getInventarioReceptorD();
            if (inventarioReceptorD != null) {
                inventarioReceptorD = em.getReference(inventarioReceptorD.getClass(), inventarioReceptorD.getInventarioID());
                model_TransaccionInv.setInventarioReceptorD(inventarioReceptorD);
            }
            em.persist(model_TransaccionInv);
            if (historialTransaccInvID != null) {
                historialTransaccInvID.getTransaccionInvCollection().add(model_TransaccionInv);
                historialTransaccInvID = em.merge(historialTransaccInvID);
            }
            if (inventarioEmisorID != null) {
                inventarioEmisorID.getTransaccionInvCollection().add(model_TransaccionInv);
                inventarioEmisorID = em.merge(inventarioEmisorID);
            }
            if (inventarioReceptorD != null) {
                inventarioReceptorD.getTransaccionInvCollection().add(model_TransaccionInv);
                inventarioReceptorD = em.merge(inventarioReceptorD);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_TransaccionInv(model_TransaccionInv.getTransaccionInvID()) != null) {
                throw new PreexistingEntityException("Model_TransaccionInv " + model_TransaccionInv + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransaccionInv model_TransaccionInv) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TransaccionInv persistentModel_TransaccionInv = em.find(TransaccionInv.class, model_TransaccionInv.getTransaccionInvID());
            HistorialTransaccInv historialTransaccInvIDOld = persistentModel_TransaccionInv.getHistorialTransaccInvID();
            HistorialTransaccInv historialTransaccInvIDNew = model_TransaccionInv.getHistorialTransaccInvID();
            Inventario inventarioEmisorIDOld = persistentModel_TransaccionInv.getInventarioEmisorID();
            Inventario inventarioEmisorIDNew = model_TransaccionInv.getInventarioEmisorID();
            Inventario inventarioReceptorDOld = persistentModel_TransaccionInv.getInventarioReceptorD();
            Inventario inventarioReceptorDNew = model_TransaccionInv.getInventarioReceptorD();
            if (historialTransaccInvIDNew != null) {
                historialTransaccInvIDNew = em.getReference(historialTransaccInvIDNew.getClass(), historialTransaccInvIDNew.getHistorialTransaccInvID());
                model_TransaccionInv.setHistorialTransaccInvID(historialTransaccInvIDNew);
            }
            if (inventarioEmisorIDNew != null) {
                inventarioEmisorIDNew = em.getReference(inventarioEmisorIDNew.getClass(), inventarioEmisorIDNew.getInventarioID());
                model_TransaccionInv.setInventarioEmisorID(inventarioEmisorIDNew);
            }
            if (inventarioReceptorDNew != null) {
                inventarioReceptorDNew = em.getReference(inventarioReceptorDNew.getClass(), inventarioReceptorDNew.getInventarioID());
                model_TransaccionInv.setInventarioReceptorD(inventarioReceptorDNew);
            }
            model_TransaccionInv = em.merge(model_TransaccionInv);
            if (historialTransaccInvIDOld != null && !historialTransaccInvIDOld.equals(historialTransaccInvIDNew)) {
                historialTransaccInvIDOld.getTransaccionInvCollection().remove(model_TransaccionInv);
                historialTransaccInvIDOld = em.merge(historialTransaccInvIDOld);
            }
            if (historialTransaccInvIDNew != null && !historialTransaccInvIDNew.equals(historialTransaccInvIDOld)) {
                historialTransaccInvIDNew.getTransaccionInvCollection().add(model_TransaccionInv);
                historialTransaccInvIDNew = em.merge(historialTransaccInvIDNew);
            }
            if (inventarioEmisorIDOld != null && !inventarioEmisorIDOld.equals(inventarioEmisorIDNew)) {
                inventarioEmisorIDOld.getTransaccionInvCollection().remove(model_TransaccionInv);
                inventarioEmisorIDOld = em.merge(inventarioEmisorIDOld);
            }
            if (inventarioEmisorIDNew != null && !inventarioEmisorIDNew.equals(inventarioEmisorIDOld)) {
                inventarioEmisorIDNew.getTransaccionInvCollection().add(model_TransaccionInv);
                inventarioEmisorIDNew = em.merge(inventarioEmisorIDNew);
            }
            if (inventarioReceptorDOld != null && !inventarioReceptorDOld.equals(inventarioReceptorDNew)) {
                inventarioReceptorDOld.getTransaccionInvCollection().remove(model_TransaccionInv);
                inventarioReceptorDOld = em.merge(inventarioReceptorDOld);
            }
            if (inventarioReceptorDNew != null && !inventarioReceptorDNew.equals(inventarioReceptorDOld)) {
                inventarioReceptorDNew.getTransaccionInvCollection().add(model_TransaccionInv);
                inventarioReceptorDNew = em.merge(inventarioReceptorDNew);
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
                Integer id = model_TransaccionInv.getTransaccionInvID();
                if (findModel_TransaccionInv(id) == null) {
                    throw new NonexistentEntityException("The model_TransaccionInv with id " + id + " no longer exists.");
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
        try {
            utx.begin();
            em = getEntityManager();
            TransaccionInv model_TransaccionInv;
            try {
                model_TransaccionInv = em.getReference(TransaccionInv.class, id);
                model_TransaccionInv.getTransaccionInvID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_TransaccionInv with id " + id + " no longer exists.", enfe);
            }
            HistorialTransaccInv historialTransaccInvID = model_TransaccionInv.getHistorialTransaccInvID();
            if (historialTransaccInvID != null) {
                historialTransaccInvID.getTransaccionInvCollection().remove(model_TransaccionInv);
                historialTransaccInvID = em.merge(historialTransaccInvID);
            }
            Inventario inventarioEmisorID = model_TransaccionInv.getInventarioEmisorID();
            if (inventarioEmisorID != null) {
                inventarioEmisorID.getTransaccionInvCollection().remove(model_TransaccionInv);
                inventarioEmisorID = em.merge(inventarioEmisorID);
            }
            Inventario inventarioReceptorD = model_TransaccionInv.getInventarioReceptorD();
            if (inventarioReceptorD != null) {
                inventarioReceptorD.getTransaccionInvCollection().remove(model_TransaccionInv);
                inventarioReceptorD = em.merge(inventarioReceptorD);
            }
            em.remove(model_TransaccionInv);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public List<TransaccionInv> findModel_TransaccionInvEntities() {
        return findModel_TransaccionInvEntities(true, -1, -1);
    }

    public List<TransaccionInv> findModel_TransaccionInvEntities(int maxResults, int firstResult) {
        return findModel_TransaccionInvEntities(false, maxResults, firstResult);
    }

    private List<TransaccionInv> findModel_TransaccionInvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransaccionInv.class));
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

    public TransaccionInv findModel_TransaccionInv(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransaccionInv.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_TransaccionInvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransaccionInv> rt = cq.from(TransaccionInv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
