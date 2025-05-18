/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Inventario.Servicio_Inventario;
import Seguridad.Servicio_Seguridad;
import Uso_Comun.Modelos.Producto;
import Uso_Comun.DAOs.DAO_Pedidos;
import Uso_Comun.Modelos.Pedidos;
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
                return "Error al obtener los datos desde listapedidosJSON: "+e;
            }
        }else{
            return "El usuario no es valido";
        }
    }
    
    private static String PedidosToJSON(List<Pedidos> pedidos) {
        int i = 0;
        int size = pedidos.size();
        System.out.println("cantidad de pedidos: "+size);
        String json = "";
        
        //todavia no esta cambiado al completo
        for (Pedidos pedido : pedidos) {
            i++;
            System.out.println("iteracion for numero "+i);
            
            Producto producto = new ArrayList<>(pedido.getProductoCollection()).get(0);
            System.out.println("Array obtenido de Producto");
            
            Integer PedidoID = pedido.getPedidoID();
            System.out.println("ProductoID obtenido");
            
            String Cliente = pedido.getClienteID().getNombre();
            System.out.println("Cliente obtenido");
            
            String Producto = producto.getModelo();
            System.out.println("Producto abtenido");
            
            Float Precio = producto.getPrecio();
            System.out.println("Precio obtenido");
            
            Date fecha_inicio = pedido.getFechainicio();
            System.out.println("Fecha obtenida");

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
