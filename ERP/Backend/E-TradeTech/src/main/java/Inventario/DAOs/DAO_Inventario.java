/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.DAOs;

import static Inventario.DAOs.DAO_Gestores.EstablecerConexion;
import Inventario.Modelos.Almacen;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Inventario;
import Inventario.Modelos.TransaccionInv;
import Uso_Comun.Modelos.Producto;
import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import Uso_Comun.DAOs.DAO_Producto;
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
public class DAO_Inventario implements Serializable {


    public DAO_Inventario() {
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

    private static final DAO_Producto DAOp = new DAO_Producto();
    
    public List<Inventario> findInvetarioByGestor(Integer GestorID) throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }

        String query = "SELECT i.* FROM Inventario i join Almacen a on i.AlmacenID = a.AlmacenID join Gestores g on g.AlmacenID = a.AlmacenID where GestorID = ?;";
        PreparedStatement stmt = conectar.prepareStatement(query);
        stmt.setString(1, String.valueOf(GestorID));

        ResultSet rs = stmt.executeQuery();

        List<Inventario> inventarios = new ArrayList<Inventario>();
        int i = 0;
        while (rs.next()) {
            i++;
            Inventario inventario = new Inventario();
            inventario.setInventarioID(rs.getInt("InventarioID"));
            inventario.setTipo(rs.getString("tipo"));

            inventarios.add(inventario);
        }
        if (i == 0) {
            inventarios = null;
        }
        return inventarios;
    }


    public List<Inventario> findInventariosLibres() throws SQLException {
        if (conectar == null || conectar.isClosed()) {
            EstablecerConexion();
        }
        
        String query = "SELECT i.* FROM Inventario i where tipo = 'Libre';";
        PreparedStatement stmt = conectar.prepareStatement(query);
        
        ResultSet rs = stmt.executeQuery();

        List<Inventario> inventarios = new ArrayList<Inventario>();
        int i = 0;
        while (rs.next()) {
            i++;
            Inventario inventario = new Inventario();
            inventario.setInventarioID(rs.getInt("InventarioID"));
            inventario.setTipo(rs.getString("tipo"));
            
            Collection<Producto> productos = DAOp.find_toInventario(inventario);
            inventario.setProductoCollection(productos);

            inventarios.add(inventario);
        }
        if (i == 0) {
            inventarios = null;
        }
        return inventarios;
    }
}
