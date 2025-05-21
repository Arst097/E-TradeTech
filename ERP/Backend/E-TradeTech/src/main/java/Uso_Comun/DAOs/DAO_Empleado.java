/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.DAOs;

import Inventario.Modelos.Despachador;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Gestores;
import Inventario.Modelos.Gestores;
import Uso_Comun.Modelos.Empleado;
import Ventas.Modelos.Cliente;
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
public class DAO_Empleado implements Serializable {

    public DAO_Empleado() {
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
    
    public Empleado findUsuarioByCorreoAndSHA256(boolean Ch, String Correo, String SHA256) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        String query = "SELECT Empleado_id, Nombre, Correo, Contraseña_SHA256 FROM Usuario WHERE Correo = ? AND Contraseña_SHA256 = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, Correo);
        stmt.setString(2, SHA256);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Empleado empleado = new Empleado();
            empleado.setEmpleadoid(rs.getInt("Empleado_id"));
            empleado.setNombre(rs.getString("Nombre"));
            empleado.setCorreo(rs.getString("Correo"));
            empleado.setContraseñaSHA256(rs.getString("Contraseña_SHA256"));

            return empleado;
        }
        
        return null;
    }
    
    private String obtenerDatosUsuarios(List<Empleado> usuarios) {
        StringBuilder datos = new StringBuilder();

        for (Empleado usuario : usuarios) {
            // Asumiendo que Empleado tiene métodos getNombre() y getEmail()
            datos.append("Correo: ").append(usuario.getCorreo()).append(", ");
            datos.append("ContraseñaSHA256: ").append(usuario.getContraseñaSHA256()).append("\n");
        }

        return datos.toString();
    }

}
