/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import Inventario.Servicio_Inventario;
import Seguridad.Servicio_Seguridad;
import Uso_Comun.Servicio_Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Zambrano
 */
public class ServletAgregarProductos extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String categoria = request.getParameter("categoria");
        String stockStr = request.getParameter("stock");
        String precioStr = request.getParameter("precio");
        
        String Correo = "hernesto.perez@example.com";
        String ContrasenaPlana = "password123";
        String ContrasenaSHA256 = Servicio_Seguridad.encryptSHA256(ContrasenaPlana);
        String Token = "";
        try {
            Token = Servicio_Usuario.login(Correo, ContrasenaSHA256, false);
        } catch (SQLException ex) {
            Logger.getLogger(ServletInventarioMostrar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            int stock = Integer.parseInt(stockStr);
            var precio = Double.parseDouble(precioStr);

            // Aquí puedes guardar el producto, por ejemplo en una base de datos
            System.out.println("Producto recibido:");
            System.out.println("Nombre: " + nombre);
            System.out.println("Categoría: " + categoria);
            System.out.println("Stock: " + stock);
            System.out.println("Precio: " + precio);
            
            
            boolean completado = false;
            try {
                System.out.println("Se hace el try en servlet para ejecutar CrearMontoProductos");
                completado = Servicio_Inventario.CrearMontoProductos(Token, nombre, categoria, stockStr, precioStr);
            } catch (Exception ex) {
                Logger.getLogger(ServletAgregarProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Si quieres enviar una respuesta al cliente (por ejemplo, AJAX)
            String respuesta = "Error en el cambio";
            if(completado){
                respuesta = "Cambio Exitoso";
            }
            
            response.setContentType("text/plain");
            response.getWriter().write(respuesta);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Stock o precio inválidos.");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
