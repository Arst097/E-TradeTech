/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Inventario.Model_Despachador;
import Inventario.Model_Despachador;
import Inventario.Model_Gestores;
import Inventario.Model_Gestores;
import Uso_Comun.Model_Usuario;
import Uso_Comun.Model_Cliente;
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
public class DAO_Usuario implements Serializable {

    public DAO_Usuario(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Model_Usuario model_Usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (model_Usuario.getDespachadorCollection() == null) {
            model_Usuario.setDespachadorCollection(new ArrayList<Model_Despachador>());
        }
        if (model_Usuario.getGestoresCollection() == null) {
            model_Usuario.setGestoresCollection(new ArrayList<Model_Gestores>());
        }
        if (model_Usuario.getClienteCollection() == null) {
            model_Usuario.setClienteCollection(new ArrayList<Model_Cliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Model_Despachador> attachedDespachadorCollection = new ArrayList<Model_Despachador>();
            for (Model_Despachador despachadorCollectionModel_DespachadorToAttach : model_Usuario.getDespachadorCollection()) {
                despachadorCollectionModel_DespachadorToAttach = em.getReference(despachadorCollectionModel_DespachadorToAttach.getClass(), despachadorCollectionModel_DespachadorToAttach.getDespachadorID());
                attachedDespachadorCollection.add(despachadorCollectionModel_DespachadorToAttach);
            }
            model_Usuario.setDespachadorCollection(attachedDespachadorCollection);
            Collection<Model_Gestores> attachedGestoresCollection = new ArrayList<Model_Gestores>();
            for (Model_Gestores gestoresCollectionModel_GestoresToAttach : model_Usuario.getGestoresCollection()) {
                gestoresCollectionModel_GestoresToAttach = em.getReference(gestoresCollectionModel_GestoresToAttach.getClass(), gestoresCollectionModel_GestoresToAttach.getGestorID());
                attachedGestoresCollection.add(gestoresCollectionModel_GestoresToAttach);
            }
            model_Usuario.setGestoresCollection(attachedGestoresCollection);
            Collection<Model_Cliente> attachedClienteCollection = new ArrayList<Model_Cliente>();
            for (Model_Cliente clienteCollectionModel_ClienteToAttach : model_Usuario.getClienteCollection()) {
                clienteCollectionModel_ClienteToAttach = em.getReference(clienteCollectionModel_ClienteToAttach.getClass(), clienteCollectionModel_ClienteToAttach.getClienteID());
                attachedClienteCollection.add(clienteCollectionModel_ClienteToAttach);
            }
            model_Usuario.setClienteCollection(attachedClienteCollection);
            em.persist(model_Usuario);
            for (Model_Despachador despachadorCollectionModel_Despachador : model_Usuario.getDespachadorCollection()) {
                Model_Usuario oldUsuarioUsuarioidOfDespachadorCollectionModel_Despachador = despachadorCollectionModel_Despachador.getUsuarioUsuarioid();
                despachadorCollectionModel_Despachador.setUsuarioUsuarioid(model_Usuario);
                despachadorCollectionModel_Despachador = em.merge(despachadorCollectionModel_Despachador);
                if (oldUsuarioUsuarioidOfDespachadorCollectionModel_Despachador != null) {
                    oldUsuarioUsuarioidOfDespachadorCollectionModel_Despachador.getDespachadorCollection().remove(despachadorCollectionModel_Despachador);
                    oldUsuarioUsuarioidOfDespachadorCollectionModel_Despachador = em.merge(oldUsuarioUsuarioidOfDespachadorCollectionModel_Despachador);
                }
            }
            for (Model_Gestores gestoresCollectionModel_Gestores : model_Usuario.getGestoresCollection()) {
                Model_Usuario oldUsuarioUsuarioidOfGestoresCollectionModel_Gestores = gestoresCollectionModel_Gestores.getUsuarioUsuarioid();
                gestoresCollectionModel_Gestores.setUsuarioUsuarioid(model_Usuario);
                gestoresCollectionModel_Gestores = em.merge(gestoresCollectionModel_Gestores);
                if (oldUsuarioUsuarioidOfGestoresCollectionModel_Gestores != null) {
                    oldUsuarioUsuarioidOfGestoresCollectionModel_Gestores.getGestoresCollection().remove(gestoresCollectionModel_Gestores);
                    oldUsuarioUsuarioidOfGestoresCollectionModel_Gestores = em.merge(oldUsuarioUsuarioidOfGestoresCollectionModel_Gestores);
                }
            }
            for (Model_Cliente clienteCollectionModel_Cliente : model_Usuario.getClienteCollection()) {
                Model_Usuario oldUsuarioUsuarioidOfClienteCollectionModel_Cliente = clienteCollectionModel_Cliente.getUsuarioUsuarioid();
                clienteCollectionModel_Cliente.setUsuarioUsuarioid(model_Usuario);
                clienteCollectionModel_Cliente = em.merge(clienteCollectionModel_Cliente);
                if (oldUsuarioUsuarioidOfClienteCollectionModel_Cliente != null) {
                    oldUsuarioUsuarioidOfClienteCollectionModel_Cliente.getClienteCollection().remove(clienteCollectionModel_Cliente);
                    oldUsuarioUsuarioidOfClienteCollectionModel_Cliente = em.merge(oldUsuarioUsuarioidOfClienteCollectionModel_Cliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Usuario(model_Usuario.getUsuarioid()) != null) {
                throw new PreexistingEntityException("Model_Usuario " + model_Usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Model_Usuario model_Usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Model_Usuario persistentModel_Usuario = em.find(Model_Usuario.class, model_Usuario.getUsuarioid());
            Collection<Model_Despachador> despachadorCollectionOld = persistentModel_Usuario.getDespachadorCollection();
            Collection<Model_Despachador> despachadorCollectionNew = model_Usuario.getDespachadorCollection();
            Collection<Model_Gestores> gestoresCollectionOld = persistentModel_Usuario.getGestoresCollection();
            Collection<Model_Gestores> gestoresCollectionNew = model_Usuario.getGestoresCollection();
            Collection<Model_Cliente> clienteCollectionOld = persistentModel_Usuario.getClienteCollection();
            Collection<Model_Cliente> clienteCollectionNew = model_Usuario.getClienteCollection();
            List<String> illegalOrphanMessages = null;
            for (Model_Despachador despachadorCollectionOldModel_Despachador : despachadorCollectionOld) {
                if (!despachadorCollectionNew.contains(despachadorCollectionOldModel_Despachador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Despachador " + despachadorCollectionOldModel_Despachador + " since its usuarioUsuarioid field is not nullable.");
                }
            }
            for (Model_Gestores gestoresCollectionOldModel_Gestores : gestoresCollectionOld) {
                if (!gestoresCollectionNew.contains(gestoresCollectionOldModel_Gestores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Gestores " + gestoresCollectionOldModel_Gestores + " since its usuarioUsuarioid field is not nullable.");
                }
            }
            for (Model_Cliente clienteCollectionOldModel_Cliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(clienteCollectionOldModel_Cliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Model_Cliente " + clienteCollectionOldModel_Cliente + " since its usuarioUsuarioid field is not nullable.");
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
            model_Usuario.setDespachadorCollection(despachadorCollectionNew);
            Collection<Model_Gestores> attachedGestoresCollectionNew = new ArrayList<Model_Gestores>();
            for (Model_Gestores gestoresCollectionNewModel_GestoresToAttach : gestoresCollectionNew) {
                gestoresCollectionNewModel_GestoresToAttach = em.getReference(gestoresCollectionNewModel_GestoresToAttach.getClass(), gestoresCollectionNewModel_GestoresToAttach.getGestorID());
                attachedGestoresCollectionNew.add(gestoresCollectionNewModel_GestoresToAttach);
            }
            gestoresCollectionNew = attachedGestoresCollectionNew;
            model_Usuario.setGestoresCollection(gestoresCollectionNew);
            Collection<Model_Cliente> attachedClienteCollectionNew = new ArrayList<Model_Cliente>();
            for (Model_Cliente clienteCollectionNewModel_ClienteToAttach : clienteCollectionNew) {
                clienteCollectionNewModel_ClienteToAttach = em.getReference(clienteCollectionNewModel_ClienteToAttach.getClass(), clienteCollectionNewModel_ClienteToAttach.getClienteID());
                attachedClienteCollectionNew.add(clienteCollectionNewModel_ClienteToAttach);
            }
            clienteCollectionNew = attachedClienteCollectionNew;
            model_Usuario.setClienteCollection(clienteCollectionNew);
            model_Usuario = em.merge(model_Usuario);
            for (Model_Despachador despachadorCollectionNewModel_Despachador : despachadorCollectionNew) {
                if (!despachadorCollectionOld.contains(despachadorCollectionNewModel_Despachador)) {
                    Model_Usuario oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador = despachadorCollectionNewModel_Despachador.getUsuarioUsuarioid();
                    despachadorCollectionNewModel_Despachador.setUsuarioUsuarioid(model_Usuario);
                    despachadorCollectionNewModel_Despachador = em.merge(despachadorCollectionNewModel_Despachador);
                    if (oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador != null && !oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador.equals(model_Usuario)) {
                        oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador.getDespachadorCollection().remove(despachadorCollectionNewModel_Despachador);
                        oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador = em.merge(oldUsuarioUsuarioidOfDespachadorCollectionNewModel_Despachador);
                    }
                }
            }
            for (Model_Gestores gestoresCollectionNewModel_Gestores : gestoresCollectionNew) {
                if (!gestoresCollectionOld.contains(gestoresCollectionNewModel_Gestores)) {
                    Model_Usuario oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores = gestoresCollectionNewModel_Gestores.getUsuarioUsuarioid();
                    gestoresCollectionNewModel_Gestores.setUsuarioUsuarioid(model_Usuario);
                    gestoresCollectionNewModel_Gestores = em.merge(gestoresCollectionNewModel_Gestores);
                    if (oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores != null && !oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores.equals(model_Usuario)) {
                        oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores.getGestoresCollection().remove(gestoresCollectionNewModel_Gestores);
                        oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores = em.merge(oldUsuarioUsuarioidOfGestoresCollectionNewModel_Gestores);
                    }
                }
            }
            for (Model_Cliente clienteCollectionNewModel_Cliente : clienteCollectionNew) {
                if (!clienteCollectionOld.contains(clienteCollectionNewModel_Cliente)) {
                    Model_Usuario oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente = clienteCollectionNewModel_Cliente.getUsuarioUsuarioid();
                    clienteCollectionNewModel_Cliente.setUsuarioUsuarioid(model_Usuario);
                    clienteCollectionNewModel_Cliente = em.merge(clienteCollectionNewModel_Cliente);
                    if (oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente != null && !oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente.equals(model_Usuario)) {
                        oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente.getClienteCollection().remove(clienteCollectionNewModel_Cliente);
                        oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente = em.merge(oldUsuarioUsuarioidOfClienteCollectionNewModel_Cliente);
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
                Integer id = model_Usuario.getUsuarioid();
                if (findModel_Usuario(id) == null) {
                    throw new NonexistentEntityException("The model_Usuario with id " + id + " no longer exists.");
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
            Model_Usuario model_Usuario;
            try {
                model_Usuario = em.getReference(Model_Usuario.class, id);
                model_Usuario.getUsuarioid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Model_Despachador> despachadorCollectionOrphanCheck = model_Usuario.getDespachadorCollection();
            for (Model_Despachador despachadorCollectionOrphanCheckModel_Despachador : despachadorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Usuario (" + model_Usuario + ") cannot be destroyed since the Model_Despachador " + despachadorCollectionOrphanCheckModel_Despachador + " in its despachadorCollection field has a non-nullable usuarioUsuarioid field.");
            }
            Collection<Model_Gestores> gestoresCollectionOrphanCheck = model_Usuario.getGestoresCollection();
            for (Model_Gestores gestoresCollectionOrphanCheckModel_Gestores : gestoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Usuario (" + model_Usuario + ") cannot be destroyed since the Model_Gestores " + gestoresCollectionOrphanCheckModel_Gestores + " in its gestoresCollection field has a non-nullable usuarioUsuarioid field.");
            }
            Collection<Model_Cliente> clienteCollectionOrphanCheck = model_Usuario.getClienteCollection();
            for (Model_Cliente clienteCollectionOrphanCheckModel_Cliente : clienteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Model_Usuario (" + model_Usuario + ") cannot be destroyed since the Model_Cliente " + clienteCollectionOrphanCheckModel_Cliente + " in its clienteCollection field has a non-nullable usuarioUsuarioid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(model_Usuario);
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

    public List<Model_Usuario> findModel_UsuarioEntities() {
        return findModel_UsuarioEntities(true, -1, -1);
    }

    public List<Model_Usuario> findModel_UsuarioEntities(int maxResults, int firstResult) {
        return findModel_UsuarioEntities(false, maxResults, firstResult);
    }

    private List<Model_Usuario> findModel_UsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Model_Usuario.class));
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

    public Model_Usuario findModel_Usuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Model_Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_UsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Model_Usuario> rt = cq.from(Model_Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
