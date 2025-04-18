/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

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
public class DAO_Almacen implements Serializable {

    public DAO_Almacen(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Almacen model_Almacen) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Almacen.getDespachadorCollection() == null) {
            model_Almacen.setDespachadorCollection(new ArrayList<Model_Despachador>());
        }
        if (model_Almacen.getGestoresCollection() == null) {
            model_Almacen.setGestoresCollection(new ArrayList<Model_Gestores>());
        }
        if (model_Almacen.getInventarioCollection() == null) {
            model_Almacen.setInventarioCollection(new ArrayList<Model_Inventario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Model_Despachador> attachedDespachadorCollection = new ArrayList<Model_Despachador>();
            for (Model_Despachador despachadorCollectionModel_DespachadorToAttach : model_Almacen.getDespachadorCollection()) {
                despachadorCollectionModel_DespachadorToAttach = em.getReference(despachadorCollectionModel_DespachadorToAttach.getClass(), despachadorCollectionModel_DespachadorToAttach.getDespachadorID());
                attachedDespachadorCollection.add(despachadorCollectionModel_DespachadorToAttach);
            }
            model_Almacen.setDespachadorCollection(attachedDespachadorCollection);
            Collection<Model_Gestores> attachedGestoresCollection = new ArrayList<Model_Gestores>();
            for (Model_Gestores gestoresCollectionModel_GestoresToAttach : model_Almacen.getGestoresCollection()) {
                gestoresCollectionModel_GestoresToAttach = em.getReference(gestoresCollectionModel_GestoresToAttach.getClass(), gestoresCollectionModel_GestoresToAttach.getGestorID());
                attachedGestoresCollection.add(gestoresCollectionModel_GestoresToAttach);
            }
            model_Almacen.setGestoresCollection(attachedGestoresCollection);
            Collection<Model_Inventario> attachedInventarioCollection = new ArrayList<Model_Inventario>();
            for (Model_Inventario inventarioCollectionModel_InventarioToAttach : model_Almacen.getInventarioCollection()) {
                inventarioCollectionModel_InventarioToAttach = em.getReference(inventarioCollectionModel_InventarioToAttach.getClass(), inventarioCollectionModel_InventarioToAttach.getInventarioID());
                attachedInventarioCollection.add(inventarioCollectionModel_InventarioToAttach);
            }
            model_Almacen.setInventarioCollection(attachedInventarioCollection);
            em.persist(model_Almacen);
            for (Model_Despachador despachadorCollectionModel_Despachador : model_Almacen.getDespachadorCollection()) {
                Model_Almacen oldAlmacenIDOfDespachadorCollectionModel_Despachador = despachadorCollectionModel_Despachador.getAlmacenID();
                despachadorCollectionModel_Despachador.setAlmacenID(model_Almacen);
                despachadorCollectionModel_Despachador = em.merge(despachadorCollectionModel_Despachador);
                if (oldAlmacenIDOfDespachadorCollectionModel_Despachador != null) {
                    oldAlmacenIDOfDespachadorCollectionModel_Despachador.getDespachadorCollection().remove(despachadorCollectionModel_Despachador);
                    oldAlmacenIDOfDespachadorCollectionModel_Despachador = em.merge(oldAlmacenIDOfDespachadorCollectionModel_Despachador);
                }
            }
            for (Model_Gestores gestoresCollectionModel_Gestores : model_Almacen.getGestoresCollection()) {
                Model_Almacen oldAlmacenIDOfGestoresCollectionModel_Gestores = gestoresCollectionModel_Gestores.getAlmacenID();
                gestoresCollectionModel_Gestores.setAlmacenID(model_Almacen);
                gestoresCollectionModel_Gestores = em.merge(gestoresCollectionModel_Gestores);
                if (oldAlmacenIDOfGestoresCollectionModel_Gestores != null) {
                    oldAlmacenIDOfGestoresCollectionModel_Gestores.getGestoresCollection().remove(gestoresCollectionModel_Gestores);
                    oldAlmacenIDOfGestoresCollectionModel_Gestores = em.merge(oldAlmacenIDOfGestoresCollectionModel_Gestores);
                }
            }
            for (Model_Inventario inventarioCollectionModel_Inventario : model_Almacen.getInventarioCollection()) {
                Model_Almacen oldAlmacenIDOfInventarioCollectionModel_Inventario = inventarioCollectionModel_Inventario.getAlmacenID();
                inventarioCollectionModel_Inventario.setAlmacenID(model_Almacen);
                inventarioCollectionModel_Inventario = em.merge(inventarioCollectionModel_Inventario);
                if (oldAlmacenIDOfInventarioCollectionModel_Inventario != null) {
                    oldAlmacenIDOfInventarioCollectionModel_Inventario.getInventarioCollection().remove(inventarioCollectionModel_Inventario);
                    oldAlmacenIDOfInventarioCollectionModel_Inventario = em.merge(oldAlmacenIDOfInventarioCollectionModel_Inventario);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Almacen(model_Almacen.getAlmacenID()) != null) {
                throw new PreexistingEntityException("Model_Almacen " + model_Almacen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Almacen model_Almacen) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Almacen persistentModel_Almacen = em.find(Model_Almacen.class, model_Almacen.getAlmacenID());
            Collection<Model_Despachador> despachadorCollectionOld = persistentModel_Almacen.getDespachadorCollection();
            Collection<Model_Despachador> despachadorCollectionNew = model_Almacen.getDespachadorCollection();
            Collection<Model_Gestores> gestoresCollectionOld = persistentModel_Almacen.getGestoresCollection();
            Collection<Model_Gestores> gestoresCollectionNew = model_Almacen.getGestoresCollection();
            Collection<Model_Inventario> inventarioCollectionOld = persistentModel_Almacen.getInventarioCollection();
            Collection<Model_Inventario> inventarioCollectionNew = model_Almacen.getInventarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Model_Despachador despachadorCollectionOldModel_Despachador : despachadorCollectionOld) {
                if (!despachadorCollectionNew.contains(despachadorCollectionOldModel_Despachador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Despachador " + despachadorCollectionOldModel_Despachador + " since its almacenID field is not nullable.");
                }
            }
            for (Model_Gestores gestoresCollectionOldModel_Gestores : gestoresCollectionOld) {
                if (!gestoresCollectionNew.contains(gestoresCollectionOldModel_Gestores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Gestores " + gestoresCollectionOldModel_Gestores + " since its almacenID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Model_Despachador> attachedDespachadorCollectionNew = new ArrayList<Model_Despachador>();
            for (Model_Despachador despachadorCollectionNewModel_DespachadorToAttach : despachadorCollectionNew) {
                despachadorCollectionNewModel_DespachadorToAttach = em.getReference(despachadorCollectionNewModel_DespachadorToAttach.getClass(), despachadorCollectionNewModel_DespachadorToAttach.getDespachadorID());
                attachedDespachadorCollectionNew.add(despachadorCollectionNewModel_DespachadorToAttach);
            }
            despachadorCollectionNew = attachedDespachadorCollectionNew;
            model_Almacen.setDespachadorCollection(despachadorCollectionNew);
            Collection<Model_Gestores> attachedGestoresCollectionNew = new ArrayList<Model_Gestores>();
            for (Model_Gestores gestoresCollectionNewModel_GestoresToAttach : gestoresCollectionNew) {
                gestoresCollectionNewModel_GestoresToAttach = em.getReference(gestoresCollectionNewModel_GestoresToAttach.getClass(), gestoresCollectionNewModel_GestoresToAttach.getGestorID());
                attachedGestoresCollectionNew.add(gestoresCollectionNewModel_GestoresToAttach);
            }
            gestoresCollectionNew = attachedGestoresCollectionNew;
            model_Almacen.setGestoresCollection(gestoresCollectionNew);
            Collection<Model_Inventario> attachedInventarioCollectionNew = new ArrayList<Model_Inventario>();
            for (Model_Inventario inventarioCollectionNewModel_InventarioToAttach : inventarioCollectionNew) {
                inventarioCollectionNewModel_InventarioToAttach = em.getReference(inventarioCollectionNewModel_InventarioToAttach.getClass(), inventarioCollectionNewModel_InventarioToAttach.getInventarioID());
                attachedInventarioCollectionNew.add(inventarioCollectionNewModel_InventarioToAttach);
            }
            inventarioCollectionNew = attachedInventarioCollectionNew;
            model_Almacen.setInventarioCollection(inventarioCollectionNew);
            model_Almacen = em.merge(model_Almacen);
            for (Model_Despachador despachadorCollectionNewModel_Despachador : despachadorCollectionNew) {
                if (!despachadorCollectionOld.contains(despachadorCollectionNewModel_Despachador)) {
                    Model_Almacen oldAlmacenIDOfDespachadorCollectionNewModel_Despachador = despachadorCollectionNewModel_Despachador.getAlmacenID();
                    despachadorCollectionNewModel_Despachador.setAlmacenID(model_Almacen);
                    despachadorCollectionNewModel_Despachador = em.merge(despachadorCollectionNewModel_Despachador);
                    if (oldAlmacenIDOfDespachadorCollectionNewModel_Despachador != null && !oldAlmacenIDOfDespachadorCollectionNewModel_Despachador.equals(model_Almacen)) {
                        oldAlmacenIDOfDespachadorCollectionNewModel_Despachador.getDespachadorCollection().remove(despachadorCollectionNewModel_Despachador);
                        oldAlmacenIDOfDespachadorCollectionNewModel_Despachador = em.merge(oldAlmacenIDOfDespachadorCollectionNewModel_Despachador);
                    }
                }
            }
            for (Model_Gestores gestoresCollectionNewModel_Gestores : gestoresCollectionNew) {
                if (!gestoresCollectionOld.contains(gestoresCollectionNewModel_Gestores)) {
                    Model_Almacen oldAlmacenIDOfGestoresCollectionNewModel_Gestores = gestoresCollectionNewModel_Gestores.getAlmacenID();
                    gestoresCollectionNewModel_Gestores.setAlmacenID(model_Almacen);
                    gestoresCollectionNewModel_Gestores = em.merge(gestoresCollectionNewModel_Gestores);
                    if (oldAlmacenIDOfGestoresCollectionNewModel_Gestores != null && !oldAlmacenIDOfGestoresCollectionNewModel_Gestores.equals(model_Almacen)) {
                        oldAlmacenIDOfGestoresCollectionNewModel_Gestores.getGestoresCollection().remove(gestoresCollectionNewModel_Gestores);
                        oldAlmacenIDOfGestoresCollectionNewModel_Gestores = em.merge(oldAlmacenIDOfGestoresCollectionNewModel_Gestores);
                    }
                }
            }
            for (Model_Inventario inventarioCollectionOldModel_Inventario : inventarioCollectionOld) {
                if (!inventarioCollectionNew.contains(inventarioCollectionOldModel_Inventario)) {
                    inventarioCollectionOldModel_Inventario.setAlmacenID(null);
                    inventarioCollectionOldModel_Inventario = em.merge(inventarioCollectionOldModel_Inventario);
                }
            }
            for (Model_Inventario inventarioCollectionNewModel_Inventario : inventarioCollectionNew) {
                if (!inventarioCollectionOld.contains(inventarioCollectionNewModel_Inventario)) {
                    Model_Almacen oldAlmacenIDOfInventarioCollectionNewModel_Inventario = inventarioCollectionNewModel_Inventario.getAlmacenID();
                    inventarioCollectionNewModel_Inventario.setAlmacenID(model_Almacen);
                    inventarioCollectionNewModel_Inventario = em.merge(inventarioCollectionNewModel_Inventario);
                    if (oldAlmacenIDOfInventarioCollectionNewModel_Inventario != null && !oldAlmacenIDOfInventarioCollectionNewModel_Inventario.equals(model_Almacen)) {
                        oldAlmacenIDOfInventarioCollectionNewModel_Inventario.getInventarioCollection().remove(inventarioCollectionNewModel_Inventario);
                        oldAlmacenIDOfInventarioCollectionNewModel_Inventario = em.merge(oldAlmacenIDOfInventarioCollectionNewModel_Inventario);
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
                Integer id = model_Almacen.getAlmacenID();
                if (findModel_Almacen(id) == null) {
                    throw new NonexistentEntityException("The model_Almacen with id " + id + " no longer exists.");
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
            Model_Almacen model_Almacen;
            try {
                model_Almacen = em.getReference(Model_Almacen.class, id);
                model_Almacen.getAlmacenID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Almacen with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Model_Despachador> despachadorCollectionOrphanCheck = model_Almacen.getDespachadorCollection();
            for (Model_Despachador despachadorCollectionOrphanCheckModel_Despachador : despachadorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Almacen (" + model_Almacen + ") cannot be destroyed since the Model_Despachador " + despachadorCollectionOrphanCheckModel_Despachador + " in its despachadorCollection field has a non-nullable almacenID field.");
            }
            Collection<Model_Gestores> gestoresCollectionOrphanCheck = model_Almacen.getGestoresCollection();
            for (Model_Gestores gestoresCollectionOrphanCheckModel_Gestores : gestoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Almacen (" + model_Almacen + ") cannot be destroyed since the Model_Gestores " + gestoresCollectionOrphanCheckModel_Gestores + " in its gestoresCollection field has a non-nullable almacenID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Model_Inventario> inventarioCollection = model_Almacen.getInventarioCollection();
            for (Model_Inventario inventarioCollectionModel_Inventario : inventarioCollection) {
                inventarioCollectionModel_Inventario.setAlmacenID(null);
                inventarioCollectionModel_Inventario = em.merge(inventarioCollectionModel_Inventario);
            }
            em.remove(model_Almacen);
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

    public List<Model_Almacen> findModel_AlmacenEntities() {
        return findModel_AlmacenEntities(true, -1, -1);
    }

    public List<Model_Almacen> findModel_AlmacenEntities(int maxResults, int firstResult) {
        return findModel_AlmacenEntities(false, maxResults, firstResult);
    }

    private List<Model_Almacen> findModel_AlmacenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Almacen.class));
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

    public Model_Almacen findModel_Almacen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Almacen.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_AlmacenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Almacen> rt = cq.from(Model_Almacen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
