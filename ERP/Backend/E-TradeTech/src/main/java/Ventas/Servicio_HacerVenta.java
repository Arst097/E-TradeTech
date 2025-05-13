/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Ventas.DAOS.*;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_HacerVenta {

    private static final DAO_Pedidos DAOp = new DAO_Pedidos();
    private static final DAO_Cliente DAOc = new DAO_Cliente();

    //retorna null si no se encontro ningun Cliente con ese ID
    public static String Buscar_Cliente(int ClienteID) {
        try {

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] Productos_Disponibles() {

        return null;
    }

    public static float Precio_Unitario(int Cantidad) {

        return -1;
    }
    
    public static int Compra_Valida(int ProductoID, int Cantidad){
        try{
            
            return 0;
        }catch(Exception e){
            return -1;
        }
    }
    
    public static int Compra_Valida(String ProductoStr, int Cantidad){
        //hay que obtener el productoID
        int ProductoID = -1;
        
        return Compra_Valida(ProductoID, Cantidad);
    }
}
