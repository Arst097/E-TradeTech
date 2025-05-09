/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.DAOs;

import static Inventario.DAOs.DAO_Inventario.EstablecerConexion;
import Inventario.Modelos.Inventario;
import Uso_Comun.Modelos.Producto;
import Uso_Comun.Modelos.Pedidos;
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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Producto implements Serializable {

    public DAO_Producto(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public DAO_Producto() {
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

    //no funciona, es el que utiliza JSA y desactive eso
    public void fcreate(Producto model_Producto) throws PreexistingEntityException, RollbackFailureException, Exception {
        System.out.println("Entrando a funcion Create");

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Inventario inventarioID = model_Producto.getInventarioID();
            System.out.println("Desde Create: " + inventarioID);
            if (inventarioID != null) {
                inventarioID = em.getReference(inventarioID.getClass(), inventarioID.getInventarioID());
                model_Producto.setInventarioID(inventarioID);
                System.out.println("Desde Create: " + inventarioID);
            }

            Pedidos pedidoID = model_Producto.getPedidoID();
            System.out.println("Entrando a if pedidoID");
            if (pedidoID != null) {
                pedidoID = em.getReference(pedidoID.getClass(), pedidoID.getPedidoID());
                model_Producto.setPedidoID(pedidoID);
            }

            em.persist(model_Producto);

            if (inventarioID != null) {
                inventarioID.getProductoCollection().add(model_Producto);
                em.merge(inventarioID);
            }
            if (pedidoID != null) {
                pedidoID.getProductoCollection().add(model_Producto);
                em.merge(pedidoID);
            }

            tx.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception re) {
                    throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
                }
            }
            if (findModel_Producto(model_Producto.getProductoID()) != null) {
                throw new PreexistingEntityException("Producto " + model_Producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Producto producto) {
        System.out.println("Entra a metodo create de Productos");
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            if (producto.getProductoID() == null) {
                System.out.println("Obteniendo una id");
                //List<Producto> AllProducto = findModel_ProductoEntities();
                //int nuevoID = AllProducto.get(AllProducto.size() - 1).getProductoID() + 1;
                int tamañoLista = this.findTamañoDeTabla()+1;
                producto.setProductoID(tamañoLista);
                System.out.println("Nuevo Id escogido:" + tamañoLista);
            }

            if (producto.getFechaEntrada() == null) {
                System.out.println("Añadiendo fecha");
                producto.setFechaEntrada(new Date());
            }

            String query = "INSERT Producto (ProductoID, InventarioID, Modelo, Fecha_Entrada, Precio, Categoria";
            int QSimbolQuestions = 6;

            boolean pedidoExiste = producto.getPedidoID() != null;

            if (pedidoExiste) {
                query = query + ", PedidoID";
                QSimbolQuestions++;
            }

            query = query + ") VALUES (";

            for (int i = 1; i <= QSimbolQuestions; i++) {
                query = query + "?";
                if (i != QSimbolQuestions) {
                    query = query + ",";
                }
            }
            query = query + ");";
            
            System.out.println(query);

            Integer productoID = producto.getProductoID();
            Integer inventarioID = producto.getInventarioID().getInventarioID();
            Integer pedidoID = -1;
            String modelo = producto.getModelo();
            Date fecha = producto.getFechaEntrada();
            float precio = producto.getPrecio();
            String categoria = producto.getCategoria();
            if (pedidoExiste) {
                pedidoID = producto.getPedidoID().getPedidoID();
            }

            CallableStatement cs = conectar.prepareCall(query);

            cs.setInt(1, productoID);
            cs.setInt(2, inventarioID);
            cs.setString(3, modelo);
            cs.setTimestamp(4, new Timestamp(fecha.getTime()));
            cs.setDouble(5, precio);
            cs.setString(6, categoria);
            if (pedidoExiste) {
                cs.setInt(7, pedidoID);
            }

            System.out.println("Intenta hacer la insercion");
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en create: " + ex);
        }
    }

    public void fedit(Producto model_Producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto persistentModel_Producto = em.find(Producto.class, model_Producto.getProductoID());
            Inventario inventarioIDOld = persistentModel_Producto.getInventarioID();
            Inventario inventarioIDNew = model_Producto.getInventarioID();
            Pedidos pedidoIDOld = persistentModel_Producto.getPedidoID();
            Pedidos pedidoIDNew = model_Producto.getPedidoID();
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
    
    private Boolean canEdit(Producto producto){
        boolean check_productoID = producto.getProductoID().equals(null) || (producto.getProductoID() <= 0);
        boolean check_categoria = producto.getCategoria().isBlank();
        boolean check_fechaEntrada = producto.getFechaEntrada().equals(null);
        boolean check_inventarioID = producto.getInventarioID().equals(null) || (producto.getInventarioID().getInventarioID() <= 0);
        boolean check_modelo = producto.getModelo().isBlank();
        boolean check_pedidoID = producto.getPedidoID().equals(null) || (producto.getPedidoID().getPedidoID() <=0);
        boolean check_precio = producto.getPrecio().equals(null) || (producto.getPrecio() <= 0);
        
        boolean canEdit = check_productoID && check_categoria && check_fechaEntrada && check_inventarioID && check_modelo && check_pedidoID && check_precio;
        
        System.out.println("El objeto puede editar: " + canEdit
                         + "check_productoID = " + check_productoID
                         + "check_categoria" + check_categoria
                         + "check_fechaEntrada" + check_fechaEntrada
                         + "check_inventarioID" + check_inventarioID
                         + "check_modelo" + check_modelo
                         + "check_pedidoID" + check_pedidoID
                         + "check_precio" + check_precio);
        
        return canEdit; 
    }
    
    public void edit(Producto producto){
        System.out.println("Entra a metodo edit de Productos");
        if(canEdit(producto)){
            
        }
        
    }

    public void fdestroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            Producto model_Producto;
            try {
                model_Producto = em.getReference(Producto.class, id);
                model_Producto.getProductoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The Producto with id " + id + " no longer exists.", enfe);
            }

            Inventario inventarioID = model_Producto.getInventarioID();
            if (inventarioID != null) {
                inventarioID.getProductoCollection().remove(model_Producto);
                em.merge(inventarioID);
            }

            Pedidos pedidoID = model_Producto.getPedidoID();
            if (pedidoID != null) {
                pedidoID.getProductoCollection().remove(model_Producto);
                em.merge(pedidoID);
            }

            em.remove(model_Producto);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception re) {
                    throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findModel_ProductoEntities() {
        return findModel_ProductoEntities(true, -1, -1);
    }

    public List<Producto> findModel_ProductoEntities(int maxResults, int firstResult) {
        return findModel_ProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findModel_ProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findModel_Producto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public List<Producto> findProductoByInventario(Integer InventarioID) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT p FROM Producto p WHERE p.inventarioID.inventarioID = :inventarioId"
            );
            query.setParameter("inventarioId", InventarioID);

            List<Producto> resultados = query.getResultList();

            return resultados.isEmpty() ? null : resultados;
        } finally {
            em.close();
        }
    }

    public List<Object[]> findGrupoProductosByInventario(int InventarioID) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }

        String query
                = "SELECT "
                + "p.modelo AS modelo, "
                + "MAX(p.categoria) AS categoria, "
                + "COUNT(*) AS cantidad, "
                + "MAX(p.precio) AS precioMaximo "
                + "FROM Producto p "
                + "WHERE p.InventarioID = ? "
                + "GROUP BY p.modelo;";

        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, String.valueOf(InventarioID));

        ResultSet rs = stmt.executeQuery();

        List<Object[]> resultados = new ArrayList<>();

        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getString("modelo");        // String
            fila[1] = rs.getString("categoria");     // String (puede variar según el tipo real)
            fila[2] = rs.getLong("cantidad");        // COUNT(*) siempre devuelve Long
            fila[3] = rs.getFloat("precioMaximo");  // Float o tipo de 'precio'

            resultados.add(fila);
        }
        return resultados;
    }
    
    public int findTamañoDeTabla() throws SQLException{
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        
        String query
                = "SELECT "
                + "COUNT(*) AS cantidad "
                + "FROM Producto";
        
        PreparedStatement stmt = conectar.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();
        
        int tamañoTabla = 0;
        if(rs.next()){
            tamañoTabla = rs.getInt("cantidad");
        }
        
        return tamañoTabla;
    }

    public int getModel_ProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
