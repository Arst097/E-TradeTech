/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas.DAOS;

import Uso_Comun.DAOs.DAO_Pedidos;
import Uso_Comun.Modelos.Pedidos;
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

    public DAO_Cliente() {
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

    public Cliente findCliente(int ClienteID) {
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
