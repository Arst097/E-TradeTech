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
import Inventario.Servicio_Gestor;
import Uso_Comun.DAOs.DAO_Usuario;
import Uso_Comun.Modelos.Usuario;
import Uso_Comun.Servicio_Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import Uso_Comun.DAOs.DAO_Producto;
import Uso_Comun.Modelos.Producto;

/**
 *
 * @author HP PORTATIL
 */
public class Prueba {
    public static void main(String[] args) throws Exception{
        //Servicio_Gestor.prueba();
        //String Token = SqlConnection.login("Hernesto Perez", "Password123");
        //System.out.println(SqlConnection.getProductosByToken(Token));
        //Servicio_Usuario.CrearUsuario(5, "juan", "juan@gmail.com", "password123");
        //Servicio_Usuario.EliminarUsuario(5);
        
        //Prueba_Almacen_Inventario_Productos();
        
        String Correo = "hernesto.perez@example.com";
        String Contraseña_Obtenida = Servicio_Usuario.encryptSHA256("password123");
        System.out.println(Servicio_Usuario.login(Correo,Contraseña_Obtenida));
        
    }
    
    
    
}