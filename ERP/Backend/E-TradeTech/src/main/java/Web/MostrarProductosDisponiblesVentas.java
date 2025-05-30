/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import Ventas.Servicio_HacerVenta;
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
public class MostrarProductosDisponiblesVentas extends HttpServlet {

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
//            out.println("<title>Servlet MostrarProductosDisponiblesVentas</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet MostrarProductosDisponiblesVentas at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
         response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Simular datos de productos y precios
        String[] productos = {"Test1", "Test2", "Test3"};
        double[] precios = {1200.50, 800.00, 450.75};

        // Construir JSON manualmente
        StringBuilder json = new StringBuilder();
        json.append("{");

        // Agregar productos
        json.append("\"Producto\": [");
        for (int i = 0; i < productos.length; i++) {
            json.append("\"").append(productos[i]).append("\"");
            if (i < productos.length - 1) {
                json.append(",");
            }
        }
        json.append("],");

        // Agregar precios
        json.append("\"Precios\": [");
        for (int i = 0; i < precios.length; i++) {
            json.append(precios[i]);
            if (i < precios.length - 1) {
                json.append(",");
            }
        }
        json.append("]");

        json.append("}");

        String jsonstr = Servicio_HacerVenta.Productos_Disponibles();
        // Enviar respuesta
        PrintWriter out = response.getWriter();
        out.write(jsonstr);
        out.flush();
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
