/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Inventario.Servicio_Inventario;
import Seguridad.Servicio_Seguridad;
import Uso_Comun.Modelos.Producto;
import Ventas.DAOS.DAO_Pedidos;
import Ventas.Modelos.Pedidos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * las funciones publicas son dos 
 * listapedidosJSON(String) - Ingresa con un token
 * listapedidosJSON_Directo (int) - Ingresa directamente el ID de USUARIO, no su ID de gestor
 * 
 * para ver como esta creado el json que se devuelve, mira la funcion PedidosToJSON
 */
public class Servicio_VisualizarVentas {

    private static final DAO_Pedidos DAOp = new DAO_Pedidos();
    
    //retorna un string en formato json con todos los datos disponibles en la tabla Pedidos de la BD
    public static String listapedidosJSON(String Token){
        int UsuarioID = Servicio_Seguridad.getUserIdFromJwtToken(Token);
        return listapedidosJSON(UsuarioID);
    }
    
    //hace lo mismo que listapedidosJSON pero ingresando directamente el UsuarioID
    public static String listapedidosJSON_Directo(int UsuarioID){
        return listapedidosJSON(UsuarioID);
    }
    
    private static String listapedidosJSON(int UsuarioID) {
        boolean Usuario_Valido = Servicio_Inventario.UsuarioIsGestor(UsuarioID);
        
        if(Usuario_Valido){
            try{
                List<Pedidos> Todos_los_Pedidos = DAOp.findPedidos();
                return PedidosToJSON(Todos_los_Pedidos);
            }catch(Exception e){
                return "Error al obtener los datos";
            }
        }else{
            return "El usuario no es valido";
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
            String Cliente = pedido.getClienteID().getNombre();
            String Producto = producto.getModelo();
            Float Precio = producto.getPrecio();
            Date fecha_inicio = pedido.getFechainicio();

            json = json
                    + "{\"ID_pedido\":\""
                    + PedidoID
                    + "\",\"Cliente\":\""
                    + Cliente
                    + "\",\"Producto\":\""
                    + Producto
                    + "\",\"Valor_Total\":"
                    + Precio
                    + ",\"Fecha\":"
                    + fecha_inicio
                    + "}";
            if (i < size) {
                json = json + ",";
            }
        }
        
        return json;
    }
}
