/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.DAOs;

import Inventario.Modelos.Almacen;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Inventario;
import Uso_Comun.Modelos.Usuario;
import Uso_Comun.Modelos.Pedidos;
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
public class DAO_Despachador implements Serializable {

    public DAO_Despachador(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_Despachador() {
        this.emf = Persistence.createEntityManagerFactory("ETradeTech_PU");
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Despachador model_Despachador) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Despachador.getPedidosCollection() == null) {
            model_Despachador.setPedidosCollection(new ArrayList<Pedidos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Almacen almacenID = model_Despachador.getAlmacenID();
            if (almacenID != null) {
                almacenID = em.getReference(almacenID.getClass(), almacenID.getAlmacenID());
                model_Despachador.setAlmacenID(almacenID);
            }
            Inventario inventarioID = model_Despachador.getInventarioID();
            if (inventarioID != null) {
                inventarioID = em.getReference(inventarioID.getClass(), inventarioID.getInventarioID());
                model_Despachador.setInventarioID(inventarioID);
            }
            Usuario usuarioUsuarioid = model_Despachador.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid = em.getReference(usuarioUsuarioid.getClass(), usuarioUsuarioid.getUsuarioid());
                model_Despachador.setUsuarioUsuarioid(usuarioUsuarioid);
            }
            Collection<Pedidos> attachedPedidosCollection = new ArrayList<Pedidos>();
            for (Pedidos pedidosCollectionModel_PedidosToAttach : model_Despachador.getPedidosCollection()) {
                pedidosCollectionModel_PedidosToAttach = em.getReference(pedidosCollectionModel_PedidosToAttach.getClass(), pedidosCollectionModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollection.add(pedidosCollectionModel_PedidosToAttach);
            }
            model_Despachador.setPedidosCollection(attachedPedidosCollection);
            em.persist(model_Despachador);
            if (almacenID != null) {
                almacenID.getDespachadorCollection().add(model_Despachador);
                almacenID = em.merge(almacenID);
            }
            if (inventarioID != null) {
                inventarioID.getDespachadorCollection().add(model_Despachador);
                inventarioID = em.merge(inventarioID);
            }
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getDespachadorCollection().add(model_Despachador);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            for (Pedidos pedidosCollectionModel_Pedidos : model_Despachador.getPedidosCollection()) {
                Despachador oldDespachadorIDOfPedidosCollectionModel_Pedidos = pedidosCollectionModel_Pedidos.getDespachadorID();
                pedidosCollectionModel_Pedidos.setDespachadorID(model_Despachador);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
                if (oldDespachadorIDOfPedidosCollectionModel_Pedidos != null) {
                    oldDespachadorIDOfPedidosCollectionModel_Pedidos.getPedidosCollection().remove(pedidosCollectionModel_Pedidos);
                    oldDespachadorIDOfPedidosCollectionModel_Pedidos = em.merge(oldDespachadorIDOfPedidosCollectionModel_Pedidos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Despachador(model_Despachador.getDespachadorID()) != null) {
                throw new PreexistingEntityException("Model_Despachador " + model_Despachador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Despachador model_Despachador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Despachador persistentModel_Despachador = em.find(Despachador.class, model_Despachador.getDespachadorID());
            Almacen almacenIDOld = persistentModel_Despachador.getAlmacenID();
            Almacen almacenIDNew = model_Despachador.getAlmacenID();
            Inventario inventarioIDOld = persistentModel_Despachador.getInventarioID();
            Inventario inventarioIDNew = model_Despachador.getInventarioID();
            Usuario usuarioUsuarioidOld = persistentModel_Despachador.getUsuarioUsuarioid();
            Usuario usuarioUsuarioidNew = model_Despachador.getUsuarioUsuarioid();
            Collection<Pedidos> pedidosCollectionOld = persistentModel_Despachador.getPedidosCollection();
            Collection<Pedidos> pedidosCollectionNew = model_Despachador.getPedidosCollection();
            if (almacenIDNew != null) {
                almacenIDNew = em.getReference(almacenIDNew.getClass(), almacenIDNew.getAlmacenID());
                model_Despachador.setAlmacenID(almacenIDNew);
            }
            if (inventarioIDNew != null) {
                inventarioIDNew = em.getReference(inventarioIDNew.getClass(), inventarioIDNew.getInventarioID());
                model_Despachador.setInventarioID(inventarioIDNew);
            }
            if (usuarioUsuarioidNew != null) {
                usuarioUsuarioidNew = em.getReference(usuarioUsuarioidNew.getClass(), usuarioUsuarioidNew.getUsuarioid());
                model_Despachador.setUsuarioUsuarioid(usuarioUsuarioidNew);
            }
            Collection<Pedidos> attachedPedidosCollectionNew = new ArrayList<Pedidos>();
            for (Pedidos pedidosCollectionNewModel_PedidosToAttach : pedidosCollectionNew) {
                pedidosCollectionNewModel_PedidosToAttach = em.getReference(pedidosCollectionNewModel_PedidosToAttach.getClass(), pedidosCollectionNewModel_PedidosToAttach.getPedidoID());
                attachedPedidosCollectionNew.add(pedidosCollectionNewModel_PedidosToAttach);
            }
            pedidosCollectionNew = attachedPedidosCollectionNew;
            model_Despachador.setPedidosCollection(pedidosCollectionNew);
            model_Despachador = em.merge(model_Despachador);
            if (almacenIDOld != null && !almacenIDOld.equals(almacenIDNew)) {
                almacenIDOld.getDespachadorCollection().remove(model_Despachador);
                almacenIDOld = em.merge(almacenIDOld);
            }
            if (almacenIDNew != null && !almacenIDNew.equals(almacenIDOld)) {
                almacenIDNew.getDespachadorCollection().add(model_Despachador);
                almacenIDNew = em.merge(almacenIDNew);
            }
            if (inventarioIDOld != null && !inventarioIDOld.equals(inventarioIDNew)) {
                inventarioIDOld.getDespachadorCollection().remove(model_Despachador);
                inventarioIDOld = em.merge(inventarioIDOld);
            }
            if (inventarioIDNew != null && !inventarioIDNew.equals(inventarioIDOld)) {
                inventarioIDNew.getDespachadorCollection().add(model_Despachador);
                inventarioIDNew = em.merge(inventarioIDNew);
            }
            if (usuarioUsuarioidOld != null && !usuarioUsuarioidOld.equals(usuarioUsuarioidNew)) {
                usuarioUsuarioidOld.getDespachadorCollection().remove(model_Despachador);
                usuarioUsuarioidOld = em.merge(usuarioUsuarioidOld);
            }
            if (usuarioUsuarioidNew != null && !usuarioUsuarioidNew.equals(usuarioUsuarioidOld)) {
                usuarioUsuarioidNew.getDespachadorCollection().add(model_Despachador);
                usuarioUsuarioidNew = em.merge(usuarioUsuarioidNew);
            }
            for (Pedidos pedidosCollectionOldModel_Pedidos : pedidosCollectionOld) {
                if (!pedidosCollectionNew.contains(pedidosCollectionOldModel_Pedidos)) {
                    pedidosCollectionOldModel_Pedidos.setDespachadorID(null);
                    pedidosCollectionOldModel_Pedidos = em.merge(pedidosCollectionOldModel_Pedidos);
                }
            }
            for (Pedidos pedidosCollectionNewModel_Pedidos : pedidosCollectionNew) {
                if (!pedidosCollectionOld.contains(pedidosCollectionNewModel_Pedidos)) {
                    Despachador oldDespachadorIDOfPedidosCollectionNewModel_Pedidos = pedidosCollectionNewModel_Pedidos.getDespachadorID();
                    pedidosCollectionNewModel_Pedidos.setDespachadorID(model_Despachador);
                    pedidosCollectionNewModel_Pedidos = em.merge(pedidosCollectionNewModel_Pedidos);
                    if (oldDespachadorIDOfPedidosCollectionNewModel_Pedidos != null && !oldDespachadorIDOfPedidosCollectionNewModel_Pedidos.equals(model_Despachador)) {
                        oldDespachadorIDOfPedidosCollectionNewModel_Pedidos.getPedidosCollection().remove(pedidosCollectionNewModel_Pedidos);
                        oldDespachadorIDOfPedidosCollectionNewModel_Pedidos = em.merge(oldDespachadorIDOfPedidosCollectionNewModel_Pedidos);
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
                Integer id = model_Despachador.getDespachadorID();
                if (findModel_Despachador(id) == null) {
                    throw new NonexistentEntityException("The model_Despachador with id " + id + " no longer exists.");
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
            Despachador model_Despachador;
            try {
                model_Despachador = em.getReference(Despachador.class, id);
                model_Despachador.getDespachadorID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Despachador with id " + id + " no longer exists.", enfe);
            }
            Almacen almacenID = model_Despachador.getAlmacenID();
            if (almacenID != null) {
                almacenID.getDespachadorCollection().remove(model_Despachador);
                almacenID = em.merge(almacenID);
            }
            Inventario inventarioID = model_Despachador.getInventarioID();
            if (inventarioID != null) {
                inventarioID.getDespachadorCollection().remove(model_Despachador);
                inventarioID = em.merge(inventarioID);
            }
            Usuario usuarioUsuarioid = model_Despachador.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getDespachadorCollection().remove(model_Despachador);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            Collection<Pedidos> pedidosCollection = model_Despachador.getPedidosCollection();
            for (Pedidos pedidosCollectionModel_Pedidos : pedidosCollection) {
                pedidosCollectionModel_Pedidos.setDespachadorID(null);
                pedidosCollectionModel_Pedidos = em.merge(pedidosCollectionModel_Pedidos);
            }
            em.remove(model_Despachador);
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

    public List<Despachador> findModel_DespachadorEntities() {
        return findModel_DespachadorEntities(true, -1, -1);
    }

    public List<Despachador> findModel_DespachadorEntities(int maxResults, int firstResult) {
        return findModel_DespachadorEntities(false, maxResults, firstResult);
    }

    private List<Despachador> findModel_DespachadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Despachador.class));
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

    public Despachador findModel_Despachador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Despachador.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_DespachadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Despachador> rt = cq.from(Despachador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
