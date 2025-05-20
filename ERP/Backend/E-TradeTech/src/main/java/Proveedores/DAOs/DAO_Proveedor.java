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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
