/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores;

import Proveedores.DAOs.DAO_Ofertas;
import Proveedores.DAOs.DAO_Proveedor;
import Proveedores.Modelos.Oferta;
import Proveedores.Modelos.Proveedor;
import Seguridad.Servicio_Seguridad;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_visualizarProv {
    
    private static final DAO_Proveedor DAOp = new DAO_Proveedor();
    
    //retorna un string en formato json con todos los datos disponibles en la tabla Pedidos de la BD
    public static String listaproveedoresJSON(String Token){
        int UsuarioID = Servicio_Seguridad.getUserIdFromJwtToken(Token);
        return listaproveedoresJSON(UsuarioID);
    }
    
    //hace lo mismo que listapedidosJSON pero ingresando directamente el UsuarioID
    public static String listaproveedoresJSON_Directo(int UsuarioID){
        return listaproveedoresJSON(UsuarioID);
    }
    
    private static String listaproveedoresJSON(int UsuarioID) {
        boolean Usuario_Valido = Servicio_UsuariosModProv.UsuarioIsUserCompras(UsuarioID);
        
        if(Usuario_Valido){
            try{
                List<Proveedor> Todos_los_Proveedores = DAOp.findProveedores();
                return ProveedoresToJSON(Todos_los_Proveedores);
            }catch(Exception e){
                return "Error al obtener los datos";
            }
        }else{
            return "El usuario no es valido";
        }
    }
    
    private static String ProveedoresToJSON(List<Proveedor> proveedores) {
        int i = 0;
        int size = proveedores.size();
        String json = "";
        
        for (Proveedor proveedor : proveedores) {
            i++;
            
            Integer PedidoID = proveedor.getProveedorID();
            String Nombre = proveedor.getNombre();
            String Telefono = proveedor.getTelefono();
            String Descripcion = proveedor.getDescripcion();
            String Estado = proveedor.getEstado();
            String Oferta = getProductoOfertaDeProveedor(proveedor);

            json = json
                    + "{\"ID\":\""
                    + PedidoID
                    + "\",\"Nombre\":\""
                    + Nombre
                    + "\",\"Telefono\":\""
                    + Telefono
                    + "\",\"Descripcion\":"
                    + Descripcion
                    + ",\"Estado\":"
                    + Estado
                    + ",\"Oferta de Proveedor\":"
                    + Oferta
                    + "}";
            if (i < size) {
                json = json + ",";
            }
        }
        
        return json;
    }
    
    private static final DAO_Ofertas DAOo = new DAO_Ofertas();
    
    private static String getProductoOfertaDeProveedor(Proveedor proveedor){

        List<Oferta> ofertas = DAOo.findOfertasByProveedor(proveedor);
        
        return ofertas.get(0).getProductoOfertado();
    }
}
