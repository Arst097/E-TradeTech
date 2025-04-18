/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Pedidos;
import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
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
public class DAO_HistorialPedido implements Serializable {

    public DAO_HistorialPedido(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_HistorialPedidos model_HistorialPedidos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_HistorialPedidos.getPedidosCollection() == null) {
            model_HistorialPedidos.setPedidosCollection(new ArrayList<Model_Pedidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Model_Pedidos> attachedPedidosCollection = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionModel_PedidosToAttach : model_HistorialPedidos.getPedidosCollection()) {
                pedidosCollectionModel_PedidosToAttach = em.getReference(pedidosCollectionModel_PedidosToAttach.getClass(), pedidosCollectionModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollection.add(pedidosCollectionModel_PedidosToAttach);
            }
            model_HistorialPedidos.setPedidosCollection(attachedPedidosCollection);
            em.persist(model_HistorialPedidos);
            for (Model_Pedidos pedidosCollectionModel_Pedidos : model_HistorialPedidos.getPedidosCollection()) {
                Model_HistorialPedidos oldHistorialPredidosIDOfPedidosCollectionModel_Pedidos = pedidosCollectionModel_Pedidos.getHistorialPredidosID();
                pedidosCollectionModel_Pedidos.setHistorialPredidosID(model_HistorialPedidos);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
                if (oldHistorialPredidosIDOfPedidosCollectionModel_Pedidos != null) {
                    oldHistorialPredidosIDOfPedidosCollectionModel_Pedidos.getPedidosCollection().remove(pedidosCollectionModel_Pedidos);
                    oldHistorialPredidosIDOfPedidosCollectionModel_Pedidos = em.merge(oldHistorialPredidosIDOfPedidosCollectionModel_Pedidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_HistorialPedidos(model_HistorialPedidos.getHistorialPredidosID()) != null) {
                throw new PreexistingEntityException("Model_HistorialPedidos " + model_HistorialPedidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_HistorialPedidos model_HistorialPedidos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_HistorialPedidos persistentModel_HistorialPedidos = em.find(Model_HistorialPedidos.class, model_HistorialPedidos.getHistorialPredidosID());
            Collection<Model_Pedidos> pedidosCollectionOld = persistentModel_HistorialPedidos.getPedidosCollection();
            Collection<Model_Pedidos> pedidosCollectionNew = model_HistorialPedidos.getPedidosCollection();
            List<String> illegalOrphanMessages = null;
            for (Model_Pedidos pedidosCollectionOldModel_Pedidos : pedidosCollectionOld) {
                if (!pedidosCollectionNew.contains(pedidosCollectionOldModel_Pedidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Pedidos " + pedidosCollectionOldModel_Pedidos + " since its historialPredidosID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Model_Pedidos> attachedPedidosCollectionNew = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionNewModel_PedidosToAttach : pedidosCollectionNew) {
                pedidosCollectionNewModel_PedidosToAttach = em.getReference(pedidosCollectionNewModel_PedidosToAttach.getClass(), pedidosCollectionNewModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollectionNew.add(pedidosCollectionNewModel_PedidosToAttach);
            }
            pedidosCollectionNew = attachedPedidosCollectionNew;
            model_HistorialPedidos.setPedidosCollection(pedidosCollectionNew);
            model_HistorialPedidos = em.merge(model_HistorialPedidos);
            for (Model_Pedidos pedidosCollectionNewModel_Pedidos : pedidosCollectionNew) {
                if (!pedidosCollectionOld.contains(pedidosCollectionNewModel_Pedidos)) {
                    Model_HistorialPedidos oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos = pedidosCollectionNewModel_Pedidos.getHistorialPredidosID();
                    pedidosCollectionNewModel_Pedidos.setHistorialPredidosID(model_HistorialPedidos);
                    pedidosCollectionNewModel_Pedidos = em.merge(pedidosCollectionNewModel_Pedidos);
                    if (oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos != null && !oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos.equals(model_HistorialPedidos)) {
                        oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos.getPedidosCollection().remove(pedidosCollectionNewModel_Pedidos);
                        oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos = em.merge(oldHistorialPredidosIDOfPedidosCollectionNewModel_Pedidos);
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
                Integer id = model_HistorialPedidos.getHistorialPredidosID();
                if (findModel_HistorialPedidos(id) == null) {
                    throw new NonexistentEntityException("The model_HistorialPedidos with id " + id + " no longer exists.");
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
            Model_HistorialPedidos model_HistorialPedidos;
            try {
                model_HistorialPedidos = em.getReference(Model_HistorialPedidos.class, id);
                model_HistorialPedidos.getHistorialPredidosID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_HistorialPedidos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Model_Pedidos> pedidosCollectionOrphanCheck = model_HistorialPedidos.getPedidosCollection();
            for (Model_Pedidos pedidosCollectionOrphanCheckModel_Pedidos : pedidosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_HistorialPedidos (" + model_HistorialPedidos + ") cannot be destroyed since the Model_Pedidos " + pedidosCollectionOrphanCheckModel_Pedidos + " in its pedidosCollection field has a non-nullable historialPredidosID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(model_HistorialPedidos);
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

    public List<Model_HistorialPedidos> findModel_HistorialPedidosEntities() {
        return findModel_HistorialPedidosEntities(true, -1, -1);
    }

    public List<Model_HistorialPedidos> findModel_HistorialPedidosEntities(int maxResults, int firstResult) {
        return findModel_HistorialPedidosEntities(false, maxResults, firstResult);
    }

    private List<Model_HistorialPedidos> findModel_HistorialPedidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_HistorialPedidos.class));
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

    public Model_HistorialPedidos findModel_HistorialPedidos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_HistorialPedidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_HistorialPedidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_HistorialPedidos> rt = cq.from(Model_HistorialPedidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
