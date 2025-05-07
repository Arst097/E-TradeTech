/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.DAOs;

import Inventario.Modelos.Despachador;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Gestores;
import Inventario.Modelos.Gestores;
import Uso_Comun.Modelos.Usuario;
import Uso_Comun.Modelos.Cliente;
import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public DAO_Usuario() {
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario model_Usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        if (model_Usuario.getDespachadorCollection() == null) {
            model_Usuario.setDespachadorCollection(new ArrayList<Despachador>());
        }
        if (model_Usuario.getGestoresCollection() == null) {
            model_Usuario.setGestoresCollection(new ArrayList<Gestores>());
        }
        if (model_Usuario.getClienteCollection() == null) {
            model_Usuario.setClienteCollection(new ArrayList<Cliente>());
        }

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Código original de asociación de colecciones
            Collection<Despachador> attachedDespachadorCollection = new ArrayList<>();
            for (Despachador despachador : model_Usuario.getDespachadorCollection()) {
                despachador = em.getReference(despachador.getClass(), despachador.getDespachadorID());
                attachedDespachadorCollection.add(despachador);
            }
            model_Usuario.setDespachadorCollection(attachedDespachadorCollection);

            Collection<Gestores> attachedGestoresCollection = new ArrayList<>();
            for (Gestores gestor : model_Usuario.getGestoresCollection()) {
                gestor = em.getReference(gestor.getClass(), gestor.getGestorID());
                attachedGestoresCollection.add(gestor);
            }
            model_Usuario.setGestoresCollection(attachedGestoresCollection);

            Collection<Cliente> attachedClienteCollection = new ArrayList<>();
            for (Cliente cliente : model_Usuario.getClienteCollection()) {
                cliente = em.getReference(cliente.getClass(), cliente.getClienteID());
                attachedClienteCollection.add(cliente);
            }
            model_Usuario.setClienteCollection(attachedClienteCollection);

            em.persist(model_Usuario);

            // Actualización de relaciones bidireccionales
            for (Despachador despachador : model_Usuario.getDespachadorCollection()) {
                Usuario oldUsuario = despachador.getUsuarioUsuarioid();
                despachador.setUsuarioUsuarioid(model_Usuario);
                em.merge(despachador);

                if (oldUsuario != null) {
                    oldUsuario.getDespachadorCollection().remove(despachador);
                    em.merge(oldUsuario);
                }
            }

            for (Gestores gestor : model_Usuario.getGestoresCollection()) {
                Usuario oldUsuario = gestor.getUsuarioUsuarioid();
                gestor.setUsuarioUsuarioid(model_Usuario);
                em.merge(gestor);

                if (oldUsuario != null) {
                    oldUsuario.getGestoresCollection().remove(gestor);
                    em.merge(oldUsuario);
                }
            }

            for (Cliente cliente : model_Usuario.getClienteCollection()) {
                Usuario oldUsuario = cliente.getUsuarioUsuarioid();
                cliente.setUsuarioUsuarioid(model_Usuario);
                em.merge(cliente);

                if (oldUsuario != null) {
                    oldUsuario.getClienteCollection().remove(cliente);
                    em.merge(oldUsuario);
                }
            }

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rbEx) {
                    throw new RollbackFailureException("Error al hacer rollback", rbEx);
                }
            }

            // Verificar si es un error de entidad preexistente
            if (findModel_Usuario(model_Usuario.getUsuarioid()) != null) {
                throw new PreexistingEntityException("El usuario ya existe: " + model_Usuario.getUsuarioid(), ex);
            }

            throw ex;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void edit(Usuario model_Usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Usuario persistentModel_Usuario = em.find(Usuario.class, model_Usuario.getUsuarioid());
            if (persistentModel_Usuario == null) {
                throw new NonexistentEntityException("El usuario con id " + model_Usuario.getUsuarioid() + " no existe.");
            }

            // Verificación de orfandad ilegal
            Collection<Despachador> despachadorCollectionOld = persistentModel_Usuario.getDespachadorCollection();
            Collection<Despachador> despachadorCollectionNew = model_Usuario.getDespachadorCollection();
            Collection<Gestores> gestoresCollectionOld = persistentModel_Usuario.getGestoresCollection();
            Collection<Gestores> gestoresCollectionNew = model_Usuario.getGestoresCollection();
            Collection<Cliente> clienteCollectionOld = persistentModel_Usuario.getClienteCollection();
            Collection<Cliente> clienteCollectionNew = model_Usuario.getClienteCollection();

            List<String> illegalOrphanMessages = null;

            // Verificar despachadores
            for (Despachador oldDespachador : despachadorCollectionOld) {
                if (!despachadorCollectionNew.contains(oldDespachador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("Debe mantener el Despachador " + oldDespachador + " porque su campo usuarioUsuarioid no es nulo.");
                }
            }

            // Verificar gestores
            for (Gestores oldGestor : gestoresCollectionOld) {
                if (!gestoresCollectionNew.contains(oldGestor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("Debe mantener el Gestor " + oldGestor + " porque su campo usuarioUsuarioid no es nulo.");
                }
            }

            // Verificar clientes
            for (Cliente oldCliente : clienteCollectionOld) {
                if (!clienteCollectionNew.contains(oldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("Debe mantener el Cliente " + oldCliente + " porque su campo usuarioUsuarioid no es nulo.");
                }
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            // Adjuntar las nuevas colecciones
            model_Usuario.setDespachadorCollection(attachCollection(em, despachadorCollectionNew));
            model_Usuario.setGestoresCollection(attachCollection(em, gestoresCollectionNew));
            model_Usuario.setClienteCollection(attachCollection(em, clienteCollectionNew));

            // Realizar el merge
            model_Usuario = em.merge(model_Usuario);

            // Actualizar relaciones bidireccionales
            updateRelationships(em, model_Usuario, despachadorCollectionOld, despachadorCollectionNew);
            updateRelationships(em, model_Usuario, gestoresCollectionOld, gestoresCollectionNew);
            updateRelationships(em, model_Usuario, clienteCollectionOld, clienteCollectionNew);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rbEx) {
                    throw new RollbackFailureException("Error al hacer rollback de la transacción", rbEx);
                }
            }

            // Verificar si el usuario ya no existe
            if (ex instanceof NonexistentEntityException) {
                throw ex;
            }

            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.isEmpty()) {
                Integer id = model_Usuario.getUsuarioid();
                if (findModel_Usuario(id) == null) {
                    throw new NonexistentEntityException("El usuario con id " + id + " ya no existe.");
                }
            }
            throw ex;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

// Métodos auxiliares para mejorar la legibilidad
    private <T> Collection<T> attachCollection(EntityManager em, Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }

        Collection<T> attachedCollection = new ArrayList<>();
        for (T item : collection) {
            item = (T) em.getReference(item.getClass(), getId(item));
            attachedCollection.add(item);
        }
        return attachedCollection;
    }

    private <T> Object getId(T entity) {
        if (entity instanceof Despachador) {
            return ((Despachador) entity).getDespachadorID();
        } else if (entity instanceof Gestores) {
            return ((Gestores) entity).getGestorID();
        } else if (entity instanceof Cliente) {
            return ((Cliente) entity).getClienteID();
        }
        throw new IllegalArgumentException("Tipo de entidad no soportado");
    }

    private <T> void updateRelationships(EntityManager em, Usuario usuario,
            Collection<T> oldCollection, Collection<T> newCollection) {
        for (T item : newCollection) {
            if (!oldCollection.contains(item)) {
                Usuario oldUsuario = getUsuarioFromEntity(item);
                setUsuarioForEntity(item, usuario);
                em.merge(item);

                if (oldUsuario != null && !oldUsuario.equals(usuario)) {
                    removeEntityFromCollection(oldUsuario, item);
                    em.merge(oldUsuario);
                }
            }
        }
    }

// Métodos auxiliares para manejar los diferentes tipos de entidades
    private Usuario getUsuarioFromEntity(Object entity) {
        if (entity instanceof Despachador) {
            return ((Despachador) entity).getUsuarioUsuarioid();
        } else if (entity instanceof Gestores) {
            return ((Gestores) entity).getUsuarioUsuarioid();
        } else if (entity instanceof Cliente) {
            return ((Cliente) entity).getUsuarioUsuarioid();
        }
        return null;
    }

    private void setUsuarioForEntity(Object entity, Usuario usuario) {
        if (entity instanceof Despachador) {
            ((Despachador) entity).setUsuarioUsuarioid(usuario);
        } else if (entity instanceof Gestores) {
            ((Gestores) entity).setUsuarioUsuarioid(usuario);
        } else if (entity instanceof Cliente) {
            ((Cliente) entity).setUsuarioUsuarioid(usuario);
        }
    }

    private void removeEntityFromCollection(Usuario usuario, Object entity) {
        if (entity instanceof Despachador) {
            usuario.getDespachadorCollection().remove(entity);
        } else if (entity instanceof Gestores) {
            usuario.getGestoresCollection().remove(entity);
        } else if (entity instanceof Cliente) {
            usuario.getClienteCollection().remove(entity);
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Usuario model_Usuario;
            try {
                model_Usuario = em.getReference(Usuario.class, id);
                model_Usuario.getUsuarioid(); // Esto fuerza la carga para verificar existencia
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.", enfe);
            }

            // Verificación de orfandad
            List<String> illegalOrphanMessages = null;

            // Verificar despachadores asociados
            for (Despachador despachador : model_Usuario.getDespachadorCollection()) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("No se puede eliminar el usuario (" + model_Usuario
                        + ") porque tiene asociado el despachador " + despachador
                        + " con referencia no nula.");
            }

            // Verificar gestores asociados
            for (Gestores gestor : model_Usuario.getGestoresCollection()) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("No se puede eliminar el usuario (" + model_Usuario
                        + ") porque tiene asociado el gestor " + gestor
                        + " con referencia no nula.");
            }

            // Verificar clientes asociados
            for (Cliente cliente : model_Usuario.getClienteCollection()) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("No se puede eliminar el usuario (" + model_Usuario
                        + ") porque tiene asociado el cliente " + cliente
                        + " con referencia no nula.");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(model_Usuario);
            tx.commit();

        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rbEx) {
                    throw new RollbackFailureException("Error al revertir la transacción", rbEx);
                }
            }

            // Relanza la excepción original
            throw ex;

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Usuario> findModel_UsuarioEntities() {
        return findModel_UsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findModel_UsuarioEntities(int maxResults, int firstResult) {
        return findModel_UsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findModel_UsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findModel_Usuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    

    private static final String usuario = "Access";
    private static final String bd = "ETradeTechDB";
    private static final String contraseña = "123";
    private static final String ip = "localhost";
    private static final String puerto = "1433";
    
    private static String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + bd + ";" + "encrypt=false";
    
    private static Connection conectar;
    
    public static void EstablecerConexion() {
        try {
            //String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + bd + ";" + "encrypt=false";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public Usuario findUsuarioByCorreoAndSHA256(boolean Ch, String Correo, String SHA256) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        String query = "SELECT Usuario_id, Nombre, Correo, Contraseña_SHA256 FROM Usuario WHERE Correo = ? AND Contraseña_SHA256 = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, Correo);
        stmt.setString(2, SHA256);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setUsuarioid(rs.getInt("Usuario_id"));
            usuario.setNombre(rs.getString("Nombre"));
            usuario.setCorreo(rs.getString("Correo"));
            usuario.setContraseñaSHA256(rs.getString("Contraseña_SHA256"));

            return usuario;
        }
        
        return null;
    }
    

    private String obtenerDatosUsuarios(List<Usuario> usuarios) {
        StringBuilder datos = new StringBuilder();

        for (Usuario usuario : usuarios) {
            // Asumiendo que Usuario tiene métodos getNombre() y getEmail()
            datos.append("Correo: ").append(usuario.getCorreo()).append(", ");
            datos.append("ContraseñaSHA256: ").append(usuario.getContraseñaSHA256()).append("\n");
        }

        return datos.toString();
    }

    public int getModel_UsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
