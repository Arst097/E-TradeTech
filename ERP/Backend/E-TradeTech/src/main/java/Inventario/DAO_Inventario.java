/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Producto;
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
public class DAO_Inventario implements Serializable {

    public DAO_Inventario(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Inventario model_Inventario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Inventario.getDespachadorCollection() == null) {
            model_Inventario.setDespachadorCollection(new ArrayList<Model_Despachador>());
        }
        if (model_Inventario.getProductoCollection() == null) {
            model_Inventario.setProductoCollection(new ArrayList<Model_Producto>());
        }
        if (model_Inventario.getTransaccionInvCollection() == null) {
            model_Inventario.setTransaccionInvCollection(new ArrayList<Model_TransaccionInv>());
        }
        if (model_Inventario.getTransaccionInvCollection1() == null) {
            model_Inventario.setTransaccionInvCollection1(new ArrayList<Model_TransaccionInv>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Almacen almacenID = model_Inventario.getAlmacenID();
            if (almacenID != null) {
                almacenID = em.getReference(almacenID.getClass(), almacenID.getAlmacenID());
                model_Inventario.setAlmacenID(almacenID);
            }
            Collection<Model_Despachador> attachedDespachadorCollection = new ArrayList<Model_Despachador>();
            for (Model_Despachador despachadorCollectionModel_DespachadorToAttach : model_Inventario.getDespachadorCollection()) {
                despachadorCollectionModel_DespachadorToAttach = em.getReference(despachadorCollectionModel_DespachadorToAttach.getClass(), despachadorCollectionModel_DespachadorToAttach.getDespachadorID());
                attachedDespachadorCollection.add(despachadorCollectionModel_DespachadorToAttach);
            }
            model_Inventario.setDespachadorCollection(attachedDespachadorCollection);
            Collection<Model_Producto> attachedProductoCollection = new ArrayList<Model_Producto>();
            for (Model_Producto productoCollectionModel_ProductoToAttach : model_Inventario.getProductoCollection()) {
                productoCollectionModel_ProductoToAttach = em.getReference(productoCollectionModel_ProductoToAttach.getClass(), productoCollectionModel_ProductoToAttach.getProductoID());
                attachedProductoCollection.add(productoCollectionModel_ProductoToAttach);
            }
            model_Inventario.setProductoCollection(attachedProductoCollection);
            Collection<Model_TransaccionInv> attachedTransaccionInvCollection = new ArrayList<Model_TransaccionInv>();
            for (Model_TransaccionInv transaccionInvCollectionModel_TransaccionInvToAttach : model_Inventario.getTransaccionInvCollection()) {
                transaccionInvCollectionModel_TransaccionInvToAttach = em.getReference(transaccionInvCollectionModel_TransaccionInvToAttach.getClass(), transaccionInvCollectionModel_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollection.add(transaccionInvCollectionModel_TransaccionInvToAttach);
            }
            model_Inventario.setTransaccionInvCollection(attachedTransaccionInvCollection);
            Collection<Model_TransaccionInv> attachedTransaccionInvCollection1 = new ArrayList<Model_TransaccionInv>();
            for (Model_TransaccionInv transaccionInvCollection1Model_TransaccionInvToAttach : model_Inventario.getTransaccionInvCollection1()) {
                transaccionInvCollection1Model_TransaccionInvToAttach = em.getReference(transaccionInvCollection1Model_TransaccionInvToAttach.getClass(), transaccionInvCollection1Model_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollection1.add(transaccionInvCollection1Model_TransaccionInvToAttach);
            }
            model_Inventario.setTransaccionInvCollection1(attachedTransaccionInvCollection1);
            em.persist(model_Inventario);
            if (almacenID != null) {
                almacenID.getInventarioCollection().add(model_Inventario);
                almacenID = em.merge(almacenID);
            }
            for (Model_Despachador despachadorCollectionModel_Despachador : model_Inventario.getDespachadorCollection()) {
                Model_Inventario oldInventarioIDOfDespachadorCollectionModel_Despachador = despachadorCollectionModel_Despachador.getInventarioID();
                despachadorCollectionModel_Despachador.setInventarioID(model_Inventario);
                despachadorCollectionModel_Despachador = em.merge(despachadorCollectionModel_Despachador);
                if (oldInventarioIDOfDespachadorCollectionModel_Despachador != null) {
                    oldInventarioIDOfDespachadorCollectionModel_Despachador.getDespachadorCollection().remove(despachadorCollectionModel_Despachador);
                    oldInventarioIDOfDespachadorCollectionModel_Despachador = em.merge(oldInventarioIDOfDespachadorCollectionModel_Despachador);
                }
            }
            for (Model_Producto productoCollectionModel_Producto : model_Inventario.getProductoCollection()) {
                Model_Inventario oldInventarioIDOfProductoCollectionModel_Producto = productoCollectionModel_Producto.getInventarioID();
                productoCollectionModel_Producto.setInventarioID(model_Inventario);
                productoCollectionModel_Producto = em.merge(productoCollectionModel_Producto);
                if (oldInventarioIDOfProductoCollectionModel_Producto != null) {
                    oldInventarioIDOfProductoCollectionModel_Producto.getProductoCollection().remove(productoCollectionModel_Producto);
                    oldInventarioIDOfProductoCollectionModel_Producto = em.merge(oldInventarioIDOfProductoCollectionModel_Producto);
                }
            }
            for (Model_TransaccionInv transaccionInvCollectionModel_TransaccionInv : model_Inventario.getTransaccionInvCollection()) {
                Model_Inventario oldInventarioEmisorIDOfTransaccionInvCollectionModel_TransaccionInv = transaccionInvCollectionModel_TransaccionInv.getInventarioEmisorID();
                transaccionInvCollectionModel_TransaccionInv.setInventarioEmisorID(model_Inventario);
                transaccionInvCollectionModel_TransaccionInv = em.merge(transaccionInvCollectionModel_TransaccionInv);
                if (oldInventarioEmisorIDOfTransaccionInvCollectionModel_TransaccionInv != null) {
                    oldInventarioEmisorIDOfTransaccionInvCollectionModel_TransaccionInv.getTransaccionInvCollection().remove(transaccionInvCollectionModel_TransaccionInv);
                    oldInventarioEmisorIDOfTransaccionInvCollectionModel_TransaccionInv = em.merge(oldInventarioEmisorIDOfTransaccionInvCollectionModel_TransaccionInv);
                }
            }
            for (Model_TransaccionInv transaccionInvCollection1Model_TransaccionInv : model_Inventario.getTransaccionInvCollection1()) {
                Model_Inventario oldInventarioReceptorDOfTransaccionInvCollection1Model_TransaccionInv = transaccionInvCollection1Model_TransaccionInv.getInventarioReceptorD();
                transaccionInvCollection1Model_TransaccionInv.setInventarioReceptorD(model_Inventario);
                transaccionInvCollection1Model_TransaccionInv = em.merge(transaccionInvCollection1Model_TransaccionInv);
                if (oldInventarioReceptorDOfTransaccionInvCollection1Model_TransaccionInv != null) {
                    oldInventarioReceptorDOfTransaccionInvCollection1Model_TransaccionInv.getTransaccionInvCollection1().remove(transaccionInvCollection1Model_TransaccionInv);
                    oldInventarioReceptorDOfTransaccionInvCollection1Model_TransaccionInv = em.merge(oldInventarioReceptorDOfTransaccionInvCollection1Model_TransaccionInv);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Inventario(model_Inventario.getInventarioID()) != null) {
                throw new PreexistingEntityException("Model_Inventario " + model_Inventario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Inventario model_Inventario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Inventario persistentModel_Inventario = em.find(Model_Inventario.class, model_Inventario.getInventarioID());
            Model_Almacen almacenIDOld = persistentModel_Inventario.getAlmacenID();
            Model_Almacen almacenIDNew = model_Inventario.getAlmacenID();
            Collection<Model_Despachador> despachadorCollectionOld = persistentModel_Inventario.getDespachadorCollection();
            Collection<Model_Despachador> despachadorCollectionNew = model_Inventario.getDespachadorCollection();
            Collection<Model_Producto> productoCollectionOld = persistentModel_Inventario.getProductoCollection();
            Collection<Model_Producto> productoCollectionNew = model_Inventario.getProductoCollection();
            Collection<Model_TransaccionInv> transaccionInvCollectionOld = persistentModel_Inventario.getTransaccionInvCollection();
            Collection<Model_TransaccionInv> transaccionInvCollectionNew = model_Inventario.getTransaccionInvCollection();
            Collection<Model_TransaccionInv> transaccionInvCollection1Old = persistentModel_Inventario.getTransaccionInvCollection1();
            Collection<Model_TransaccionInv> transaccionInvCollection1New = model_Inventario.getTransaccionInvCollection1();
            List<String> illegalOrphanMessages = null;
            for (Model_Despachador despachadorCollectionOldModel_Despachador : despachadorCollectionOld) {
                if (!despachadorCollectionNew.contains(despachadorCollectionOldModel_Despachador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Despachador " + despachadorCollectionOldModel_Despachador + " since its inventarioID field is not nullable.");
                }
            }
            for (Model_Producto productoCollectionOldModel_Producto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldModel_Producto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Producto " + productoCollectionOldModel_Producto + " since its inventarioID field is not nullable.");
                }
            }
            for (Model_TransaccionInv transaccionInvCollectionOldModel_TransaccionInv : transaccionInvCollectionOld) {
                if (!transaccionInvCollectionNew.contains(transaccionInvCollectionOldModel_TransaccionInv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_TransaccionInv " + transaccionInvCollectionOldModel_TransaccionInv + " since its inventarioEmisorID field is not nullable.");
                }
            }
            for (Model_TransaccionInv transaccionInvCollection1OldModel_TransaccionInv : transaccionInvCollection1Old) {
                if (!transaccionInvCollection1New.contains(transaccionInvCollection1OldModel_TransaccionInv)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_TransaccionInv " + transaccionInvCollection1OldModel_TransaccionInv + " since its inventarioReceptorD field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (almacenIDNew != null) {
                almacenIDNew = em.getReference(almacenIDNew.getClass(), almacenIDNew.getAlmacenID());
                model_Inventario.setAlmacenID(almacenIDNew);
            }
            Collection<Model_Despachador> attachedDespachadorCollectionNew = new ArrayList<Model_Despachador>();
            for (Model_Despachador despachadorCollectionNewModel_DespachadorToAttach : despachadorCollectionNew) {
                despachadorCollectionNewModel_DespachadorToAttach = em.getReference(despachadorCollectionNewModel_DespachadorToAttach.getClass(), despachadorCollectionNewModel_DespachadorToAttach.getDespachadorID());
                attachedDespachadorCollectionNew.add(despachadorCollectionNewModel_DespachadorToAttach);
            }
            despachadorCollectionNew = attachedDespachadorCollectionNew;
            model_Inventario.setDespachadorCollection(despachadorCollectionNew);
            Collection<Model_Producto> attachedProductoCollectionNew = new ArrayList<Model_Producto>();
            for (Model_Producto productoCollectionNewModel_ProductoToAttach : productoCollectionNew) {
                productoCollectionNewModel_ProductoToAttach = em.getReference(productoCollectionNewModel_ProductoToAttach.getClass(), productoCollectionNewModel_ProductoToAttach.getProductoID());
                attachedProductoCollectionNew.add(productoCollectionNewModel_ProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            model_Inventario.setProductoCollection(productoCollectionNew);
            Collection<Model_TransaccionInv> attachedTransaccionInvCollectionNew = new ArrayList<Model_TransaccionInv>();
            for (Model_TransaccionInv transaccionInvCollectionNewModel_TransaccionInvToAttach : transaccionInvCollectionNew) {
                transaccionInvCollectionNewModel_TransaccionInvToAttach = em.getReference(transaccionInvCollectionNewModel_TransaccionInvToAttach.getClass(), transaccionInvCollectionNewModel_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollectionNew.add(transaccionInvCollectionNewModel_TransaccionInvToAttach);
            }
            transaccionInvCollectionNew = attachedTransaccionInvCollectionNew;
            model_Inventario.setTransaccionInvCollection(transaccionInvCollectionNew);
            Collection<Model_TransaccionInv> attachedTransaccionInvCollection1New = new ArrayList<Model_TransaccionInv>();
            for (Model_TransaccionInv transaccionInvCollection1NewModel_TransaccionInvToAttach : transaccionInvCollection1New) {
                transaccionInvCollection1NewModel_TransaccionInvToAttach = em.getReference(transaccionInvCollection1NewModel_TransaccionInvToAttach.getClass(), transaccionInvCollection1NewModel_TransaccionInvToAttach.getTransaccionInvID());
                attachedTransaccionInvCollection1New.add(transaccionInvCollection1NewModel_TransaccionInvToAttach);
            }
            transaccionInvCollection1New = attachedTransaccionInvCollection1New;
            model_Inventario.setTransaccionInvCollection1(transaccionInvCollection1New);
            model_Inventario = em.merge(model_Inventario);
            if (almacenIDOld != null && !almacenIDOld.equals(almacenIDNew)) {
                almacenIDOld.getInventarioCollection().remove(model_Inventario);
                almacenIDOld = em.merge(almacenIDOld);
            }
            if (almacenIDNew != null && !almacenIDNew.equals(almacenIDOld)) {
                almacenIDNew.getInventarioCollection().add(model_Inventario);
                almacenIDNew = em.merge(almacenIDNew);
            }
            for (Model_Despachador despachadorCollectionNewModel_Despachador : despachadorCollectionNew) {
                if (!despachadorCollectionOld.contains(despachadorCollectionNewModel_Despachador)) {
                    Model_Inventario oldInventarioIDOfDespachadorCollectionNewModel_Despachador = despachadorCollectionNewModel_Despachador.getInventarioID();
                    despachadorCollectionNewModel_Despachador.setInventarioID(model_Inventario);
                    despachadorCollectionNewModel_Despachador = em.merge(despachadorCollectionNewModel_Despachador);
                    if (oldInventarioIDOfDespachadorCollectionNewModel_Despachador != null && !oldInventarioIDOfDespachadorCollectionNewModel_Despachador.equals(model_Inventario)) {
                        oldInventarioIDOfDespachadorCollectionNewModel_Despachador.getDespachadorCollection().remove(despachadorCollectionNewModel_Despachador);
                        oldInventarioIDOfDespachadorCollectionNewModel_Despachador = em.merge(oldInventarioIDOfDespachadorCollectionNewModel_Despachador);
                    }
                }
            }
            for (Model_Producto productoCollectionNewModel_Producto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewModel_Producto)) {
                    Model_Inventario oldInventarioIDOfProductoCollectionNewModel_Producto = productoCollectionNewModel_Producto.getInventarioID();
                    productoCollectionNewModel_Producto.setInventarioID(model_Inventario);
                    productoCollectionNewModel_Producto = em.merge(productoCollectionNewModel_Producto);
                    if (oldInventarioIDOfProductoCollectionNewModel_Producto != null && !oldInventarioIDOfProductoCollectionNewModel_Producto.equals(model_Inventario)) {
                        oldInventarioIDOfProductoCollectionNewModel_Producto.getProductoCollection().remove(productoCollectionNewModel_Producto);
                        oldInventarioIDOfProductoCollectionNewModel_Producto = em.merge(oldInventarioIDOfProductoCollectionNewModel_Producto);
                    }
                }
            }
            for (Model_TransaccionInv transaccionInvCollectionNewModel_TransaccionInv : transaccionInvCollectionNew) {
                if (!transaccionInvCollectionOld.contains(transaccionInvCollectionNewModel_TransaccionInv)) {
                    Model_Inventario oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv = transaccionInvCollectionNewModel_TransaccionInv.getInventarioEmisorID();
                    transaccionInvCollectionNewModel_TransaccionInv.setInventarioEmisorID(model_Inventario);
                    transaccionInvCollectionNewModel_TransaccionInv = em.merge(transaccionInvCollectionNewModel_TransaccionInv);
                    if (oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv != null && !oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv.equals(model_Inventario)) {
                        oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv.getTransaccionInvCollection().remove(transaccionInvCollectionNewModel_TransaccionInv);
                        oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv = em.merge(oldInventarioEmisorIDOfTransaccionInvCollectionNewModel_TransaccionInv);
                    }
                }
            }
            for (Model_TransaccionInv transaccionInvCollection1NewModel_TransaccionInv : transaccionInvCollection1New) {
                if (!transaccionInvCollection1Old.contains(transaccionInvCollection1NewModel_TransaccionInv)) {
                    Model_Inventario oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv = transaccionInvCollection1NewModel_TransaccionInv.getInventarioReceptorD();
                    transaccionInvCollection1NewModel_TransaccionInv.setInventarioReceptorD(model_Inventario);
                    transaccionInvCollection1NewModel_TransaccionInv = em.merge(transaccionInvCollection1NewModel_TransaccionInv);
                    if (oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv != null && !oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv.equals(model_Inventario)) {
                        oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv.getTransaccionInvCollection1().remove(transaccionInvCollection1NewModel_TransaccionInv);
                        oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv = em.merge(oldInventarioReceptorDOfTransaccionInvCollection1NewModel_TransaccionInv);
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
                Integer id = model_Inventario.getInventarioID();
                if (findModel_Inventario(id) == null) {
                    throw new NonexistentEntityException("The model_Inventario with id " + id + " no longer exists.");
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
            Model_Inventario model_Inventario;
            try {
                model_Inventario = em.getReference(Model_Inventario.class, id);
                model_Inventario.getInventarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Inventario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Model_Despachador> despachadorCollectionOrphanCheck = model_Inventario.getDespachadorCollection();
            for (Model_Despachador despachadorCollectionOrphanCheckModel_Despachador : despachadorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Inventario (" + model_Inventario + ") cannot be destroyed since the Model_Despachador " + despachadorCollectionOrphanCheckModel_Despachador + " in its despachadorCollection field has a non-nullable inventarioID field.");
            }
            Collection<Model_Producto> productoCollectionOrphanCheck = model_Inventario.getProductoCollection();
            for (Model_Producto productoCollectionOrphanCheckModel_Producto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Inventario (" + model_Inventario + ") cannot be destroyed since the Model_Producto " + productoCollectionOrphanCheckModel_Producto + " in its productoCollection field has a non-nullable inventarioID field.");
            }
            Collection<Model_TransaccionInv> transaccionInvCollectionOrphanCheck = model_Inventario.getTransaccionInvCollection();
            for (Model_TransaccionInv transaccionInvCollectionOrphanCheckModel_TransaccionInv : transaccionInvCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Inventario (" + model_Inventario + ") cannot be destroyed since the Model_TransaccionInv " + transaccionInvCollectionOrphanCheckModel_TransaccionInv + " in its transaccionInvCollection field has a non-nullable inventarioEmisorID field.");
            }
            Collection<Model_TransaccionInv> transaccionInvCollection1OrphanCheck = model_Inventario.getTransaccionInvCollection1();
            for (Model_TransaccionInv transaccionInvCollection1OrphanCheckModel_TransaccionInv : transaccionInvCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Inventario (" + model_Inventario + ") cannot be destroyed since the Model_TransaccionInv " + transaccionInvCollection1OrphanCheckModel_TransaccionInv + " in its transaccionInvCollection1 field has a non-nullable inventarioReceptorD field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Model_Almacen almacenID = model_Inventario.getAlmacenID();
            if (almacenID != null) {
                almacenID.getInventarioCollection().remove(model_Inventario);
                almacenID = em.merge(almacenID);
            }
            em.remove(model_Inventario);
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

    public List<Model_Inventario> findModel_InventarioEntities() {
        return findModel_InventarioEntities(true, -1, -1);
    }

    public List<Model_Inventario> findModel_InventarioEntities(int maxResults, int firstResult) {
        return findModel_InventarioEntities(false, maxResults, firstResult);
    }

    private List<Model_Inventario> findModel_InventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Inventario.class));
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

    public Model_Inventario findModel_Inventario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Inventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_InventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Inventario> rt = cq.from(Model_Inventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
