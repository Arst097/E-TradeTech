/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_Seguridad {

    private static final Key secretKey = generateKeyFromString("ContraseñaSuperSecreta");

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

    public static boolean TokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true; // Token válido (firma correcta y estructura OK)
        } catch (ExpiredJwtException ex) {
            // Token fue generado por tu sistema pero está expirado
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Token inválido (firma incorrecta, estructura mal formada, etc)
            return false;
        }
    }

    public static String generateJwtToken(int userId) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        Date expiryDate = new Date(currentTimeMillis + (1000 * 60 * 60 * 24)); // Token valid for 24 hours

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public static int getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }

    private static Key generateKeyFromString(String input) {
        try {
            // Hash the input string using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Use the hash as the key and create a SecretKeySpec
            return new SecretKeySpec(hash, "HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating key: " + e.toString(), e);
        }
    }
}
