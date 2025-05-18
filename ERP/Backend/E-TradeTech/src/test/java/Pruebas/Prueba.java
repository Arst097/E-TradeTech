/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Inventario.Modelos.Almacen;
import Inventario.DAOs.DAO_Almacen;
import Inventario.DAOs.DAO_Gestores;
import Inventario.DAOs.DAO_Inventario;
import Inventario.Modelos.Gestores;
import Inventario.Modelos.Inventario;
import Inventario.Servicio_Inventario;
import Inventario.*;
import Uso_Comun.DAOs.DAO_Usuario;
import Uso_Comun.Modelos.Usuario;
import Uso_Comun.Servicio_Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import Uso_Comun.DAOs.DAO_Producto;
import Uso_Comun.Modelos.Producto;
import Ventas.Servicio_VisualizarVentas;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class Prueba {

    public static void main(String[] args) throws Exception {
        //Servicio_Gestor.prueba();
        //String Token = SqlConnection.login("Hernesto Perez", "Password123");
        //System.out.println(SqlConnection.getProductosByToken(Token));
        //Servicio_Usuario.CrearUsuario(5, "juan", "juan@gmail.com", "password123");
        //Servicio_Usuario.EliminarUsuario(5);

        //Prueba_Almacen_Inventario_Productos();
        //String Token = pruebaLogin("hernesto.perez@example.com","password123");
        //pruebaProductoInventario();
        //System.out.println(pruebaInventarioGestor(Token));
        //pruebaCrearMonto();
        
        System.out.println("Resultado: "+pruebaVizualizarPedidos());

    }

//    private static void pruebaProductoInventario() {
//        DAO_Producto dao = new DAO_Producto();
//
//        List<Producto> lista = dao.findProductoByInventario(1);
//
//        for (Producto producto : lista) {
//            System.out.println(producto.getProductoID() + ": " + producto.getModelo());
//        }
//    }

    private static String pruebaVizualizarVentas(){
        try {
            String Token = pruebaLogin("carlos@empresa.com", "gestor123");
            System.out.println(Token);
            return Servicio_VisualizarVentas.listapedidosJSON(Token);
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            return "Error: " + ex;
        }
    }
    
    private static String pruebaVizualizarPedidos(){
        try {
            String Token = pruebaLogin("carlos@empresa.com", "gestor123");
            System.out.println(Token);
            return Servicio_VisualizarPedidos.listapedidosJSON(Token);
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            return "Error: " + ex;
        }
    }
    
    private static String pruebaInventarioGestor(String Token) {
        DAO_Inventario dao = new DAO_Inventario();

        List<Inventario> lista = null;
        try {
            lista = dao.findInvetarioByGestor(1);
        } catch (SQLException ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Inventario inventario : lista) {
            System.out.println(inventario.getInventarioID() + ": " + inventario.getTipo());
        }

        System.out.println("Valido: " + Servicio_Inventario.validarTipos(lista));

        return Servicio_Inventario.listaproductosJSON(Token);
    }

    private static String pruebaLogin(String Correo, String ContraseñaLimpia) throws SQLException {
        String Contraseña_Obtenida = Servicio_Usuario.encryptSHA256(ContraseñaLimpia);
        System.out.println(Contraseña_Obtenida);
        String Token_Obtenido = Servicio_Usuario.login(Correo, Contraseña_Obtenida, false);
        System.out.println(Token_Obtenido);
        return Token_Obtenido;
    }

    private static void pruebaCrearMonto() {
        try {
            String Token = pruebaLogin("carlos@empresa.com", "gestor123");
            System.out.println(Token);
            String nombre = "Modelo HP";
            String categoria = "Computadora";
            String StockStr = "2";
            String PrecioStr = "2000";
            Servicio_Inventario.CrearMontoProductos(Token, nombre, categoria, StockStr, PrecioStr);

        } catch (Exception ex) {
            Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void PruebaBorrarMonto() {
        
    }
}
