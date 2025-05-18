/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas.DAOS;

import Uso_Comun.DAOs.DAO_Pedidos;
import Ventas.Modelos.HistorialPedidos;
import Uso_Comun.Modelos.Pedidos;
import Inventario.exceptions.IllegalOrphanException;
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
public class DAO_HistorialPedido implements Serializable {

    public DAO_HistorialPedido() {
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
    
    public HistorialPedidos findModel_HistorialPedidos(Integer historialPedidosID) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        
        String query = "SELECT * FROM Historial_Pedidos WHERE Historial_PredidosID = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, String.valueOf(historialPedidosID));
        
        ResultSet rs = stmt.executeQuery();
        
        DAO_Pedidos DAOp = new DAO_Pedidos();
        
        HistorialPedidos Historial = new HistorialPedidos();
        if(rs.next()){
            Historial.setHistorialPredidosID(rs.getInt("Historial_PedidosID"));
            Historial.setPedidosCollection(DAOp.findPedidosByHistorialPedidos(Historial));
        }
        
        return Historial;
    }
    
}
