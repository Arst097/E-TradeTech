/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RRHH.DAOs;

import RRHH.Modelos.Empleado;
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
    
    private static final String tabla = "Empleado";
    private static final String[] columnas = {
        "Empleado_id",
        "Nombre",
        "Correo",
        "Contraseña_SHA256",
        "Departamento",
        "Salario",
        "Fecha_Ingreso",
        "Contrato"
    };

    public Empleado findUsuarioByCorreoAndSHA256(boolean Ch, String Correo, String SHA256) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        String query = "SELECT Empleado_id, Nombre, Correo, Contraseña_SHA256 FROM Empleado WHERE Correo = ? AND Contraseña_SHA256 = ?";
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

    public List<Empleado> findEmpleados() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Empleado;";
            PreparedStatement stmt = conectar.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setEmpleadoid(rs.getInt("Empleado_id"));

                empleado.setNombre(rs.getString("Nombre"));

                empleado.setCorreo(rs.getString("Correo"));

                empleado.setContraseñaSHA256(rs.getString("Contraseña_SHA256"));

                empleado.setDepartamento(rs.getString("Departamento"));

                empleado.setSalario(rs.getInt("Salario"));

                empleado.setFechaIngreso(rs.getDate("Fecha_Ingreso"));

                empleado.setContrato(rs.getString("Contrato"));

                empleados.add(empleado);
            }

            return empleados;
        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en findEmpleados: " + ex);
            return null;
        }
    }

    public Integer obtenerIDValida() {
        return findIDDisponible();
    }

    public boolean correo_existe(String correo) {
    try {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        String query = "SELECT 1 FROM Empleado WHERE Correo = ?";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, correo);

        ResultSet rs = stmt.executeQuery();
        boolean existe = rs.next();

        rs.close();
        stmt.close();

        return existe;
    } catch (SQLException e) {
        throw new RuntimeException("Error en DAO_Empleado.correo_existe: "+e);
    }
}

    public void create(Empleado empleado) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            if (empleado.getEmpleadoid() == null) {
                empleado.setEmpleadoid(obtenerIDValida());
            }

            if (
            empleado.getCorreo() == null && 
            empleado.getContraseñaSHA256() == null && 
            empleado.getNombre() != null
                )
            {
                empleado.generar_credenciales(empleado.getNombre());
            }
            
            if (empleado.getFechaIngreso() == null){
                empleado.setFechaIngreso(new Date());
            }

            String query = "INSERT "+tabla+" (";
            
            for(int i = 0; i < columnas.length; i++){
                query = query + columnas[i];
                if (i+1 != columnas.length) {
                    query = query + ",";
                }
            }
            
            int QSimbolQuestions = columnas.length;

            query = query + ") VALUES (";

            for (int i = 1; i <= QSimbolQuestions; i++) {
                query = query + "?";
                if (i != QSimbolQuestions) {
                    query = query + ",";
                }
            }
            query = query + ");";

            System.out.println(query);

            Integer empleado_id = empleado.getEmpleadoid();
            String nombre = empleado.getNombre();
            String correo = empleado.getCorreo();
            String contraseña_SHA256 = empleado.getContraseñaSHA256();
            String departamento = empleado.getDepartamento();
            Integer salario = empleado.getSalario();
            Date fecha_ingreso = empleado.getFechaIngreso();
            String contrato = empleado.getContrato();

            CallableStatement cs = conectar.prepareCall(query);

            cs.setInt(1, empleado_id);
            cs.setString(2, nombre);
            cs.setString(3, correo);
            cs.setString(4, contraseña_SHA256);
            cs.setString(5, departamento);
            cs.setInt(6, salario);
            cs.setTimestamp(7, new Timestamp(fecha_ingreso.getTime()));
            cs.setString(8, contrato);

            System.out.println("Intenta hacer la insercion");
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en create: " + ex);
        }
    }

    private Integer findIDDisponible() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String c_id = "Empleado_id";

            String query
                    = "SELECT MIN(t1." + c_id + ") + 1 AS PrimerIdDisponible "
                    + "FROM " + tabla + " t1 "
                    + "LEFT JOIN " + tabla + " t2 ON t1." + c_id + " + 1 = t2." + c_id + " "
                    + "WHERE t2." + c_id + " IS NULL";

            PreparedStatement stmt = conectar.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            int tamañoTabla = 0;
            if (rs.next()) {
                tamañoTabla = rs.getInt("PrimerIdDisponible");
                System.out.println("PrimerIdDisponible: " + tamañoTabla);
            }

            return tamañoTabla;
        } catch (SQLException ex) {
            System.out.println("Error en DAO_Proveedor.findTamañoDeTabla(): " + ex);
            return null;
        }
    }

}
