/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas;

import Uso_Comun.Modelos.Pedidos;
import Uso_Comun.DAOs.DAO_Pedidos;
import Inventario.Modelos.Inventario;
import Inventario.Servicio_InventarioVentas;
import Uso_Comun.DAOs.DAO_Producto;
import Uso_Comun.Modelos.Producto;
import Ventas.DAOS.*;
import Ventas.Modelos.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_HacerVenta {

    private static final DAO_Pedidos DAOp = new DAO_Pedidos();
    private static final DAO_Cliente DAOc = new DAO_Cliente();
    private static final DAO_Producto DAOpr = new DAO_Producto();
    
    private static final Servicio_InventarioVentas inventario = new Servicio_InventarioVentas();

    //Retorna el nombre de un cliente ingresando su ID
    //Si no encuentra un cliente, retorna null
    public static String Buscar_Cliente(int ClienteID) {
        try {
            Cliente cliente = DAOc.findCliente(ClienteID);
            if(cliente.equals(null)){
                return null;
            }else{
                return cliente.getNombre();
            }
        } catch (Exception e) {
            return null;
        }
    }

    //Retorna una lista con los modelos de todos los productos que estan en algun inventario libre
    //No repite modelos de productos en la lista
    public static String[] Productos_Disponibles() {
        List<Producto> productos = inventario.Productos_Disponibles();
        
        String[] modelos = productos.stream()
            .map(Producto::getModelo)        // obtenemos los modelos
            .distinct()                      // eliminamos duplicados
            .toArray(String[]::new); 
        return modelos;
    }

    //Retorna el precio unitario de un Producto con su ID
    //retorna -1 cuando no se encuentra nada
    public static float Precio_Unitario(int ProductoID) {
        return DAOpr.findProducto(ProductoID).getPrecio();
    }
    
    //Retorna el precio unitario de un Producto con su Modelo
    //retorna -1 cuando no se encuentra nada
    public static float Precio_Unitario(String ProductoStr) {
        int ProductoID = Obtener_ProductoID(ProductoStr);
        return Precio_Unitario(ProductoID);
    }
    
    //Retorna el codigo de status de la compra
    public static int Hacer_Compra(int ClienteID, String ProductoStr, int Cantidad){
        int Status_Code = Compra_Valida(ClienteID, ProductoStr, Cantidad);
        
        try{
            if(Status_Code == 0){
                Pedidos pedido = new Pedidos();
                
                List<Producto> stockproductos = ProductosEnLista(inventario.Productos_Disponibles(), ProductoStr);;
                List<Producto> productos = new ArrayList<>();
                
                int counter = 0;
                for(Producto producto: stockproductos){
                    if(counter<Cantidad){
                        break;
                    }
                    
                    Inventario inventario = new Inventario(2);
                    producto.setInventarioID(inventario);

                    productos.add(producto);
                    DAOpr.edit(producto);
                    counter++;
                }
                
                pedido.GenerarPedidoSolicitado(ClienteID, productos, 1);
                
                
                DAOp.create(pedido);
            }
        }catch(Exception e){
            Status_Code = -1;
        }
        
        return Status_Code;
    }
    
    private static List<Producto> ProductosEnLista(List<Producto> StockProductos, String Producto){
        List<Producto> productos = new ArrayList<>();
        for(Producto producto: StockProductos){
            if(producto.getModelo().equals(Producto)){
                productos.add(producto);
            }
        }
        return productos;
    }
    
    //Retorna un numero a modo de codigo de status para ver si hay algun problema con la compra
    //Si retorna 0 la compra se hizo
    //Si retorna -1 hubo algun fallo en el proceso, Revisar consola para ver el error
    //Si retorna 1 No hay Stock en inventario
    //Si retorna 2 Hubo un error al encontrar al cliente
    public static int Compra_Valida(int ClienteID, String ProductoStr, int Cantidad){
        try{
            try{
                Cliente cliente = DAOc.findCliente(ClienteID);
                
            }catch(Exception e){
                return 2;
            }
            List<Producto> productos = ProductosEnLista(inventario.Productos_Disponibles(), ProductoStr);
            if(productos.size() < Cantidad){
                return 1;
            }
            return 0;
        }catch(Exception e){
            System.out.println("Error en validacion: "+e);
            return -1;
        }
    }
    
    private static int Obtener_ProductoID(String ProductoStr){
        List<Producto> ProductosInstancia = DAOpr.findProductoByModelo(ProductoStr);
        return ProductosInstancia.get(0).getProductoID();
    }
}
