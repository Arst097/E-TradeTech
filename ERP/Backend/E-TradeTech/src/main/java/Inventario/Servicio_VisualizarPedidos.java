/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Seguridad.Servicio_Seguridad;
import Uso_Comun.DAOs.DAO_Pedidos;
import Uso_Comun.Modelos.Empleado;
import Uso_Comun.Modelos.Pedidos;
import Uso_Comun.Modelos.Producto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_VisualizarPedidos {
    private static final DAO_Pedidos DAOp = new DAO_Pedidos();
    
    //retorna un string en formato json con todos los datos disponibles en la tabla Pedidos de la BD
    public static String listapedidosJSON(String Token){
        int EmpleadoID = Servicio_Seguridad.getUserIdFromJwtToken(Token);
        return listapedidosJSON(EmpleadoID);
    }
    
    //hace lo mismo que listapedidosJSON pero ingresando directamente el EmpleadoID
    public static String listapedidosJSON_Directo(int EmpleadoID){
        return listapedidosJSON(EmpleadoID);
    }
    
    private static String listapedidosJSON(int EmpleadoID) {
        boolean Empleado_Valido = Servicio_Inventario.EmpleadoIsGestor(EmpleadoID);
        
        if(Empleado_Valido){
            try{
                List<Pedidos> Todos_los_Pedidos = DAOp.findPedidos();
                return PedidosToJSON(Todos_los_Pedidos);
            }catch(Exception e){
                return "Error al obtener los datos";
            }
        }else{
            return "El empleado no es valido";
        }
    }
    
    private static String PedidosToJSON(List<Pedidos> pedidos) {
        int i = 0;
        int size = pedidos.size();
        String json = "";
        
        //todavia no esta cambiado al completo
        for (Pedidos pedido : pedidos) {
            i++;
            
            Producto producto = new ArrayList<>(pedido.getProductoCollection()).get(0);
            
            Integer PedidoID = pedido.getPedidoID();
            String Nombre = producto.getModelo();
            Float Precio = producto.getPrecio();
            String Estado = pedido.getEstado();
            String Detalles = "*Descripcion*";
            String Contacto = pedido.getClienteID().getTelefono();

            json = json
                    + "{\"ID_pedido\":\""
                    + PedidoID
                    + "\",\"Nombre\":\""
                    + Nombre
                    + "\",\"Precio\":\""
                    + Precio
                    + "\",\"Estado\":"
                    + Estado
                    + ",\"Detalles\":"
                    + Detalles
                    + ",\"Contacto\":"
                    + Contacto
                    + "}";
            if (i < size) {
                json = json + ",";
            }
        }
        
        return json;
    }
}
