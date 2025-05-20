/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores;

import Proveedores.DAOs.DAO_UsuarioCompras;
import Proveedores.Modelos.UsuarioCompras;

/**
 *
 * @author HP PORTATIL
 */
public class Servicio_UsuariosModProv {

    private static final DAO_UsuarioCompras DAOuc = new DAO_UsuarioCompras();
    
    static boolean UsuarioIsUserCompras(int UsuarioID) {
        try{
            UsuarioCompras usuarioCompras = DAOuc.findUsuarioComprasByUsuarioID(UsuarioID);
            return !( usuarioCompras == null );
        }catch(Exception e){
            return false;
        }        
    }
    
}
