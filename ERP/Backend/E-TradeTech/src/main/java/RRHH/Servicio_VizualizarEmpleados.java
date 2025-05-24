package RRHH;

import RRHH.DAOs.DAO_Empleado;
import RRHH.Modelos.Empleado;
import Seguridad.Servicio_Seguridad;
import java.util.Date;
import java.util.List;


public class Servicio_VizualizarEmpleados {
    
//Funciones Principales de logica
    
    /*
    Recibe un id de un empleado
    Juzga si ese empleado tiene acceso a ver la informacion
    Si el empleado tiene acceso, devuelve un JSON con los datos
    La estructura del JSON es hecha en el metodo EmpleadosToJSON()
    */
    private static String listaEmpleadosJSON(int EmpleadoID) {
        boolean Aceeso_Valido = EmpleadoIsAutorizado(EmpleadoID);
        
        if(Aceeso_Valido){
            try{
                List<Empleado> Todos_los_Empleados = getEmpleados(EmpleadoID);
                return EmpleadosToJSON(Todos_los_Empleados);
            }catch(Exception e){
                return "Error al obtener los datos";
            }
        }else{
            return "El usuario no es valido";
        }
    }

    private static String EmpleadosToJSON(List<Empleado> Empleados) {
        int i = 0;
        int size = Empleados.size();
        String json = "[";
        
        for (Empleado empleado : Empleados) {
            i++;
            
            Integer EmpleadoID = empleado.getEmpleadoid();
            String Nombre = empleado.getNombre();
            String Departamento = empleado.getDepartamento();
            Integer Salario = empleado.getSalario();
            Date FechaIngreso = empleado.getFechaIngreso();
            String Contrato = empleado.getContrato();

            json = json
                    + "{\"id\":"
                    + "\"" + EmpleadoID + "\""
                    + ",\"nombre\":"
                    + "\"" + Nombre + "\""
                    + ",\"Departamento\":"
                    + "\"" + Departamento + "\""
                    + ",\"Salario\":"
                    + "\"" + Salario + "\""
                    + ",\"FechaIN\":"
                    + "\"" + FechaIngreso + "\""
                    + ",\"contrato\":"
                    + "\"" + Contrato + "\""
                    + "}";
            if (i < size) {
                json = json + ",";
            }
        }
        
        return json + "]";
    }
    
//Acesos a otras Clases
    
    private static final DAO_Empleado DAOe = new DAO_Empleado();
    
    /*
    en teoria este metodo busca de alguna manera saber si 
    el id del empleado esta en la BD como un rol autorizado.
    Podria remplazarlo para el rol autorizado sea el gestor
    pero por ahora todos estan autorizados
    */
    private static boolean EmpleadoIsAutorizado(int EmpleadoID) {
        return true;
    }

    /*
    Esto llama una funcion del DAO_Empleado
    Y se supone que la funcion que llama 
    tendria un criterio para escojer cuales empleados muestra
    Pero en este caso se llama una que muestra todos los empleados
    */
    private static List<Empleado> getEmpleados(int EmpleadoID) {
        return DAOe.findEmpleados();
    }
    
//Funciones Auxiliares Para Accesos variados

    public static String listaEmpleadosJSON(String Token){
        int EmpleadoID = Servicio_Seguridad.getUserIdFromJwtToken(Token);
        return listaEmpleadosJSON(EmpleadoID);
    }
    
    public static String listaEmpleadosJSON_Directo(int EmpleadoID){
        return listaEmpleadosJSON(EmpleadoID);
    }
}
