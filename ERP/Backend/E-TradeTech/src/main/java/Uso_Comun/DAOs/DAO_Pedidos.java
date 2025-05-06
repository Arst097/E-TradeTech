/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.DAOs;

import Uso_Comun.Modelos.Producto;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.HistorialPedidos;
import Uso_Comun.Modelos.Pedidos;
import Uso_Comun.Modelos.Mensajero;
import Uso_Comun.Modelos.Cliente;
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
public class DAO_Pedidos implements Serializable {

    public DAO_Pedidos(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_Pedidos() {
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedidos model_Pedidos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Pedidos.getProductoCollection() == null) {
            model_Pedidos.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente clienteID = model_Pedidos.getClienteID();
            if (clienteID != null) {
                clienteID = em.getReference(clienteID.getClass(), clienteID.getClienteID());
                model_Pedidos.setClienteID(clienteID);
            }
            Despachador despachadorID = model_Pedidos.getDespachadorID();
            if (despachadorID != null) {
                despachadorID = em.getReference(despachadorID.getClass(), despachadorID.getDespachadorID());
                model_Pedidos.setDespachadorID(despachadorID);
            }
            HistorialPedidos historialPredidosID = model_Pedidos.getHistorialPredidosID();
            if (historialPredidosID != null) {
                historialPredidosID = em.getReference(historialPredidosID.getClass(), historialPredidosID.getHistorialPredidosID());
                model_Pedidos.setHistorialPredidosID(historialPredidosID);
            }
            Mensajero mensajeroID = model_Pedidos.getMensajeroID();
            if (mensajeroID != null) {
                mensajeroID = em.getReference(mensajeroID.getClass(), mensajeroID.getMensajeroID());
                model_Pedidos.setMensajeroID(mensajeroID);
            }
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionModel_ProductoToAttach : model_Pedidos.getProductoCollection()) {
                productoCollectionModel_ProductoToAttach = em.getReference(productoCollectionModel_ProductoToAttach.getClass(), productoCollectionModel_ProductoToAttach.getProductoID());
                attachedProductoCollection.add(productoCollectionModel_ProductoToAttach);
            }
            model_Pedidos.setProductoCollection(attachedProductoCollection);
            em.persist(model_Pedidos);
            if (clienteID != null) {
                clienteID.getPedidosCollection().add(model_Pedidos);
                clienteID = em.merge(clienteID);
            }
            if (despachadorID != null) {
                despachadorID.getPedidosCollection().add(model_Pedidos);
                despachadorID = em.merge(despachadorID);
            }
            if (historialPredidosID != null) {
                historialPredidosID.getPedidosCollection().add(model_Pedidos);
                historialPredidosID = em.merge(historialPredidosID);
            }
            if (mensajeroID != null) {
                mensajeroID.getPedidosCollection().add(model_Pedidos);
                mensajeroID = em.merge(mensajeroID);
            }
            for (Producto productoCollectionModel_Producto : model_Pedidos.getProductoCollection()) {
                Pedidos oldPedidoIDOfProductoCollectionModel_Producto = productoCollectionModel_Producto.getPedidoID();
                productoCollectionModel_Producto.setPedidoID(model_Pedidos);
                productoCollectionModel_Producto = em.merge(productoCollectionModel_Producto);
                if (oldPedidoIDOfProductoCollectionModel_Producto != null) {
                    oldPedidoIDOfProductoCollectionModel_Producto.getProductoCollection().remove(productoCollectionModel_Producto);
                    oldPedidoIDOfProductoCollectionModel_Producto = em.merge(oldPedidoIDOfProductoCollectionModel_Producto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Pedidos(model_Pedidos.getPedidoID()) != null) {
                throw new PreexistingEntityException("Model_Pedidos " + model_Pedidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedidos model_Pedidos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pedidos persistentModel_Pedidos = em.find(Pedidos.class, model_Pedidos.getPedidoID());
            Cliente clienteIDOld = persistentModel_Pedidos.getClienteID();
            Cliente clienteIDNew = model_Pedidos.getClienteID();
            Despachador despachadorIDOld = persistentModel_Pedidos.getDespachadorID();
            Despachador despachadorIDNew = model_Pedidos.getDespachadorID();
            HistorialPedidos historialPredidosIDOld = persistentModel_Pedidos.getHistorialPredidosID();
            HistorialPedidos historialPredidosIDNew = model_Pedidos.getHistorialPredidosID();
            Mensajero mensajeroIDOld = persistentModel_Pedidos.getMensajeroID();
            Mensajero mensajeroIDNew = model_Pedidos.getMensajeroID();
            Collection<Producto> productoCollectionOld = persistentModel_Pedidos.getProductoCollection();
            Collection<Producto> productoCollectionNew = model_Pedidos.getProductoCollection();
            if (clienteIDNew != null) {
                clienteIDNew = em.getReference(clienteIDNew.getClass(), clienteIDNew.getClienteID());
                model_Pedidos.setClienteID(clienteIDNew);
            }
            if (despachadorIDNew != null) {
                despachadorIDNew = em.getReference(despachadorIDNew.getClass(), despachadorIDNew.getDespachadorID());
                model_Pedidos.setDespachadorID(despachadorIDNew);
            }
            if (historialPredidosIDNew != null) {
                historialPredidosIDNew = em.getReference(historialPredidosIDNew.getClass(), historialPredidosIDNew.getHistorialPredidosID());
                model_Pedidos.setHistorialPredidosID(historialPredidosIDNew);
            }
            if (mensajeroIDNew != null) {
                mensajeroIDNew = em.getReference(mensajeroIDNew.getClass(), mensajeroIDNew.getMensajeroID());
                model_Pedidos.setMensajeroID(mensajeroIDNew);
            }
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewModel_ProductoToAttach : productoCollectionNew) {
                productoCollectionNewModel_ProductoToAttach = em.getReference(productoCollectionNewModel_ProductoToAttach.getClass(), productoCollectionNewModel_ProductoToAttach.getProductoID());
                attachedProductoCollectionNew.add(productoCollectionNewModel_ProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            model_Pedidos.setProductoCollection(productoCollectionNew);
            model_Pedidos = em.merge(model_Pedidos);
            if (clienteIDOld != null && !clienteIDOld.equals(clienteIDNew)) {
                clienteIDOld.getPedidosCollection().remove(model_Pedidos);
                clienteIDOld = em.merge(clienteIDOld);
            }
            if (clienteIDNew != null && !clienteIDNew.equals(clienteIDOld)) {
                clienteIDNew.getPedidosCollection().add(model_Pedidos);
                clienteIDNew = em.merge(clienteIDNew);
            }
            if (despachadorIDOld != null && !despachadorIDOld.equals(despachadorIDNew)) {
                despachadorIDOld.getPedidosCollection().remove(model_Pedidos);
                despachadorIDOld = em.merge(despachadorIDOld);
            }
            if (despachadorIDNew != null && !despachadorIDNew.equals(despachadorIDOld)) {
                despachadorIDNew.getPedidosCollection().add(model_Pedidos);
                despachadorIDNew = em.merge(despachadorIDNew);
            }
            if (historialPredidosIDOld != null && !historialPredidosIDOld.equals(historialPredidosIDNew)) {
                historialPredidosIDOld.getPedidosCollection().remove(model_Pedidos);
                historialPredidosIDOld = em.merge(historialPredidosIDOld);
            }
            if (historialPredidosIDNew != null && !historialPredidosIDNew.equals(historialPredidosIDOld)) {
                historialPredidosIDNew.getPedidosCollection().add(model_Pedidos);
                historialPredidosIDNew = em.merge(historialPredidosIDNew);
            }
            if (mensajeroIDOld != null && !mensajeroIDOld.equals(mensajeroIDNew)) {
                mensajeroIDOld.getPedidosCollection().remove(model_Pedidos);
                mensajeroIDOld = em.merge(mensajeroIDOld);
            }
            if (mensajeroIDNew != null && !mensajeroIDNew.equals(mensajeroIDOld)) {
                mensajeroIDNew.getPedidosCollection().add(model_Pedidos);
                mensajeroIDNew = em.merge(mensajeroIDNew);
            }
            for (Producto productoCollectionOldModel_Producto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldModel_Producto)) {
                    productoCollectionOldModel_Producto.setPedidoID(null);
                    productoCollectionOldModel_Producto = em.merge(productoCollectionOldModel_Producto);
                }
            }
            for (Producto productoCollectionNewModel_Producto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewModel_Producto)) {
                    Pedidos oldPedidoIDOfProductoCollectionNewModel_Producto = productoCollectionNewModel_Producto.getPedidoID();
                    productoCollectionNewModel_Producto.setPedidoID(model_Pedidos);
                    productoCollectionNewModel_Producto = em.merge(productoCollectionNewModel_Producto);
                    if (oldPedidoIDOfProductoCollectionNewModel_Producto != null && !oldPedidoIDOfProductoCollectionNewModel_Producto.equals(model_Pedidos)) {
                        oldPedidoIDOfProductoCollectionNewModel_Producto.getProductoCollection().remove(productoCollectionNewModel_Producto);
                        oldPedidoIDOfProductoCollectionNewModel_Producto = em.merge(oldPedidoIDOfProductoCollectionNewModel_Producto);
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
                Integer id = model_Pedidos.getPedidoID();
                if (findModel_Pedidos(id) == null) {
                    throw new NonexistentEntityException("The model_Pedidos with id " + id + " no longer exists.");
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
            Pedidos model_Pedidos;
            try {
                model_Pedidos = em.getReference(Pedidos.class, id);
                model_Pedidos.getPedidoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Pedidos with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteID = model_Pedidos.getClienteID();
            if (clienteID != null) {
                clienteID.getPedidosCollection().remove(model_Pedidos);
                clienteID = em.merge(clienteID);
            }
            Despachador despachadorID = model_Pedidos.getDespachadorID();
            if (despachadorID != null) {
                despachadorID.getPedidosCollection().remove(model_Pedidos);
                despachadorID = em.merge(despachadorID);
            }
            HistorialPedidos historialPredidosID = model_Pedidos.getHistorialPredidosID();
            if (historialPredidosID != null) {
                historialPredidosID.getPedidosCollection().remove(model_Pedidos);
                historialPredidosID = em.merge(historialPredidosID);
            }
            Mensajero mensajeroID = model_Pedidos.getMensajeroID();
            if (mensajeroID != null) {
                mensajeroID.getPedidosCollection().remove(model_Pedidos);
                mensajeroID = em.merge(mensajeroID);
            }
            Collection<Producto> productoCollection = model_Pedidos.getProductoCollection();
            for (Producto productoCollectionModel_Producto : productoCollection) {
                productoCollectionModel_Producto.setPedidoID(null);
                productoCollectionModel_Producto = em.merge(productoCollectionModel_Producto);
            }
            em.remove(model_Pedidos);
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

    public List<Pedidos> findModel_PedidosEntities() {
        return findModel_PedidosEntities(true, -1, -1);
    }

    public List<Pedidos> findModel_PedidosEntities(int maxResults, int firstResult) {
        return findModel_PedidosEntities(false, maxResults, firstResult);
    }

    private List<Pedidos> findModel_PedidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedidos.class));
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

    public Pedidos findModel_Pedidos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_PedidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedidos> rt = cq.from(Pedidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
