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
import java.util.List;

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
        
        pruebaProductoInventario();
        pruebaInventarioGestor();

    }

    private static void pruebaProductoInventario() {
        DAO_Producto dao = new DAO_Producto();
        
        List<Producto> lista = dao.findProductoByInventario(1);
        
        for(Producto producto: lista){
            System.out.println(producto.getProductoID()+": "+producto.getModelo());
        }
    }
    
    private static void pruebaInventarioGestor(){
        DAO_Inventario dao = new DAO_Inventario();
        
        List<Inventario> lista = dao.findInvetarioByGestor(1);
        
        for(Inventario inventario: lista){
            System.out.println(inventario.getInventarioID()+": "+inventario.getTipo());
        }
        
        System.out.println("Valido: " + Servicio_Inventario.validarTipos(lista));
        
        System.out.println(Servicio_Inventario.listaproductosJSON(4));
    }

    private static void pruebaLogin(){
        String Correo = "hernesto.perez@example.com";
        String Contraseña_Obtenida = Servicio_Usuario.encryptSHA256("password123");
        System.out.println(Contraseña_Obtenida);
        System.out.println(Servicio_Usuario.login(Correo, Contraseña_Obtenida, false));
    }
}
