/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Inventario.DAOs.*;
import Inventario.Modelos.Inventario;
import Uso_Comun.DAOs.DAO_Producto;
import Uso_Comun.Modelos.Producto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author HP PORTATIL
 */
public class Servicio_InventarioVentas {
    DAO_Inventario DAOi = new DAO_Inventario();
    DAO_Producto DAOp = new DAO_Producto();

    public List<Producto> Productos_Disponibles() {
        List<Producto> productosDisponibles = new ArrayList<>();
        
        List<Inventario> inventarios = null;
        try {
            inventarios = DAOi.findInventariosLibres();
            System.out.println("cantidad inventarios libres: "+inventarios.size());
        } catch (SQLException ex) {
            System.out.println("Error al encontrar Inventario libre: "+ex);
        }
        
        for(Inventario inventario : inventarios){
            List<Producto> productosInstancia = new ArrayList<>(inventario.getProductoCollection());
            for(Producto producto : productosInstancia){
                productosDisponibles.add(producto);
            }
        }
        return productosDisponibles;
    }
    
}
