/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package APruebas;

import Inventario.Almacen;
import Inventario.DAO_Almacen;
import Inventario.Servicio_Gestor;
import Uso_Comun.DAO_Usuario;
import Uso_Comun.Model_Usuario;
import Uso_Comun.Servicio_Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author HP PORTATIL
 */
public class Prueba {
    public static void main(String[] args) throws Exception{
        Servicio_Gestor.prueba();
        System.out.print("antes\n");
        //Servicio_Usuario.CrearUsuario(5, "juan", "juan@gmail.com", "password123");
        //Servicio_Usuario.EliminarUsuario(5);
        System.out.print("despues\n");
    }
}