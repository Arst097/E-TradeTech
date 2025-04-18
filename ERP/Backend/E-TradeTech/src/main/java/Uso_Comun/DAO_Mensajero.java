/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Uso_Comun.Model_Mensajero;
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
public class DAO_Mensajero implements Serializable {

    public DAO_Mensajero(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Mensajero model_Mensajero) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Mensajero.getPedidosCollection() == null) {
            model_Mensajero.setPedidosCollection(new ArrayList<Model_Pedidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Model_Pedidos> attachedPedidosCollection = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionModel_PedidosToAttach : model_Mensajero.getPedidosCollection()) {
                pedidosCollectionModel_PedidosToAttach = em.getReference(pedidosCollectionModel_PedidosToAttach.getClass(), pedidosCollectionModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollection.add(pedidosCollectionModel_PedidosToAttach);
            }
            model_Mensajero.setPedidosCollection(attachedPedidosCollection);
            em.persist(model_Mensajero);
            for (Model_Pedidos pedidosCollectionModel_Pedidos : model_Mensajero.getPedidosCollection()) {
                Model_Mensajero oldMensajeroIDOfPedidosCollectionModel_Pedidos = pedidosCollectionModel_Pedidos.getMensajeroID();
                pedidosCollectionModel_Pedidos.setMensajeroID(model_Mensajero);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
                if (oldMensajeroIDOfPedidosCollectionModel_Pedidos != null) {
                    oldMensajeroIDOfPedidosCollectionModel_Pedidos.getPedidosCollection().remove(pedidosCollectionModel_Pedidos);
                    oldMensajeroIDOfPedidosCollectionModel_Pedidos = em.merge(oldMensajeroIDOfPedidosCollectionModel_Pedidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Mensajero(model_Mensajero.getMensajeroID()) != null) {
                throw new PreexistingEntityException("Model_Mensajero " + model_Mensajero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Mensajero model_Mensajero) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Mensajero persistentModel_Mensajero = em.find(Model_Mensajero.class, model_Mensajero.getMensajeroID());
            Collection<Model_Pedidos> pedidosCollectionOld = persistentModel_Mensajero.getPedidosCollection();
            Collection<Model_Pedidos> pedidosCollectionNew = model_Mensajero.getPedidosCollection();
            Collection<Model_Pedidos> attachedPedidosCollectionNew = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionNewModel_PedidosToAttach : pedidosCollectionNew) {
                pedidosCollectionNewModel_PedidosToAttach = em.getReference(pedidosCollectionNewModel_PedidosToAttach.getClass(), pedidosCollectionNewModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollectionNew.add(pedidosCollectionNewModel_PedidosToAttach);
            }
            pedidosCollectionNew = attachedPedidosCollectionNew;
            model_Mensajero.setPedidosCollection(pedidosCollectionNew);
            model_Mensajero = em.merge(model_Mensajero);
            for (Model_Pedidos pedidosCollectionOldModel_Pedidos : pedidosCollectionOld) {
                if (!pedidosCollectionNew.contains(pedidosCollectionOldModel_Pedidos)) {
                    pedidosCollectionOldModel_Pedidos.setMensajeroID(null);
                    pedidosCollectionOldModel_Pedidos = em.merge(pedidosCollectionOldModel_Pedidos);
                }
            }
            for (Model_Pedidos pedidosCollectionNewModel_Pedidos : pedidosCollectionNew) {
                if (!pedidosCollectionOld.contains(pedidosCollectionNewModel_Pedidos)) {
                    Model_Mensajero oldMensajeroIDOfPedidosCollectionNewModel_Pedidos = pedidosCollectionNewModel_Pedidos.getMensajeroID();
                    pedidosCollectionNewModel_Pedidos.setMensajeroID(model_Mensajero);
                    pedidosCollectionNewModel_Pedidos = em.merge(pedidosCollectionNewModel_Pedidos);
                    if (oldMensajeroIDOfPedidosCollectionNewModel_Pedidos != null && !oldMensajeroIDOfPedidosCollectionNewModel_Pedidos.equals(model_Mensajero)) {
                        oldMensajeroIDOfPedidosCollectionNewModel_Pedidos.getPedidosCollection().remove(pedidosCollectionNewModel_Pedidos);
                        oldMensajeroIDOfPedidosCollectionNewModel_Pedidos = em.merge(oldMensajeroIDOfPedidosCollectionNewModel_Pedidos);
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
                Integer id = model_Mensajero.getMensajeroID();
                if (findModel_Mensajero(id) == null) {
                    throw new NonexistentEntityException("The model_Mensajero with id " + id + " no longer exists.");
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
            Model_Mensajero model_Mensajero;
            try {
                model_Mensajero = em.getReference(Model_Mensajero.class, id);
                model_Mensajero.getMensajeroID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Mensajero with id " + id + " no longer exists.", enfe);
            }
            Collection<Model_Pedidos> pedidosCollection = model_Mensajero.getPedidosCollection();
            for (Model_Pedidos pedidosCollectionModel_Pedidos : pedidosCollection) {
                pedidosCollectionModel_Pedidos.setMensajeroID(null);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
            }
            em.remove(model_Mensajero);
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

    public List<Model_Mensajero> findModel_MensajeroEntities() {
        return findModel_MensajeroEntities(true, -1, -1);
    }

    public List<Model_Mensajero> findModel_MensajeroEntities(int maxResults, int firstResult) {
        return findModel_MensajeroEntities(false, maxResults, firstResult);
    }

    private List<Model_Mensajero> findModel_MensajeroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Mensajero.class));
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

    public Model_Mensajero findModel_Mensajero(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Mensajero.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_MensajeroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Mensajero> rt = cq.from(Model_Mensajero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
