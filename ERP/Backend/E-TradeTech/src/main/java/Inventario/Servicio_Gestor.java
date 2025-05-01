/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.DAOs.DAO_Producto;
import Inventario.Modelos.Inventario;
import Inventario.DAOs.DAO_Almacen;
import Inventario.DAOs.DAO_Inventario;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_Gestor {

    private static DAO_Inventario DAOi = new DAO_Inventario();
    private static DAO_Producto DAOp = new DAO_Producto();

    public static String listaproductosJSON() {

        int usuarioID = 1;
        
        List<Inventario> inventarios = DAOi.findInvetarioByGestor(usuarioID);
        
        if(!validarTipos(inventarios)){
            return "Los inventarios en almacen no coinciden con inventario libre y reservado";
        }
        
        int QInvLibre = -1;
        int QInvReservado = -1;
        
        for(Inventario inventario: inventarios){
            int inventarioID = inventario.getInventarioID();
            String tipo = inventario.getTipo();
            System.out.println(inventarioID);
            System.out.println(tipo);
            if(tipo.equals("Libre")){
                System.out.println("a");
                QInvLibre = DAOp.findProductoByInventario(inventarioID).size();
            }else{
                QInvReservado = DAOp.findProductoByInventario(inventarioID).size();
            }
        }
        
        String Resultado = "Cantidad de Inventario Libre: " + String.valueOf(QInvLibre) + "\n"+ "Cantidad de Inventario Reservado: " + String.valueOf(QInvReservado);

        return Resultado;
    }

    public static boolean validarTipos(List<Inventario> inventarios) {
        if (inventarios == null || inventarios.size() != 2) {
            return false;
        }

        String tipo1 = inventarios.get(0).getTipo();
        String tipo2 = inventarios.get(1).getTipo();

        return (("Libre".equals(tipo1) && "Reservado".equals(tipo2))
                || ("Reservado".equals(tipo1) && "Libre".equals(tipo2)));
    }

}
