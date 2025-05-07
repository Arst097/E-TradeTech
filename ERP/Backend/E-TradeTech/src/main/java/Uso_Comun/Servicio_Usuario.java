/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Inventario.DAOs.DAO_Gestores;
import Uso_Comun.DAOs.DAO_Usuario;
import Uso_Comun.Modelos.Pedidos;
import Uso_Comun.Modelos.Usuario;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.RollbackFailureException;
import Seguridad.Servicio_Seguridad;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_Usuario {

    private static DAO_Usuario DAO = new DAO_Usuario();
    
    public static String encryptSHA256(String input) {
        return Servicio_Seguridad.encryptSHA256(input);
    }
    
    public static String login(String correo, String contraseña_encriptada, boolean b) throws SQLException {
        
        Usuario usuario = DAO.findUsuarioByCorreoAndSHA256(b, correo, contraseña_encriptada);
        
        if(usuario == null){
            return "Usuario No Encontrado";
        }
        DAO_Gestores TempDAO = new DAO_Gestores();
        if(TempDAO.findGestorByUsuarioId(b,usuario.getUsuarioid()) == null){
            return "Usuario No Gestor";
        }
        return Servicio_Seguridad.generateJwtToken(usuario.getUsuarioid());
    }
    
    public static boolean TokenValido(String token) {
        return Servicio_Seguridad.TokenValido(token);
    }
}
