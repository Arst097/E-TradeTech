/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.ListaContactos;
import Proveedores.Modelos.Oferta;
import Proveedores.Modelos.Proveedor;
import Uso_Comun.Modelos.Pedidos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import java.sql.CallableStatement;
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
public class DAO_Proveedor implements Serializable {

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
    
    public DAO_Proveedor() {
    }

    public List<Proveedor> findProveedores() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Proveedor;";
            PreparedStatement stmt = conectar.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            List<Proveedor> proveedores = new ArrayList<>();
            while(rs.next()) {
                Proveedor proveedor = new Proveedor();
                
                proveedor.setProveedorID(rs.getInt("ProveedorID"));
                
                ListaContactos listaContactos = new ListaContactos(rs.getInt("Lista_ContactosID"));
                proveedor.setListaContactosID(listaContactos);
                
                proveedor.setNombre(rs.getString("Nombre"));
                
                proveedor.setDescripcion(rs.getString("Descripcion"));
                
                proveedor.setTelefono(rs.getString("Telefono"));

                proveedor.setEstado(rs.getString("Estado"));
                
                proveedores.add(proveedor);
            }

            return proveedores;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Proveedor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void create(Proveedor proveedor) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            if (proveedor.getProveedorID() == null) {
                proveedor.setProveedorID(obtenerIDValida());
            }

            if (proveedor.getEstado() == null){
                proveedor.setEstado("Inactivo");
            }
            
            if (proveedor.getDescripcion() == null){
                proveedor.setDescripcion("");
            }

            String query = "SET IDENTITY_INSERT Proveedor ON; "
                    + "INSERT Proveedor (ProveedorID, Lista_ContactosID, Nombre, Descripcion, Telefono, Estado";
            int QSimbolQuestions = 6;

            query = query + ") VALUES (";

            for (int i = 1; i <= QSimbolQuestions; i++) {
                query = query + "?";
                if (i != QSimbolQuestions) {
                    query = query + ",";
                }
            }
            query = query + ");";

            System.out.println(query);

            Integer ProveedorID = proveedor.getProveedorID();
            Integer Lista_ContactosID = proveedor.getListaContactosID().getListaContactosID();
            String Nombre = proveedor.getNombre();
            String Descripcion = proveedor.getDescripcion();
            String Telefono = proveedor.getTelefono();
            String Estado = proveedor.getEstado();

            CallableStatement cs = conectar.prepareCall(query);

            cs.setInt(1, ProveedorID);
            cs.setInt(2, Lista_ContactosID);
            cs.setString(3, Nombre);
            cs.setString(4, Descripcion);
            cs.setString(5, Telefono);
            cs.setString(6, Estado);

            System.out.println("Intenta hacer la insercion");
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en create: " + ex);
        }
    }

    public Integer obtenerIDValida() {
        return this.findIDDisponible();
    }

        private Integer findIDDisponible() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String tabla = "Proveedor";
            String c_id = "ProveedorID";
            
            String query
                    = "SELECT MIN(t1."+c_id+") + 1 AS PrimerIdDisponible "
                    + "FROM "+tabla+" t1 "
                    + "LEFT JOIN "+tabla+" t2 ON t1."+c_id+" + 1 = t2."+c_id+" "
                    + "WHERE t2."+c_id+" IS NULL";
            
            PreparedStatement stmt = conectar.prepareStatement(query);
            
            ResultSet rs = stmt.executeQuery();
            
            int tamañoTabla = 0;
            if (rs.next()) {
                tamañoTabla = rs.getInt("PrimerIdDisponible");
                System.out.println("PrimerIdDisponible: "+tamañoTabla);
            }
            
            return tamañoTabla;
        } catch (SQLException ex) {
            System.out.println("Error en DAO_Proveedor.findTamañoDeTabla(): "+ex);
            return null;
        }
    }

}
