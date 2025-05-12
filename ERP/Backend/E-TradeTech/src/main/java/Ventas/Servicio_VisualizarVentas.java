/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Ventas.Modelos.Pedidos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_VisualizarVentas {

    private String ListaPedidosToJSON(List<Pedidos> pedidos) {
        int i = 0;
        int size = pedidos.size();
        String json = "";
        
        //todavia no esta cambiado al completo
        for (Pedidos pedido : pedidos) {
            i++;
            
            Integer PedidoID = pedido.getPedidoID();
            //String Cliente = ;
            //String Producto = ;
            //Float Precio = new ArrayList<>(pedido.getProductoCollection()).get(0).;
            Date fecha_inicio = pedido.getFechainicio();

            json = json
                    + "{\"ID_pedido\":\""
                    + PedidoID
                    + "\",\"Cliente\":\""
//                    + Cliente
                    + "\",\"Producto\":\""
//                    + Producto
                    + "\",\"Valor_Total\":"
//                    + Precio
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
