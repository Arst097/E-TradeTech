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
import java.util.List;

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

        int GestorID = DAOg.findGestorByUsuarioId(false, UsuarioID).getGestorID();

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
                            + "},";

                }
            }
        }

        json = json + "]";

        return json;
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

}
