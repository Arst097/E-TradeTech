/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Inventario.Model_Inventario;
import Uso_Comun.Model_Producto;
import Uso_Comun.Model_Pedidos;
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
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Producto implements Serializable {

    public DAO_Producto(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Producto model_Producto) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Inventario inventarioID = model_Producto.getInventarioID();
            if (inventarioID != null) {
                inventarioID = em.getReference(inventarioID.getClass(), inventarioID.getInventarioID());
                model_Producto.setInventarioID(inventarioID);
            }
            Model_Pedidos pedidoID = model_Producto.getPedidoID();
            if (pedidoID != null) {
                pedidoID = em.getReference(pedidoID.getClass(), pedidoID.getPedidoID());
                model_Producto.setPedidoID(pedidoID);
            }
            em.persist(model_Producto);
            if (inventarioID != null) {
                inventarioID.getProductoCollection().add(model_Producto);
                inventarioID = em.merge(inventarioID);
            }
            if (pedidoID != null) {
                pedidoID.getProductoCollection().add(model_Producto);
                pedidoID = em.merge(pedidoID);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Producto(model_Producto.getProductoID()) != null) {
                throw new PreexistingEntityException("Model_Producto " + model_Producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Producto model_Producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Producto persistentModel_Producto = em.find(Model_Producto.class, model_Producto.getProductoID());
            Model_Inventario inventarioIDOld = persistentModel_Producto.getInventarioID();
            Model_Inventario inventarioIDNew = model_Producto.getInventarioID();
            Model_Pedidos pedidoIDOld = persistentModel_Producto.getPedidoID();
            Model_Pedidos pedidoIDNew = model_Producto.getPedidoID();
            if (inventarioIDNew != null) {
                inventarioIDNew = em.getReference(inventarioIDNew.getClass(), inventarioIDNew.getInventarioID());
                model_Producto.setInventarioID(inventarioIDNew);
            }
            if (pedidoIDNew != null) {
                pedidoIDNew = em.getReference(pedidoIDNew.getClass(), pedidoIDNew.getPedidoID());
                model_Producto.setPedidoID(pedidoIDNew);
            }
            model_Producto = em.merge(model_Producto);
            if (inventarioIDOld != null && !inventarioIDOld.equals(inventarioIDNew)) {
                inventarioIDOld.getProductoCollection().remove(model_Producto);
                inventarioIDOld = em.merge(inventarioIDOld);
            }
            if (inventarioIDNew != null && !inventarioIDNew.equals(inventarioIDOld)) {
                inventarioIDNew.getProductoCollection().add(model_Producto);
                inventarioIDNew = em.merge(inventarioIDNew);
            }
            if (pedidoIDOld != null && !pedidoIDOld.equals(pedidoIDNew)) {
                pedidoIDOld.getProductoCollection().remove(model_Producto);
                pedidoIDOld = em.merge(pedidoIDOld);
            }
            if (pedidoIDNew != null && !pedidoIDNew.equals(pedidoIDOld)) {
                pedidoIDNew.getProductoCollection().add(model_Producto);
                pedidoIDNew = em.merge(pedidoIDNew);
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
                Integer id = model_Producto.getProductoID();
                if (findModel_Producto(id) == null) {
                    throw new NonexistentEntityException("The model_Producto with id " + id + " no longer exists.");
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
            Model_Producto model_Producto;
            try {
                model_Producto = em.getReference(Model_Producto.class, id);
                model_Producto.getProductoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Producto with id " + id + " no longer exists.", enfe);
            }
            Model_Inventario inventarioID = model_Producto.getInventarioID();
            if (inventarioID != null) {
                inventarioID.getProductoCollection().remove(model_Producto);
                inventarioID = em.merge(inventarioID);
            }
            Model_Pedidos pedidoID = model_Producto.getPedidoID();
            if (pedidoID != null) {
                pedidoID.getProductoCollection().remove(model_Producto);
                pedidoID = em.merge(pedidoID);
            }
            em.remove(model_Producto);
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

    public List<Model_Producto> findModel_ProductoEntities() {
        return findModel_ProductoEntities(true, -1, -1);
    }

    public List<Model_Producto> findModel_ProductoEntities(int maxResults, int firstResult) {
        return findModel_ProductoEntities(false, maxResults, firstResult);
    }

    private List<Model_Producto> findModel_ProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Producto.class));
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

    public Model_Producto findModel_Producto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_ProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Producto> rt = cq.from(Model_Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
