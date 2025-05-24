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
import Proveedores.DAOs.DAO_Ofertas;
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
    private static final DAO_Ofertas DAOo = new DAO_Ofertas();
    
    
    public static int CrearProveedor(String nombre, String status, String telefono, String descripcion, String oferta){
        if(!Validar_Status(status)){
            return 1;
        }
        
        try{
            Proveedor proveedor = new Proveedor();
            proveedor.CrearProveedor(nombre,status,telefono,descripcion,1);
            Oferta ofertaObj = new Oferta();
            ofertaObj.ConfigurarOfertaAleatorea(oferta, proveedor);
            DAOp.create(proveedor);
            DAOo.create(ofertaObj);
            return 0;
        }catch(Exception e){
            System.out.println("Error en la creacion: "+e);
            return -1;
        }
    }
    
    private static boolean Validar_Status(String status){
        return status.equals("Activo") || status.equals("Inactivo");
    }
    
}
