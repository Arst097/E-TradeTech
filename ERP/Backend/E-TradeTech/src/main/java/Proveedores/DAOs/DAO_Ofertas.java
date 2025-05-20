/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import Proveedores.Modelos.Oferta;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.Proveedor;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_Ofertas implements Serializable {

    public DAO_Ofertas() {
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

    public List<Oferta> findOfertasByProveedor(Proveedor proveedor) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Ofertas WHERE ProveedorID = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);

            int proveedorID = proveedor.getProveedorID();
            stmt.setString(1, String.valueOf(proveedorID));

            ResultSet rs = stmt.executeQuery();

            List<Oferta> ofertas = new ArrayList<>();
            while (rs.next()) {
                Oferta oferta = new Oferta();
                oferta.setOfertasID(rs.getInt("OfertasID"));

                oferta.setPrecioUnidad(rs.getString("Precio_Unidad"));
                oferta.setProductoOfertado(rs.getString("Producto_Ofertado"));

                int proveedorID_i = rs.getInt("ProveedorID");
                Proveedor proveedor_i = new Proveedor(proveedorID_i);
                oferta.setProveedorID(proveedor_i);

                ofertas.add(oferta);
            }

            return ofertas;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_Ofertas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Integer obtenerIDValida() {
        return this.findTamañoDeTabla() + 1;
    }

    private int findTamañoDeTabla() {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            
            String query
                = "SELECT "
                + "COUNT(*) AS cantidad "
                + "FROM Ofertas";
            
            PreparedStatement stmt = conectar.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

        int tamañoTabla = 0;
        if (rs.next()) {
            tamañoTabla = rs.getInt("cantidad");
        }

        return tamañoTabla;
        } catch (SQLException ex) {
            System.out.println("Error en Objeto DAO_Ofertas.findTamañoDeTabla(): "+ex);
            return -1;
        }
    }

    public void create(Oferta oferta) {
        System.out.println("Entra a metodo create de Productos");
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            if (oferta.getOfertasID() == null) {
                oferta.setOfertasID(this.obtenerIDValida());
            }

            String query = "INSERT Ofertas (OfertasID, ProveedorID, Producto_Ofertado, Precio_Unidad";
            int QSimbolQuestions = 4;

            query = query + ") VALUES (";

            for (int i = 1; i <= QSimbolQuestions; i++) {
                query = query + "?";
                if (i != QSimbolQuestions) {
                    query = query + ",";
                }
            }
            query = query + ");";

            System.out.println(query);

            Integer OfertaID = oferta.getOfertasID();
            Integer ProveedorID = oferta.getProveedorID().getProveedorID();
            String Producto_Ofertado = oferta.getProductoOfertado();
            String Precio_Unidad = oferta.getPrecioUnidad();

            CallableStatement cs = conectar.prepareCall(query);

            cs.setInt(1, OfertaID);
            cs.setInt(2, ProveedorID);
            cs.setString(3, Producto_Ofertado);
            cs.setString(4, Precio_Unidad);

            System.out.println("Intenta hacer la insercion");
            cs.execute();
        } catch (SQLException ex) {
            System.out.println("Error en create: " + ex);
        }
    }

}
