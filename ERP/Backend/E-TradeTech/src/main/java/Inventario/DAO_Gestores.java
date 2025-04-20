/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Usuario;
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
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Gestores implements Serializable {

    public DAO_Gestores(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public DAO_Gestores() {
        this.emf = Persistence.createEntityManagerFactory("ETradeTech_PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gestores model_Gestores) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Almacen almacenID = model_Gestores.getAlmacenID();
            if (almacenID != null) {
                almacenID = em.getReference(almacenID.getClass(), almacenID.getAlmacenID());
                model_Gestores.setAlmacenID(almacenID);
            }
            Model_Usuario usuarioUsuarioid = model_Gestores.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid = em.getReference(usuarioUsuarioid.getClass(), usuarioUsuarioid.getUsuarioid());
                model_Gestores.setUsuarioUsuarioid(usuarioUsuarioid);
            }
            em.persist(model_Gestores);
            if (almacenID != null) {
                almacenID.getGestoresCollection().add(model_Gestores);
                almacenID = em.merge(almacenID);
            }
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getGestoresCollection().add(model_Gestores);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findModel_Gestores(model_Gestores.getGestorID()) != null) {
                throw new PreexistingEntityException("Model_Gestores " + model_Gestores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gestores model_Gestores) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gestores persistentModel_Gestores = em.find(Gestores.class, model_Gestores.getGestorID());
            Almacen almacenIDOld = persistentModel_Gestores.getAlmacenID();
            Almacen almacenIDNew = model_Gestores.getAlmacenID();
            Model_Usuario usuarioUsuarioidOld = persistentModel_Gestores.getUsuarioUsuarioid();
            Model_Usuario usuarioUsuarioidNew = model_Gestores.getUsuarioUsuarioid();
            if (almacenIDNew != null) {
                almacenIDNew = em.getReference(almacenIDNew.getClass(), almacenIDNew.getAlmacenID());
                model_Gestores.setAlmacenID(almacenIDNew);
            }
            if (usuarioUsuarioidNew != null) {
                usuarioUsuarioidNew = em.getReference(usuarioUsuarioidNew.getClass(), usuarioUsuarioidNew.getUsuarioid());
                model_Gestores.setUsuarioUsuarioid(usuarioUsuarioidNew);
            }
            model_Gestores = em.merge(model_Gestores);
            if (almacenIDOld != null && !almacenIDOld.equals(almacenIDNew)) {
                almacenIDOld.getGestoresCollection().remove(model_Gestores);
                almacenIDOld = em.merge(almacenIDOld);
            }
            if (almacenIDNew != null && !almacenIDNew.equals(almacenIDOld)) {
                almacenIDNew.getGestoresCollection().add(model_Gestores);
                almacenIDNew = em.merge(almacenIDNew);
            }
            if (usuarioUsuarioidOld != null && !usuarioUsuarioidOld.equals(usuarioUsuarioidNew)) {
                usuarioUsuarioidOld.getGestoresCollection().remove(model_Gestores);
                usuarioUsuarioidOld = em.merge(usuarioUsuarioidOld);
            }
            if (usuarioUsuarioidNew != null && !usuarioUsuarioidNew.equals(usuarioUsuarioidOld)) {
                usuarioUsuarioidNew.getGestoresCollection().add(model_Gestores);
                usuarioUsuarioidNew = em.merge(usuarioUsuarioidNew);
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
                Integer id = model_Gestores.getGestorID();
                if (findModel_Gestores(id) == null) {
                    throw new NonexistentEntityException("The model_Gestores with id " + id + " no longer exists.");
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
            Gestores model_Gestores;
            try {
                model_Gestores = em.getReference(Gestores.class, id);
                model_Gestores.getGestorID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The model_Gestores with id " + id + " no longer exists.", enfe);
            }
            Almacen almacenID = model_Gestores.getAlmacenID();
            if (almacenID != null) {
                almacenID.getGestoresCollection().remove(model_Gestores);
                almacenID = em.merge(almacenID);
            }
            Model_Usuario usuarioUsuarioid = model_Gestores.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getGestoresCollection().remove(model_Gestores);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            em.remove(model_Gestores);
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

    public List<Gestores> findModel_GestoresEntities() {
        return findModel_GestoresEntities(true, -1, -1);
    }

    public List<Gestores> findModel_GestoresEntities(int maxResults, int firstResult) {
        return findModel_GestoresEntities(false, maxResults, firstResult);
    }

    private List<Gestores> findModel_GestoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gestores.class));
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

    public Gestores findModel_Gestores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gestores.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_GestoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gestores> rt = cq.from(Gestores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
