/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.PreexistingEntityException;
import Inventario.exceptions.RollbackFailureException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.ListaContactos;
import Proveedores.Modelos.Proveedor;
import Proveedores.Modelos.UsuarioCompras;
import Uso_Comun.Modelos.Usuario;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_UsuarioCompras implements Serializable {

    public DAO_UsuarioCompras() {
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

    public UsuarioCompras findUsuarioComprasByUsuarioID(int usuarioID) {
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT * FROM Usuario_Compras WHERE Usuario_Usuario_id = ?;";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, String.valueOf(usuarioID));

            ResultSet rs = stmt.executeQuery();

            UsuarioCompras usuariosCompras = ResultSet_to_UsuarioCompras(rs);

            return usuariosCompras;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_UsuarioCompras.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private UsuarioCompras ResultSet_to_UsuarioCompras(ResultSet rs) throws SQLException {
        List<UsuarioCompras> usuariosCompras = ResultSet_to_ListUsuarioCompras(rs);
        if( usuariosCompras.size() > 0 ){
            return usuariosCompras.get(0);
        }
        return null;
    }

    private List<UsuarioCompras> ResultSet_to_ListUsuarioCompras(ResultSet rs) throws SQLException {
        List<UsuarioCompras> usuariosCompras = new ArrayList<>();
        while (rs.next()) {
            UsuarioCompras usuarioCompras = new UsuarioCompras();

            usuarioCompras.setUsuarioComprasID(rs.getInt("Usuario_ComprasID"));
            
            Usuario usuarioUsuarioid = new Usuario(rs.getInt("Usuario_Usuario_id"));
            usuarioCompras.setUsuarioUsuarioid(usuarioUsuarioid);
            
            Integer listaContactosID_int = rs.getInt("Lista_ContactosID");
            if( !listaContactosID_int.equals(null) || listaContactosID_int > 0 ){
                ListaContactos listaContactosID = new ListaContactos(listaContactosID_int);
                usuarioCompras.setListaContactosID(listaContactosID);
            }
            
            usuarioCompras.setPuesto(rs.getString("Puesto"));
            
            usuarioCompras.setTelefono(rs.getString("Telefono"));

            usuariosCompras.add(usuarioCompras);
        }
        return usuariosCompras;
    }
}
