/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.DAOs;

import Inventario.Modelos.Almacen;
import Inventario.Modelos.Gestores;
import Uso_Comun.Modelos.Empleado;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import static Uso_Comun.DAOs.DAO_Empleado.EstablecerConexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Gestores implements Serializable {

    public DAO_Gestores() {
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
    
    public Gestores findGestorByEmpleadoId(boolean ch, Integer empleadoId) throws SQLException {
        
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        
        String query = "SELECT * FROM Gestores WHERE Usuario_Usuario_id = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, String.valueOf(empleadoId));
        
        ResultSet rs = stmt.executeQuery();
        
        Gestores gestor = null;
        if(rs.next()){
            gestor = new Gestores();
            gestor.setGestorID(rs.getInt("GestorID"));
        }
        
        return gestor;
    }

}
