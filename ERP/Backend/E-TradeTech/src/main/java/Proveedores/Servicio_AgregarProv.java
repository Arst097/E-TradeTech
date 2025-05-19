/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores;

import Ventas.*;
import Uso_Comun.Modelos.Pedidos;
import Uso_Comun.DAOs.DAO_Pedidos;
import Inventario.Modelos.Inventario;
import Inventario.Servicio_InventarioVentas;
import Proveedores.DAOs.DAO_Proveedor;
import Proveedores.Modelos.ListaContactos;
import Proveedores.Modelos.Oferta;
import Proveedores.Modelos.Proveedor;
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
public class Servicio_AgregarProv {

    private static final DAO_Proveedor DAOp = new DAO_Proveedor();
    
    
    public static int CrearProveedor(String nombre, String status, String telefono, String descripcion, String oferta){
        if(!Validar_Status(status)){
            return 1;
        }
        
        try{
            
        }catch(Exception e){
            System.out.println("Error en la creacion: "+e);
            return -1;
        }
        return -1;
    }
    
    private static boolean Validar_Status(String status){
        return status.equals("Activo") || status.equals("Inactivo");
    }
    
    private static void IncercionProveedor(String nombre, String descripcion, String telefono, String estado, ListaContactos listaContactos){
        Proveedor proveedor = new Proveedor(nombre, descripcion, telefono, estado, listaContactos);
        DAOp.create(proveedor);
    }
    
    private static void IncercionProveedor(String nombre, String descripcion, String telefono, String estado, Integer listaContactos){
        ListaContactos listaContactos_obj = new ListaContactos(listaContactos);
        
        IncercionProveedor(nombre, descripcion, telefono, estado, listaContactos_obj);
    }
    
    private static void IncercionOfertaAleatorea(String productoOfertado, Proveedor proveedor){
        Oferta oferta = new Oferta();
        oferta.ConfigurarOfertaAleatorea(productoOfertado,proveedor);
    }
    
    private static void IncercionOfertaAleatorea(String productoOfertado, Integer proveedorID){
        Proveedor proveedor = new Proveedor(proveedorID);
        IncercionOfertaAleatorea(productoOfertado, proveedor);
    }
}
