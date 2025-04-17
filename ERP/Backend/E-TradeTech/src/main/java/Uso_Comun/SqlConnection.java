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
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;

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

    private static final String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + "/" + bd;

    private static final Key secretKey = generateKeyFromString("ContraseñaSuperSecreta");

    public static void EstablecerConexion() {
        try {
            String cadena = "jdbc:sqlserver://localhost:" + puerto + ";" + "databaseName=" + bd + ";" + "encrypt=false";
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            System.out.println("Conexion Establecida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.toString());
            System.out.println(e);
        }
    }

    private static ResultSet ejecutarConsulta(String consulta) {
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

    private static String ejecutarConsultaComoString(String consulta) {
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

    public static String login(String correo, String contraseña) {
        String hashedPassword = hashPassword(contraseña);

        try {
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }

            String query = "SELECT Usuario_id FROM Usuario WHERE Correo = ? AND Contraseña_SHA256 = ?";
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setString(1, correo);
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

    public static String getProductosByToken(String token) {
        try {
            // Decode the token to get the Usuario_id
            int userId = Integer.parseInt(Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());

            // SQL query to retrieve Productos associated with the Usuario_id
            String query = "SELECT p.* "
                    + "FROM Producto p "
                    + "JOIN Inventario i ON p.InventarioID = i.InventarioID "
                    + "JOIN Almacen a ON i.AlmacenID = a.AlmacenID "
                    + "JOIN Gestores g ON a.AlmacenID = g.AlmacenID "
                    + "WHERE g.Usuario_Usuario_id = ?";

            // Execute the query and format the results
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // Convert the result to a readable format
            StringBuilder productos = new StringBuilder();
            while (rs.next()) {
                productos.append("ProductoID: ").append(rs.getInt("ProductoID")).append(", ");
                productos.append("Modelo: ").append(rs.getString("Modelo")).append(", ");
                productos.append("Fecha_Entrada: ").append(rs.getTimestamp("Fecha_Entrada")).append("\n");
            }

            return productos.length() > 0 ? productos.toString() : "No productos found for the given token.";

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println(e);
            return "Error retrieving productos.";
        }
    }

    public static Map<String, Object> getFullDataByToken(String token) {
        try {
            // Decode the token to get the Usuario_id
            int userId = Integer.parseInt(Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());

            // SQL query to retrieve all related information
            String query = "SELECT p.ProductoID, p.Modelo, p.Fecha_Entrada, "
                    + "i.InventarioID, i.tipo AS InventarioTipo, "
                    + "a.AlmacenID, a.Nombre AS AlmacenNombre, a.Direccion, a.Capacidad, a.Telefono "
                    + "FROM Producto p "
                    + "JOIN Inventario i ON p.InventarioID = i.InventarioID "
                    + "JOIN Almacen a ON i.AlmacenID = a.AlmacenID "
                    + "JOIN Gestores g ON a.AlmacenID = g.AlmacenID "
                    + "WHERE g.Usuario_Usuario_id = ?";

            // Execute the query
            if (conectar == null || conectar.isClosed()) {
                EstablecerConexion();
            }
            PreparedStatement stmt = conectar.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // Parse the results into a dictionary-like structure
            Map<String, Object> result = new HashMap<>();
            List<Map<String, Object>> productos = new ArrayList<>();
            Map<String, Object> almacen = new HashMap<>();
            Map<String, Object> inventario = new HashMap<>();

            while (rs.next()) {
                // Add Producto data
                Map<String, Object> producto = new HashMap<>();
                producto.put("ProductoID", rs.getInt("ProductoID"));
                producto.put("Modelo", rs.getString("Modelo"));
                producto.put("Fecha_Entrada", rs.getTimestamp("Fecha_Entrada"));
                productos.add(producto);

                // Add Inventario data (overwrite but it's the same for all)
                inventario.put("InventarioID", rs.getInt("InventarioID"));
                inventario.put("Tipo", rs.getString("InventarioTipo"));

                // Add Almacen data (overwrite but it's the same for all)
                almacen.put("AlmacenID", rs.getInt("AlmacenID"));
                almacen.put("Nombre", rs.getString("AlmacenNombre"));
                almacen.put("Direccion", rs.getString("Direccion"));
                almacen.put("Capacidad", rs.getInt("Capacidad"));
                almacen.put("Telefono", rs.getString("Telefono"));
            }

            // Add everything to the result map
            result.put("Productos", productos);
            result.put("Inventario", inventario);
            result.put("Almacen", almacen);

            return result;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
            System.out.println(e);
            return null;
        }
    }

    public static Key generateKeyFromString(String input) {
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
