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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Producto implements Serializable {

    public DAO_Producto() {
    }

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

    private static final DAO_Pedidos DAOp = new DAO_Pedidos();
    
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
                int tamañoLista = this.findTamañoDeTabla() + 1;
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

    private Boolean canEdit(Producto producto) {
        boolean check_productoID = !( producto.getProductoID() == null || (producto.getProductoID() <= 0) );
        boolean check_categoria = !producto.getCategoria().isBlank();
        boolean check_fechaEntrada = !( producto.getFechaEntrada() == null );
        boolean check_inventarioID = !( producto.getInventarioID() == null || (producto.getInventarioID().getInventarioID() <= 0) );
        boolean check_modelo = !producto.getModelo().isBlank();
        boolean check_pedidoID = producto.getPrecio() >= 0 ;
        boolean check_precio = !( producto.getPrecio() == null || (producto.getPrecio() <= 0) );

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

    public int findTamañoDeTabla() throws SQLException {
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
        if (rs.next()) {
            tamañoTabla = rs.getInt("cantidad");
        }

        return tamañoTabla;
    }

    public Producto findProducto(int productoID) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Producto WHERE ProductoID = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(productoID));

            ResultSet rs = stmt.executeQuery();

            Producto producto = null;
            if (rs.next()) {
                producto = new Producto();
                producto.setProductoID(rs.getInt("ProductoID"));

                Inventario inventario = new Inventario(rs.getInt("InventarioID"));
                producto.setInventarioID(inventario);

                Integer pedidoID = rs.getInt("PedidoID");
                if (!pedidoID.equals(null)) {
                    DAO_Pedidos DAOp = new DAO_Pedidos();
                    producto.setPedidoID(DAOp.findPedido(pedidoID));
                }

                producto.setModelo(rs.getString("Modelo"));
                producto.setFechaEntrada(rs.getDate("Fecha_Entrada"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setCategoria(rs.getString("Categoria"));

            }

            return producto;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Producto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Producto> findProductoByModelo(String Modelo) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Producto WHERE Modelo = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(Modelo));

            ResultSet rs = stmt.executeQuery();

            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setProductoID(rs.getInt("ProductoID"));

                Inventario inventario = new Inventario(rs.getInt("InventarioID"));
                producto.setInventarioID(inventario);

                Integer pedidoID = rs.getInt("PedidoID");
                if (!pedidoID.equals(null)) {
                    DAO_Pedidos DAOp = new DAO_Pedidos();
                    producto.setPedidoID(DAOp.findPedido(pedidoID));
                }

                producto.setModelo(rs.getString("Modelo"));
                producto.setFechaEntrada(rs.getDate("Fecha_Entrada"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setCategoria(rs.getString("Categoria"));

                productos.add(producto);
            }

            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Producto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void edit(Producto producto) {
        if(!canEdit(producto)){
            System.out.println("No se pudo hacer la edicion");
            return;
        }

        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String query = "UPDATE Producto "
                    + "SET "
                        + "InventarioID = ?"
                        + ", Modelo = ? "
                        + ", Fecha_Entrada = ? "
                        + ", Precio = ? "
                        + ", Categoria = ? ";
            
            boolean pedidoExiste = producto.getPedidoID() != null ;

            if (pedidoExiste) {
                query = query + ", PedidoID = ? ";
            }
            
            query = query + "WHERE ProductoID = ?;";
            
            Integer inventarioID = producto.getInventarioID().getInventarioID();
            String modelo = producto.getModelo();
            Date fechaEntrada = producto.getFechaEntrada();
            Float precio = producto.getPrecio();
            String categoria = producto.getCategoria();
            
            Integer pedidoID = -1;
            if(pedidoExiste){
                pedidoID = producto.getPedidoID().getPedidoID();
            }
            
            Integer productoID = producto.getProductoID();
            
            CallableStatement cs = conectar.prepareCall(query);
            
            cs.setInt(1, inventarioID);
            cs.setString(2, modelo);
            cs.setTimestamp(3, new Timestamp(fechaEntrada.getTime()));
            cs.setFloat(4, precio);
            cs.setString(5, categoria);
            
            int count = 6;
            if(pedidoExiste){
                cs.setInt(count, pedidoID);
                count++;
            }
            
            cs.setInt(count, productoID);
            
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en Edit de Producto: "+ex);
        }
        
    }

    public Collection<Producto> find_toPedidos(Pedidos pedido) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Producto WHERE PedidoID = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(pedido.getPedidoID()));

            ResultSet rs = stmt.executeQuery();

            ArrayList<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setProductoID(rs.getInt("ProductoID"));

                Inventario inventario = new Inventario(rs.getInt("InventarioID"));
                producto.setInventarioID(inventario);

                producto.setPedidoID(pedido);

                producto.setModelo(rs.getString("Modelo"));
                producto.setFechaEntrada(rs.getDate("Fecha_Entrada"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setCategoria(rs.getString("Categoria"));

                productos.add(producto);
            }
            
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Producto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public Collection<Producto> find_toInventario(Inventario inventario) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Producto WHERE InventarioID = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(inventario.getInventarioID()));

            ResultSet rs = stmt.executeQuery();

            ArrayList<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setProductoID(rs.getInt("ProductoID"));

                producto.setInventarioID(inventario);

                producto.setModelo(rs.getString("Modelo"));
                producto.setFechaEntrada(rs.getDate("Fecha_Entrada"));
                producto.setPrecio(rs.getFloat("Precio"));
                producto.setCategoria(rs.getString("Categoria"));

                productos.add(producto);
            }
            
            return productos;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Producto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }    }

}
