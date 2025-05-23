/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import Proveedores.Servicio_AgregarProv;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Kevin Zambrano
 */
public class ServletAgregarProveedor extends HttpServlet {

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
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ServletAgregarProveedor</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ServletAgregarProveedor at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
        String nombre = request.getParameter("nombre");
        String Estado = request.getParameter("Status");
        String telefono = request.getParameter("telefono");
        String descripcion = request.getParameter("descripcion");
        String oferta = request.getParameter("oferta");

        try {
//            int stock = Integer.parseInt(stockStr);
//            var precio = Double.parseDouble(precioStr);

            // Aquí puedes guardar el producto, por ejemplo en una base de datos
            int Status = Servicio_AgregarProv.CrearProveedor(nombre, Estado, telefono, descripcion, oferta);

            // Si quieres enviar una respuesta al cliente (por ejemplo, AJAX)
            String R = "";
            switch(Status){
                case 0: 
                    R = "Proveedor agregado correctamente";
                    break;
                case 1: 
                    R = "Estado ingresado invalido";
                    break;
                default:
                    R = "Error en la creacion del proveedor";
                    break;
            }
            
            response.getWriter().write(R);
            response.setContentType("text/plain");

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
