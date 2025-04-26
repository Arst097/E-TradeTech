/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.DAOs;

import Inventario.Modelos.HistorialTransaccInv;
import Inventario.Modelos.TransaccionInv;
import Inventario.exceptions.IllegalOrphanException;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_HistorialTransaccInv implements Serializable {

    public DAO_HistorialTransaccInv(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_HistorialTransaccInv() {
        this.emf = Persistence.createEntityManagerFactory("ETradeTech_PU");
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialTransaccInv model_HistorialTransaccInv) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_HistorialTransaccInv.getTransaccionInvCollection() == null) {
            model_HistorialTransaccInv.setTransaccionInvCollection(new ArrayList<TransaccionInv>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TransaccionInv> attachedTransaccionInvCollection = new ArrayList<TransaccionInv>();
            for (TransaccionInv transaccionInvCollectionModel_TransaccionInvToAttach : model_HistorialTransaccInv.getTransaccionInvCollection()) {
                transaccionInvCollectionModel_TransaccionInvToAttach = em.getReference(transaccionInvCollectionModel_TransaccionInvToAttach.getClass(), transaccionInvCollectionModel_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollection.add(transaccionInvCollectionModel_TransaccionInvToAttach);
            }
            model_HistorialTransaccInv.setTransaccionInvCollection(attachedTransaccionInvCollection);
            em.persist(model_HistorialTransaccInv);
            for (TransaccionInv transaccionInvCollectionModel_TransaccionInv : model_HistorialTransaccInv.getTransaccionInvCollection()) {
                HistorialTransaccInv oldHistorialTransaccInvIDOfTransaccionInvCollectionModel_TransaccionInv = transaccionInvCollectionModel_TransaccionInv.getHistorialTransaccInvID();
                transaccionInvCollectionModel_TransaccionInv.setHistorialTransaccInvID(model_HistorialTransaccInv);
                transaccionInvCollectionModel_TransaccionInv = em.merge(transaccionInvCollectionModel_TransaccionInv);
                if (oldHistorialTransaccInvIDOfTransaccionInvCollectionModel_TransaccionInv != null) {
                    oldHistorialTransaccInvIDOfTransaccionInvCollectionModel_TransaccionInv.getTransaccionInvCollection().remove(transaccionInvCollectionModel_TransaccionInv);
                    oldHistorialTransaccInvIDOfTransaccionInvCollectionModel_TransaccionInv = em.merge(oldHistorialTransaccInvIDOfTransaccionInvCollectionModel_TransaccionInv);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_HistorialTransaccInv(model_HistorialTransaccInv.getHistorialTransaccInvID()) != null) {
                throw new PreexistingEntityException("Model_HistorialTransaccInv " + model_HistorialTransaccInv + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialTransaccInv model_HistorialTransaccInv) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistorialTransaccInv persistentModel_HistorialTransaccInv = em.find(HistorialTransaccInv.class, model_HistorialTransaccInv.getHistorialTransaccInvID());
            Collection<TransaccionInv> transaccionInvCollectionOld = persistentModel_HistorialTransaccInv.getTransaccionInvCollection();
            Collection<TransaccionInv> transaccionInvCollectionNew = model_HistorialTransaccInv.getTransaccionInvCollection();
            List<String> illegalOrphanMessages = null;
            for (TransaccionInv transaccionInvCollectionOldModel_TransaccionInv : transaccionInvCollectionOld) {
                if (!transaccionInvCollectionNew.contains(transaccionInvCollectionOldModel_TransaccionInv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_TransaccionInv " + transaccionInvCollectionOldModel_TransaccionInv + " since its historialTransaccInvID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransaccionInv> attachedTransaccionInvCollectionNew = new ArrayList<TransaccionInv>();
            for (TransaccionInv transaccionInvCollectionNewModel_TransaccionInvToAttach : transaccionInvCollectionNew) {
                transaccionInvCollectionNewModel_TransaccionInvToAttach = em.getReference(transaccionInvCollectionNewModel_TransaccionInvToAttach.getClass(), transaccionInvCollectionNewModel_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollectionNew.add(transaccionInvCollectionNewModel_TransaccionInvToAttach);
            }
            transaccionInvCollectionNew = attachedTransaccionInvCollectionNew;
            model_HistorialTransaccInv.setTransaccionInvCollection(transaccionInvCollectionNew);
            model_HistorialTransaccInv = em.merge(model_HistorialTransaccInv);
            for (TransaccionInv transaccionInvCollectionNewModel_TransaccionInv : transaccionInvCollectionNew) {
                if (!transaccionInvCollectionOld.contains(transaccionInvCollectionNewModel_TransaccionInv)) {
                    HistorialTransaccInv oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv = transaccionInvCollectionNewModel_TransaccionInv.getHistorialTransaccInvID();
                    transaccionInvCollectionNewModel_TransaccionInv.setHistorialTransaccInvID(model_HistorialTransaccInv);
                    transaccionInvCollectionNewModel_TransaccionInv = em.merge(transaccionInvCollectionNewModel_TransaccionInv);
                    if (oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv != null && !oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv.equals(model_HistorialTransaccInv)) {
                        oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv.getTransaccionInvCollection().remove(transaccionInvCollectionNewModel_TransaccionInv);
                        oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv = em.merge(oldHistorialTransaccInvIDOfTransaccionInvCollectionNewModel_TransaccionInv);
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
                Integer id = model_HistorialTransaccInv.getHistorialTransaccInvID();
                if (findModel_HistorialTransaccInv(id) == null) {
                    throw new NonexistentEntityException("The model_HistorialTransaccInv with id " + id + " no longer exists.");
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
        try {
            utx.begin();
            em = getEntityManager();
            HistorialTransaccInv model_HistorialTransaccInv;
            try {
                model_HistorialTransaccInv = em.getReference(HistorialTransaccInv.class, id);
                model_HistorialTransaccInv.getHistorialTransaccInvID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_HistorialTransaccInv with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransaccionInv> transaccionInvCollectionOrphanCheck = model_HistorialTransaccInv.getTransaccionInvCollection();
            for (TransaccionInv transaccionInvCollectionOrphanCheckModel_TransaccionInv : transaccionInvCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_HistorialTransaccInv (" + model_HistorialTransaccInv + ") cannot be destroyed since the Model_TransaccionInv " + transaccionInvCollectionOrphanCheckModel_TransaccionInv + " in its transaccionInvCollection field has a non-nullable historialTransaccInvID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(model_HistorialTransaccInv);
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

    public List<HistorialTransaccInv> findModel_HistorialTransaccInvEntities() {
        return findModel_HistorialTransaccInvEntities(true, -1, -1);
    }

    public List<HistorialTransaccInv> findModel_HistorialTransaccInvEntities(int maxResults, int firstResult) {
        return findModel_HistorialTransaccInvEntities(false, maxResults, firstResult);
    }

    private List<HistorialTransaccInv> findModel_HistorialTransaccInvEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialTransaccInv.class));
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

    public HistorialTransaccInv findModel_HistorialTransaccInv(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialTransaccInv.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_HistorialTransaccInvCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialTransaccInv> rt = cq.from(HistorialTransaccInv.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
