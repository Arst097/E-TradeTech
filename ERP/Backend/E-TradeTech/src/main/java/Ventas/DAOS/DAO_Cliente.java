/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas.DAOS;

import Ventas.Modelos.Pedidos;
import Uso_Comun.Modelos.Usuario;
import Ventas.Modelos.Cliente;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Cliente implements Serializable {

    public DAO_Cliente(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_Cliente() {
    }
    
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    private static Connection conectar = null;
    
    private static final String usuario = "Access";
    private static final String bd = "ETradeTechDB";
    private static final String contraseña = "123";
    private static final String ip = "localhost";
    private static final String puerto = "1433";
    
    public static void EstablecerConexion() {
        try {
            String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + bd + ";" + "encrypt=false";
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            System.out.println("Conexion Establecida");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public void create(Cliente model_Cliente) throws PreexistingEntityException, RollbackFailureException, Exception {
//        if (model_Cliente.getPedidosCollection() == null) {
//            model_Cliente.setPedidosCollection(new ArrayList<Pedidos>());
//        }
//
//        EntityManager em = null;
//        EntityTransaction tx = null;
//
//        try {
//            em = getEntityManager();
//            tx = em.getTransaction();
//            tx.begin();
//
//            // Asociación de Usuario
//            Usuario usuarioUsuarioid = model_Cliente.getUsuarioUsuarioid();
//            if (usuarioUsuarioid != null) {
//                usuarioUsuarioid = em.getReference(usuarioUsuarioid.getClass(), usuarioUsuarioid.getUsuarioid());
//                model_Cliente.setUsuarioUsuarioid(usuarioUsuarioid);
//            }
//
//            // Asociación de Pedidos
//            Collection<Pedidos> attachedPedidosCollection = new ArrayList<>();
//            for (Pedidos pedido : model_Cliente.getPedidosCollection()) {
//                pedido = em.getReference(pedido.getClass(), pedido.getPedidoID());
//                attachedPedidosCollection.add(pedido);
//            }
//            model_Cliente.setPedidosCollection(attachedPedidosCollection);
//
//            // Persistencia
//            em.persist(model_Cliente);
//
//            // Actualización relaciones bidireccionales
//            if (usuarioUsuarioid != null) {
//                usuarioUsuarioid.getClienteCollection().add(model_Cliente);
//                em.merge(usuarioUsuarioid);
//            }
//
//            for (Pedidos pedido : model_Cliente.getPedidosCollection()) {
//                Cliente oldCliente = pedido.getClienteID();
//                pedido.setClienteID(model_Cliente);
//                em.merge(pedido);
//
//                if (oldCliente != null) {
//                    oldCliente.getPedidosCollection().remove(pedido);
//                    em.merge(oldCliente);
//                }
//            }
//
//            tx.commit();
//        } catch (Exception ex) {
//            if (tx != null && tx.isActive()) {
//                try {
//                    tx.rollback();
//                } catch (Exception rbEx) {
//                    throw new RollbackFailureException("Error al hacer rollback de la transacción", rbEx);
//                }
//            }
//
//            // Verificar si es un error de entidad preexistente
//            if (findModel_Cliente(model_Cliente.getClienteID()) != null) {
//                throw new PreexistingEntityException("El cliente ya existe: " + model_Cliente.getClienteID(), ex);
//            }
//
//            throw ex;
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//    }

    public void edit(Cliente model_Cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentModel_Cliente = em.find(Cliente.class, model_Cliente.getClienteID());
            Usuario usuarioUsuarioidOld = persistentModel_Cliente.getUsuarioUsuarioid();
            Usuario usuarioUsuarioidNew = model_Cliente.getUsuarioUsuarioid();
            Collection<Pedidos> pedidosCollectionOld = persistentModel_Cliente.getPedidosCollection();
            Collection<Pedidos> pedidosCollectionNew = model_Cliente.getPedidosCollection();
            if (usuarioUsuarioidNew != null) {
                usuarioUsuarioidNew = em.getReference(usuarioUsuarioidNew.getClass(), usuarioUsuarioidNew.getUsuarioid());
                model_Cliente.setUsuarioUsuarioid(usuarioUsuarioidNew);
            }
            Collection<Pedidos> attachedPedidosCollectionNew = new ArrayList<Pedidos>();
            for (Pedidos pedidosCollectionNewModel_PedidosToAttach : pedidosCollectionNew) {
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
            for (Pedidos pedidosCollectionOldModel_Pedidos : pedidosCollectionOld) {
                if (!pedidosCollectionNew.contains(pedidosCollectionOldModel_Pedidos)) {
                    pedidosCollectionOldModel_Pedidos.setClienteID(null);
                    pedidosCollectionOldModel_Pedidos = em.merge(pedidosCollectionOldModel_Pedidos);
                }
            }
            for (Pedidos pedidosCollectionNewModel_Pedidos : pedidosCollectionNew) {
                if (!pedidosCollectionOld.contains(pedidosCollectionNewModel_Pedidos)) {
                    Cliente oldClienteIDOfPedidosCollectionNewModel_Pedidos = pedidosCollectionNewModel_Pedidos.getClienteID();
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
        EntityTransaction tx = null;

        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Cliente model_Cliente;
            try {
                model_Cliente = em.getReference(Cliente.class, id);
                model_Cliente.getClienteID(); // Para forzar la carga si es proxy
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El cliente con ID " + id + " no existe.", enfe);
            }

            // Eliminar relación con Usuario
            Usuario usuario = model_Cliente.getUsuarioUsuarioid();
            if (usuario != null) {
                usuario.getClienteCollection().remove(model_Cliente);
                em.merge(usuario);
            }

            // Eliminar relaciones con Pedidos
            Collection<Pedidos> pedidos = model_Cliente.getPedidosCollection();
            for (Pedidos pedido : pedidos) {
                pedido.setClienteID(null);
                em.merge(pedido);
            }

            // Eliminar entidad principal
            em.remove(model_Cliente);

            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rbEx) {
                    throw new RollbackFailureException("Error al hacer rollback", rbEx);
                }
            }
            throw ex;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Cliente> findModel_ClienteEntities() {
        return findModel_ClienteEntities(true, -1, -1);
    }

    public List<Cliente> findModel_ClienteEntities(int maxResults, int firstResult) {
        return findModel_ClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findModel_ClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findModel_Cliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getModel_ClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Cliente findNombreByClienteID(int ClienteID) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String query = "SELECT * FROM Cliente WHERE ClienteID = ?";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(ClienteID));
            
            ResultSet rs = stmt.executeQuery();
            
            Cliente cliente = null;
            if(rs.next()){
                cliente = new Cliente();
                cliente.setClienteID(rs.getInt("ClienteID"));
                cliente.setCorreo(rs.getNString("Correo"));
                cliente.setTelefono(rs.getNString("Telefono"));
                cliente.setNombre(rs.getNString("Nombre"));
            }
            
            return cliente;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Pedidos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }

}
