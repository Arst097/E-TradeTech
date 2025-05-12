/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Ventas.Modelos.Pedidos;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_VisualizarVentas {

    private String ListaPedidosToJSON(List<Pedidos> pedidos) {
        for (Pedidos pedido : pedidos) {

            var modelo = pedido.get;
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
            if (i < size) {
                json = json + ",";
            }
        }
    }
}
