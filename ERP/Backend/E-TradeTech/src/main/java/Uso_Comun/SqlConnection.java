/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.swing.JOptionPane;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

/**
 *
 * @author HP PORTATIL
 */
public class SqlConnection {
    private static Connection conectar = null;
    
    private static final String usuario = "Access";
    private static final String contraseña = "123";
    private static final String bd = "ETradeTechDB";
    private static final String ip = "localhost";
    private static final String puerto = "1433";
    
    private static final String cadena = "jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;
    
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public static void EstablecerConexion(){
        try{
            String cadena = "jdbc:sqlserver://localhost:"+puerto+";"+"databaseName="+bd+";"+"encrypt=false";
            conectar = DriverManager.getConnection(cadena,usuario,contraseña);
            JOptionPane.showMessageDialog(null, "Conexion Establecida");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en la conexion: "+ e.toString());
            System.out.println(e);
        }
    }
    
    public static ResultSet ejecutarConsulta(String consulta) {
        ResultSet resultado = null;
        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            PreparedStatement stmt = conectar.prepareStatement(consulta);
            resultado = stmt.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ejecutando consulta: " + e.toString());
            System.out.println(e);
        }
        return resultado;
    }
    
    public static String ejecutarConsultaComoString(String consulta) {
        StringBuilder resultadoString = new StringBuilder();
        try {
            ResultSet rs = ejecutarConsulta(consulta);
            if (rs != null) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Append column names
                for (int i = 1; i <= columnCount; i++) {
                    resultadoString.append(metaData.getColumnName(i)).append("\t");
                }
                resultadoString.append("\n");

                // Append rows
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        resultadoString.append(rs.getString(i)).append("\t");
                    }
                    resultadoString.append("\n");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error convirtiendo consulta a String: " + e.toString());
            System.out.println(e);
        }
        return resultadoString.toString();
    }
    
    public static String login(String username, String password) {
        String hashedPassword = hashPassword(password);

        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT Usuario_id FROM Usuario WHERE Nombre = ? AND Contraseña_SHA256 = ?";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("Usuario_id");
                return generateJwtToken(userId);
            } else {
                return "Invalid username or password";
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error during login: " + e.toString());
            System.out.println(e);
            return null;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private static String generateJwtToken(int userId) {
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
    
}
