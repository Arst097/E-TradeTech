/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.ListaContactos;
import Proveedores.Modelos.UsuarioCompras;
import Uso_Comun.Modelos.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_UsuarioCompras implements Serializable {

    public DAO_UsuarioCompras(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioCompras usuarioCompras) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            ListaContactos listaContactosID = usuarioCompras.getListaContactosID();
            if (listaContactosID != null) {
                listaContactosID = em.getReference(listaContactosID.getClass(), listaContactosID.getListaContactosID());
                usuarioCompras.setListaContactosID(listaContactosID);
            }
            Usuario usuarioUsuarioid = usuarioCompras.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid = em.getReference(usuarioUsuarioid.getClass(), usuarioUsuarioid.getUsuarioid());
                usuarioCompras.setUsuarioUsuarioid(usuarioUsuarioid);
            }
            em.persist(usuarioCompras);
            if (listaContactosID != null) {
                listaContactosID.getUsuarioComprasCollection().add(usuarioCompras);
                listaContactosID = em.merge(listaContactosID);
            }
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getUsuarioComprasCollection().add(usuarioCompras);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioCompras(usuarioCompras.getUsuarioComprasID()) != null) {
                throw new PreexistingEntityException("UsuarioCompras " + usuarioCompras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioCompras usuarioCompras) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioCompras persistentUsuarioCompras = em.find(UsuarioCompras.class, usuarioCompras.getUsuarioComprasID());
            ListaContactos listaContactosIDOld = persistentUsuarioCompras.getListaContactosID();
            ListaContactos listaContactosIDNew = usuarioCompras.getListaContactosID();
            Usuario usuarioUsuarioidOld = persistentUsuarioCompras.getUsuarioUsuarioid();
            Usuario usuarioUsuarioidNew = usuarioCompras.getUsuarioUsuarioid();
            if (listaContactosIDNew != null) {
                listaContactosIDNew = em.getReference(listaContactosIDNew.getClass(), listaContactosIDNew.getListaContactosID());
                usuarioCompras.setListaContactosID(listaContactosIDNew);
            }
            if (usuarioUsuarioidNew != null) {
                usuarioUsuarioidNew = em.getReference(usuarioUsuarioidNew.getClass(), usuarioUsuarioidNew.getUsuarioid());
                usuarioCompras.setUsuarioUsuarioid(usuarioUsuarioidNew);
            }
            usuarioCompras = em.merge(usuarioCompras);
            if (listaContactosIDOld != null && !listaContactosIDOld.equals(listaContactosIDNew)) {
                listaContactosIDOld.getUsuarioComprasCollection().remove(usuarioCompras);
                listaContactosIDOld = em.merge(listaContactosIDOld);
            }
            if (listaContactosIDNew != null && !listaContactosIDNew.equals(listaContactosIDOld)) {
                listaContactosIDNew.getUsuarioComprasCollection().add(usuarioCompras);
                listaContactosIDNew = em.merge(listaContactosIDNew);
            }
            if (usuarioUsuarioidOld != null && !usuarioUsuarioidOld.equals(usuarioUsuarioidNew)) {
                usuarioUsuarioidOld.getUsuarioComprasCollection().remove(usuarioCompras);
                usuarioUsuarioidOld = em.merge(usuarioUsuarioidOld);
            }
            if (usuarioUsuarioidNew != null && !usuarioUsuarioidNew.equals(usuarioUsuarioidOld)) {
                usuarioUsuarioidNew.getUsuarioComprasCollection().add(usuarioCompras);
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
                Integer id = usuarioCompras.getUsuarioComprasID();
                if (findUsuarioCompras(id) == null) {
                    throw new NonexistentEntityException("The usuarioCompras with id " + id + " no longer exists.");
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
        EntityTransaction tx = null;
        
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            
            UsuarioCompras usuarioCompras;
            try {
                usuarioCompras = em.getReference(UsuarioCompras.class, id);
                usuarioCompras.getUsuarioComprasID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioCompras with id " + id + " no longer exists.", enfe);
            }
            ListaContactos listaContactosID = usuarioCompras.getListaContactosID();
            if (listaContactosID != null) {
                listaContactosID.getUsuarioComprasCollection().remove(usuarioCompras);
                listaContactosID = em.merge(listaContactosID);
            }
            Usuario usuarioUsuarioid = usuarioCompras.getUsuarioUsuarioid();
            if (usuarioUsuarioid != null) {
                usuarioUsuarioid.getUsuarioComprasCollection().remove(usuarioCompras);
                usuarioUsuarioid = em.merge(usuarioUsuarioid);
            }
            em.remove(usuarioCompras);
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
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

    public List<UsuarioCompras> findUsuarioComprasEntities() {
        return findUsuarioComprasEntities(true, -1, -1);
    }

    public List<UsuarioCompras> findUsuarioComprasEntities(int maxResults, int firstResult) {
        return findUsuarioComprasEntities(false, maxResults, firstResult);
    }

    private List<UsuarioCompras> findUsuarioComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioCompras.class));
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

    public UsuarioCompras findUsuarioCompras(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioCompras> rt = cq.from(UsuarioCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
