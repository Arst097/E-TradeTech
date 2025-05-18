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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            List<Oferta> ofertas = null;
            while(rs.next()) {
                Oferta oferta = new Oferta();
                oferta.setOfertasID(rs.getInt("OfertaID"));

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

}
