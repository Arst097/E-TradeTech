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

    //Retorna null si no se encontro ningun Cliente con ese ID
    public static String Buscar_Cliente(int ClienteID) {
        try {

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    //Retorna una lista con los modelos de todos los productos que estan en algun inventario libre
    //No repite modelos de productos en la lista
    public static String[] Productos_Disponibles() {
        
        
        
        return null;
    }

    //Retorna el precio unitario de un Producto con su ID
    //retorna -1 cuando no se encuentra nada
    public static float Precio_Unitario(int ProductoID) {

        return -1;
    }
    
    //Retorna el precio unitario de un Producto con su Modelo
    //retorna -1 cuando no se encuentra nada
    public static float Precio_Unitario(String ProductoStr) {
        int ProductoID = Obtener_ProductoID(ProductoStr);
        return Precio_Unitario(ProductoID);
    }
    
    //Retorna el codigo de status de la compra
    public static int Hacer_Compra(int ProductoID, int Cantidad){
        int Status_Code = Compra_Valida(ProductoID, Cantidad);
        
        try{
            if(Status_Code == 0){
                
            }
        }catch(Exception e){
            Status_Code = -1;
        }
        
        return Status_Code;
    }
    
    public static int Hacer_Compra(String ProductoStr, int Cantidad){
        int ProductoID = Obtener_ProductoID(ProductoStr);
        return Hacer_Compra(ProductoID, Cantidad);
    }
    
    //Retorna un numero a modo de codigo de status para ver si hay algun problema con la compra
    //Si retorna 0 la compra se hizo
    //Si retorna -1 hubo algun fallo en el proceso, Revisar consola para ver el error
    //Si retorna 1 No hay Stock en inventario
    public static int Compra_Valida(int ProductoID, int Cantidad){
        try{
            
            return 0;
        }catch(Exception e){
            System.out.println("Error Al realizar compra: "+e);
            return -1;
        }
    }
    
    public static int Compra_Valida(String ProductoStr, int Cantidad){
        int ProductoID = Obtener_ProductoID(ProductoStr);
        return Compra_Valida(ProductoID, Cantidad);
    }
    
    private static int Obtener_ProductoID(String ProductoStr){
        
        return -1;
    }
}
