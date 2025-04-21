/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author HP PORTATIL
 */
public class VistaGestor {

    private int GestorID;
    private String Nombre;
    private String Correo;
    private String Puesto;
    private String Telefono;
    private Almacen ThisAlmacen;
    
    private class Almacen {
        
        private int AlmacenID;
        private String NombreAlmacen;
        private String DireccionAlmacen;
        private int Capacidad;
        private String TelefonoAlmacen;
        private Inventario InventarioLibre;
        private Inventario InventarioReservado;
        
        private class Inventario {
            private int InventarioID;
            private String tipo;
            private ArrayList<Producto> Productos;
            
            private class Producto{
                private int ProductoID;
                private String Modelo;
                private Date Entrada;
            }
        }
    }

    

}
