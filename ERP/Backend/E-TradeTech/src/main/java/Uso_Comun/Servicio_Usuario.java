/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Inventario.exceptions.RollbackFailureException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_Usuario {

    public static DAO_Usuario DAO = new DAO_Usuario();

    public static void CrearUsuario(Model_Usuario Usuario) throws RollbackFailureException, Exception {
        DAO.create(Usuario);
    }
    
    public static void CrearUsuario(Integer usuarioid, String nombre, String correo, String contraseñaLimpia) throws Exception{
        Model_Usuario Usuario = new Model_Usuario(usuarioid, nombre, correo, encryptSHA256("contraseñaLimpia"));
        CrearUsuario(Usuario);
    }

            
    public static String encryptSHA256(String input) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplicar el hash al array de bytes del input
            byte[] hash = digest.digest(input.getBytes());

            // Convertir el array de bytes a formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // En caso de que no exista el algoritmo (no debería ocurrir con SHA-256)
            throw new RuntimeException("Error al obtener instancia de SHA-256", e);
        }
    }
}
