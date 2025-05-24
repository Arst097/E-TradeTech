package RRHH;

import RRHH.DAOs.DAO_Empleado;
import RRHH.Modelos.Empleado;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servicio_AgregarEmpleados {
    
    //Funciones Principales de logica
    
    /*
    Ingresa todos los datos que se piden en la pantalla y retorna un codigo de status de la ejecucion
    
    Retorna 0 cuando todo se ejecuto con exito
    Retorna 1 si el departamento ingresado No es valido
    Retorna 2 si el contrato ingresado No es valido
    Retorna -1 si hubo algun error en la ejecucion, ademas imprimira en consola el error
    */
    public static int crear_empleado(String nombre, String departamento, Integer salario, Date fecha_ingreso, String contrato){
        
        if(!validar_departamento(departamento)){
            return 1;
        }
        
        if(!validar_contrato(contrato)){
            return 2;
        }
        
        try{
            return creacion_de_empleado(nombre, departamento, salario, fecha_ingreso, contrato);
        }catch(Exception e){
            System.out.println("Error en la creacion: "+e);
            return -1;
        }
        
    }
    /*
    Es como la logica en detalle de como se crea el empleado
    se crea un modelo empleado, y a partir de una funcion del objeto se establecen los datos
    luego a partir de esa funcion se insertan los datos
    */
    private static int creacion_de_empleado(String nombre, String departamento, Integer salario, Date fecha_ingreso, String contrato){
        int status = 0;
        
        try{
            Empleado empleado = new Empleado();
            empleado.generar_nuevo_empleado(nombre, departamento, salario, fecha_ingreso, contrato);
            insercion_empleado(empleado);
        }catch(Exception e){
            System.out.println("Error en creacion_de_datos: "+e);
            status = -1;
        }
        
        return status;
    }
    
    /*
    Valida si el departamento ingresado hace parte de la lista espesificada
    Retorna true si si lo hace, y false si no. 
    Equivalente a validar si es valido o no el imput es valido o no
    */
    private static boolean validar_departamento(String departamento){
        String[] departamentos_validos = {
            "Administracion",
            "Ventas",
            "Finanzas",
            "Inventario",
            "Compras",
            "Otros"
        };
        System.out.println(departamento);
        
        return Arrays.asList(departamentos_validos).contains(departamento);
    }
    
    /*
    Valida si el contrato ingresado hace parte de la lista espesificada
    Retorna true si si lo hace, y false si no. 
    Equivalente a validar si es valido o no el imput es valido o no
    */
    private static boolean validar_contrato(String departamento){
        String[] departamentos_validos = {
            "Termino fijo",
            "Termino indefinido",
            "Por obra/labor",
            "Contrato de aprendizaje"
        };
        
        return Arrays.asList(departamentos_validos).contains(departamento);
    }
    
    //Acesos a otras Clases
    
    private static final DAO_Empleado DAOe = new DAO_Empleado();
    
    private static void insercion_empleado(Empleado empleado){
        DAOe.create(empleado);
    }

//Funciones Auxiliares Para Accesos variados    
    
    /*
    Hace lo mismo que la funcion principal de mismo nombre pero tiene un return de status mas
    Retorna 4 si hubo un error en el parceo de fecha
    */
    public static int crear_empleado(String nombre, String departamento, Integer salario, String fecha_ingreso_str, String contrato){
        try {
            Date fecha_ingreso = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_ingreso_str);
            return crear_empleado(nombre, departamento, salario, fecha_ingreso, contrato);
        } catch (ParseException ex) {
            System.out.println("Error en el parceo de fecha: "+ex);
            return 4;
        }
    }

}
