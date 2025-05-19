/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.DAOs;

import Uso_Comun.Modelos.Producto;
import Inventario.Modelos.Despachador;
import Ventas.Modelos.HistorialPedidos;
import Uso_Comun.Modelos.Pedidos;
import Ventas.Modelos.Mensajero;
import Ventas.Modelos.Cliente;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import Ventas.DAOS.DAO_Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
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
public class DAO_Pedidos implements Serializable {

    public DAO_Pedidos() {
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

    private static final DAO_Cliente DAOc = new DAO_Cliente();
    private static final DAO_Producto DAOp = new DAO_Producto();
    
    
    public List<Pedidos> findPedidos() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String query = "SELECT * FROM Pedidos;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery();

            List<Pedidos> pedidos = new ArrayList<>();
            while(rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setPedidoID(rs.getInt("PedidoID"));
                pedido.setEstado(rs.getString("Estado"));
                
                Cliente cliente = DAOc.findCliente(rs.getInt("ClienteID"));
                pedido.setClienteID(cliente);
                
                pedido.setHistorialPredidosID(new HistorialPedidos(rs.getInt("Historial_PredidosID")));
                pedido.setFechainicio(rs.getDate("Fecha_Inicio"));
                
                Collection<Producto> productos = DAOp.find_toPedidos(pedido);
                pedido.setProductoCollection(productos);
                
                pedidos.add(pedido);
            }

            return pedidos;
        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en findPedidos: "+ex);
            return null;
        }

    }

    public List<Pedidos> findPedidosByHistorialPedidos(HistorialPedidos Historial) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        
        String query = "SELECT * FROM Pedidos WHERE Historial_PredidosID = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, String.valueOf(Historial.getHistorialPredidosID()));
        
        ResultSet rs = stmt.executeQuery();
        
        List<Pedidos> Pedidos = new ArrayList<>();
        while(rs.next()){
            Pedidos pedido = new Pedidos();
            pedido.setPedidoID(rs.getInt("PedidoID"));
            pedido.setEstado(rs.getString("Estado"));
            pedido.setFechainicio(rs.getDate("Fecha_Inicio"));
            
            Cliente cliente = DAOc.findCliente(rs.getInt("ClienteID"));
            pedido.setClienteID(cliente);
            
            Pedidos.add(pedido);
        }
        
        return Pedidos;

    }

    public void create(Pedidos pedido) {
        System.out.println("Entra a metodo create de Productos");
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            if (pedido.getPedidoID()== null) {
                System.out.println("Obteniendo una id");
                //List<Producto> AllProducto = findModel_ProductoEntities();
                //int nuevoID = AllProducto.get(AllProducto.size() - 1).getProductoID() + 1;
                int tamañoLista = this.findTamañoDeTabla() + 1;
                pedido.setPedidoID(tamañoLista);
                System.out.println("Nuevo Id escogido:" + tamañoLista);
            }

            if (pedido.getFechainicio() == null) {
                System.out.println("Añadiendo fecha");
                pedido.setFechainicio(new Date());
            }

            String query = "INSERT Pedidos (PedidoID, Estado, ClienteID, Historial_PredidosID, Fecha_Inicio";
            int QSimbolQuestions = 5;

            boolean DespachadorExiste = pedido.getDespachadorID() != null;
            boolean MensajeroExiste = pedido.getMensajeroID() != null;

            if (DespachadorExiste) {
                query = query + ", DespachadorID";
                QSimbolQuestions++;
            }
            
            if (MensajeroExiste) {
                query = query + ", MensajeroID";
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

            Integer pedidoID = pedido.getPedidoID();
            String estado = pedido.getEstado();
            Integer clienteID = pedido.getClienteID().getClienteID();
            Integer historialPedidosID = pedido.getHistorialPredidosID().getHistorialPredidosID();
            Date fechaInicio = pedido.getFechainicio();
            
            Integer despachadorID = -1;
            Integer mensajeroID = -1;
            if (DespachadorExiste) {
                despachadorID = pedido.getDespachadorID().getDespachadorID();
            }
            if (MensajeroExiste) {
                mensajeroID = pedido.getMensajeroID().getMensajeroID();
            }

            CallableStatement cs = conectar.prepareCall(query);

            cs.setInt(1, pedidoID);
            cs.setString(2, estado);
            cs.setInt(3, clienteID);
            cs.setInt(4,historialPedidosID);
            cs.setTimestamp(5, new Timestamp(fechaInicio.getTime()));
            int counter = 6;
            if (DespachadorExiste) {
                cs.setInt(counter, despachadorID);
                counter++;
            }
            if (MensajeroExiste){
                cs.setInt(counter, mensajeroID);
            }

            System.out.println("Intenta hacer la insercion");
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en create: " + ex);
        }
    }
    
    public int findTamañoDeTabla() throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }

        String query
                = "SELECT "
                + "COUNT(*) AS cantidad "
                + "FROM Pedidos";

        PreparedStatement stmt = conectar.prepareStatement(query);

        ResultSet rs = stmt.executeQuery();

        int tamañoTabla = 0;
        if (rs.next()) {
            tamañoTabla = rs.getInt("cantidad");
        }

        return tamañoTabla;
    }

    public Pedidos findPedido(Integer pedidoID) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String query = "SELECT * FROM Pedidos WHERE PedidoID = ?";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(pedidoID));
            
            ResultSet rs = stmt.executeQuery();
            
            Pedidos pedido = null;
            if(rs.next()){
                pedido = new Pedidos();
                pedido.setPedidoID(rs.getInt("PedidoID"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setFechainicio(rs.getDate("Fecha_Inicio"));
                
                Cliente cliente = DAOc.findCliente(rs.getInt("ClienteID"));
                pedido.setClienteID(cliente);
            }
            
            return pedido;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Pedidos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public Pedidos find_toProducto(Producto producto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
