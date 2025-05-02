/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.DAOs.DAO_Producto;
import Inventario.Modelos.Inventario;
import Inventario.DAOs.DAO_Almacen;
import Inventario.DAOs.DAO_Gestores;
import Inventario.DAOs.DAO_Inventario;
import Seguridad.Servicio_Seguridad;
import Uso_Comun.Modelos.Producto;
import Uso_Comun.Servicio_Usuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_Inventario {

    private static DAO_Inventario DAOi = new DAO_Inventario();
    private static DAO_Producto DAOp = new DAO_Producto();
    private static DAO_Gestores DAOg = new DAO_Gestores();

    public static String listaproductosJSON(String Token) {

        int UsuarioID = Servicio_Seguridad.getUserIdFromJwtToken(Token);

        int GestorID = -1;
        try {
            GestorID = DAOg.findGestorByUsuarioId(false, UsuarioID).getGestorID();
        } catch (SQLException ex) {
            Logger.getLogger(Servicio_Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Inventario> inventarios = DAOi.findInvetarioByGestor(GestorID);

        if (!validarTipos(inventarios)) {
            return "Los inventarios en almacen no coinciden con inventario libre y reservado";
        }

        String json = "[";

        for (Inventario inventario : inventarios) {
            String tipo = inventario.getTipo();

            if (tipo.equals("Libre")) {
                int inventarioID = inventario.getInventarioID();
                List<Object[]> MontosProductos = DAOp.findGrupoProductosByInventario(inventarioID);

                int size = MontosProductos.size();
                int i = 0;
                for (Object[] monto : MontosProductos) {
                    i++;

                    String modelo = (String) monto[0];
                    String categoria = (String) monto[1];
                    Long cantidad = (Long) monto[2];
                    float precio = (float) monto[3];

                    json = json
                            + "{\"id\":\""
                            + i
                            + "\",\"nombre\":\""
                            + modelo
                            + "\",\"categoria\":\""
                            + categoria
                            + "\",\"stock\":"
                            + cantidad
                            + ",\"precio\":"
                            + precio
                            + "}";
                    if(i < size){
                        json = json + ",";
                    }
                }

            }
        }

        json = json + "]";

        return json;
    }

    public static String listaproductosJSON(String correo, String contraseña_encriptada) throws SQLException {
        String Token = Servicio_Usuario.login(correo, contraseña_encriptada, false);
        return listaproductosJSON(Token);
    }

    public static boolean validarTipos(List<Inventario> inventarios) {
        if (inventarios == null || inventarios.size() != 2) {
            return false;
        }

        String tipo1 = inventarios.get(0).getTipo();
        String tipo2 = inventarios.get(1).getTipo();

        return (("Libre".equals(tipo1) && "Reservado".equals(tipo2))
                || ("Reservado".equals(tipo1) && "Libre".equals(tipo2)));
    }

    public static boolean EditarMontoProductos(int UsuarioID, String nombre, String categoria, String StockStr, String PrecioStr) throws Exception {
        int Stock = Integer.valueOf(StockStr);
        float Precio = Float.valueOf(PrecioStr);

        int GestorID = DAOg.findGestorByUsuarioId(false, UsuarioID).getGestorID();

        List<Inventario> inventarios = DAOi.findInvetarioByGestor(GestorID);

        for (Inventario inventario : inventarios) {
            int inventarioID = inventario.getInventarioID();
            String tipo = inventario.getTipo();
            if (tipo.equals("Libre")) {
                int Qi = inventario.getProductoCollection().size();
                if (Qi < Stock) {
                    int Q = Stock - Qi;
                    List<Producto> productos = DAOp.findModel_ProductoEntities(Q, 0);
                    for (Producto producto : productos) {
                        int productoID = producto.getProductoID();
                        DAOp.destroy(productoID);
                    }
                } else if (Qi > Stock) {
                    for (int i = 1; i > Stock; i++) {
                        Date fecha = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Producto producto = new Producto(Precio, categoria, nombre, fecha, inventario);
                        DAOp.create(producto);
                    }
                }
            }
        }

        return true;
    }

    public static boolean EditarMontoProductos(String correo, String contraseña_encriptada, String nombre, String categoria, String StockStr, String PrecioStr) throws Exception {
        String Token = Servicio_Usuario.login(correo, contraseña_encriptada, false);
        return EditarMontoProductos(Token, nombre, categoria, StockStr, PrecioStr);
    }

    public static boolean EditarMontoProductos(String Token, String nombre, String categoria, String StockStr, String PrecioStr) throws Exception {
        int UsuarioID = Servicio_Seguridad.getUserIdFromJwtToken(Token);
        return EditarMontoProductos(UsuarioID, nombre, categoria, StockStr, PrecioStr);
    }
}
