/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Uso_Comun.Model_Cliente;
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
public class DAO_Cliente implements Serializable {

    public DAO_Cliente(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Cliente model_Cliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Cliente.getPedidosCollection() == null) {
            model_Cliente.setPedidosCollection(new ArrayList<Model_Pedidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Usuario usuarioUsuarioid = model_Cliente.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid = em.getReference(usuarioUsuarioid.getClass(), usuarioUsuarioid.getUsuarioid());
                model_Cliente.setUsuarioUsuarioid(usuarioUsuarioid);
            }
            Collection<Model_Pedidos> attachedPedidosCollection = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionModel_PedidosToAttach : model_Cliente.getPedidosCollection()) {
                pedidosCollectionModel_PedidosToAttach = em.getReference(pedidosCollectionModel_PedidosToAttach.getClass(), pedidosCollectionModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollection.add(pedidosCollectionModel_PedidosToAttach);
            }
            model_Cliente.setPedidosCollection(attachedPedidosCollection);
            em.persist(model_Cliente);
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getClienteCollection().add(model_Cliente);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            for (Model_Pedidos pedidosCollectionModel_Pedidos : model_Cliente.getPedidosCollection()) {
                Model_Cliente oldClienteIDOfPedidosCollectionModel_Pedidos = pedidosCollectionModel_Pedidos.getClienteID();
                pedidosCollectionModel_Pedidos.setClienteID(model_Cliente);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
                if (oldClienteIDOfPedidosCollectionModel_Pedidos != null) {
                    oldClienteIDOfPedidosCollectionModel_Pedidos.getPedidosCollection().remove(pedidosCollectionModel_Pedidos);
                    oldClienteIDOfPedidosCollectionModel_Pedidos = em.merge(oldClienteIDOfPedidosCollectionModel_Pedidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Cliente(model_Cliente.getClienteID()) != null) {
                throw new PreexistingEntityException("Model_Cliente " + model_Cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Cliente model_Cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Cliente persistentModel_Cliente = em.find(Model_Cliente.class, model_Cliente.getClienteID());
            Model_Usuario usuarioUsuarioidOld = persistentModel_Cliente.getUsuarioUsuarioid();
            Model_Usuario usuarioUsuarioidNew = model_Cliente.getUsuarioUsuarioid();
            Collection<Model_Pedidos> pedidosCollectionOld = persistentModel_Cliente.getPedidosCollection();
            Collection<Model_Pedidos> pedidosCollectionNew = model_Cliente.getPedidosCollection();
            if (usuarioUsuarioidNew != null) {
                usuarioUsuarioidNew = em.getReference(usuarioUsuarioidNew.getClass(), usuarioUsuarioidNew.getUsuarioid());
                model_Cliente.setUsuarioUsuarioid(usuarioUsuarioidNew);
            }
            Collection<Model_Pedidos> attachedPedidosCollectionNew = new ArrayList<Model_Pedidos>();
            for (Model_Pedidos pedidosCollectionNewModel_PedidosToAttach : pedidosCollectionNew) {
                pedidosCollectionNewModel_PedidosToAttach = em.getReference(pedidosCollectionNewModel_PedidosToAttach.getClass(), pedidosCollectionNewModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollectionNew.add(pedidosCollectionNewModel_PedidosToAttach);
            }
            pedidosCollectionNew = attachedPedidosCollectionNew;
            model_Cliente.setPedidosCollection(pedidosCollectionNew);
            model_Cliente = em.merge(model_Cliente);
            if (usuarioUsuarioidOld != null && !usuarioUsuarioidOld.equals(usuarioUsuarioidNew)) {
                usuarioUsuarioidOld.getClienteCollection().remove(model_Cliente);
                usuarioUsuarioidOld = em.merge(usuarioUsuarioidOld);
            }
            if (usuarioUsuarioidNew != null && !usuarioUsuarioidNew.equals(usuarioUsuarioidOld)) {
                usuarioUsuarioidNew.getClienteCollection().add(model_Cliente);
                usuarioUsuarioidNew = em.merge(usuarioUsuarioidNew);
            }
            for (Model_Pedidos pedidosCollectionOldModel_Pedidos : pedidosCollectionOld) {
                if (!pedidosCollectionNew.contains(pedidosCollectionOldModel_Pedidos)) {
                    pedidosCollectionOldModel_Pedidos.setClienteID(null);
                    pedidosCollectionOldModel_Pedidos = em.merge(pedidosCollectionOldModel_Pedidos);
                }
            }
            for (Model_Pedidos pedidosCollectionNewModel_Pedidos : pedidosCollectionNew) {
                if (!pedidosCollectionOld.contains(pedidosCollectionNewModel_Pedidos)) {
                    Model_Cliente oldClienteIDOfPedidosCollectionNewModel_Pedidos = pedidosCollectionNewModel_Pedidos.getClienteID();
                    pedidosCollectionNewModel_Pedidos.setClienteID(model_Cliente);
                    pedidosCollectionNewModel_Pedidos = em.merge(pedidosCollectionNewModel_Pedidos);
                    if (oldClienteIDOfPedidosCollectionNewModel_Pedidos != null && !oldClienteIDOfPedidosCollectionNewModel_Pedidos.equals(model_Cliente)) {
                        oldClienteIDOfPedidosCollectionNewModel_Pedidos.getPedidosCollection().remove(pedidosCollectionNewModel_Pedidos);
                        oldClienteIDOfPedidosCollectionNewModel_Pedidos = em.merge(oldClienteIDOfPedidosCollectionNewModel_Pedidos);
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
                Integer id = model_Cliente.getClienteID();
                if (findModel_Cliente(id) == null) {
                    throw new NonexistentEntityException("The model_Cliente with id " + id + " no longer exists.");
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
            Model_Cliente model_Cliente;
            try {
                model_Cliente = em.getReference(Model_Cliente.class, id);
                model_Cliente.getClienteID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Cliente with id " + id + " no longer exists.", enfe);
            }
            Model_Usuario usuarioUsuarioid = model_Cliente.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getClienteCollection().remove(model_Cliente);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            Collection<Model_Pedidos> pedidosCollection = model_Cliente.getPedidosCollection();
            for (Model_Pedidos pedidosCollectionModel_Pedidos : pedidosCollection) {
                pedidosCollectionModel_Pedidos.setClienteID(null);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
            }
            em.remove(model_Cliente);
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

    public List<Model_Cliente> findModel_ClienteEntities() {
        return findModel_ClienteEntities(true, -1, -1);
    }

    public List<Model_Cliente> findModel_ClienteEntities(int maxResults, int firstResult) {
        return findModel_ClienteEntities(false, maxResults, firstResult);
    }

    private List<Model_Cliente> findModel_ClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Cliente.class));
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

    public Model_Cliente findModel_Cliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_ClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Cliente> rt = cq.from(Model_Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
