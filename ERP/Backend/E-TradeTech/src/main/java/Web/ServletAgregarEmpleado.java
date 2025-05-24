/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import RRHH.Servicio_AgregarEmpleados;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Zambrano
 */
public class ServletAgregarEmpleado extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ServletAgregarEmpleado</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ServletAgregarEmpleado at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            String nombre = request.getParameter("nombre");
            String departamento = request.getParameter("departamento");
            System.out.println("Departamento Obtenido: "+departamento);
            String salario = request.getParameter("salario");
            String fechaIngreso  = request.getParameter("fechaIngreso");
//            String valorUnitario  = request.getParameter("valorUnitario");
//            String total  = request.getParameter("total");

            try {
    //            int stock = Integer.parseInt(stockStr);
    //            var precio = Double.parseDouble(precioStr);

                // Aquí puedes guardar el producto, por ejemplo en una base de datos
                int status = Servicio_AgregarEmpleados.crear_empleado(nombre, departamento, Integer.parseInt(salario), fechaIngreso, "Termino Fijo");

                System.out.println(status);
                // Si quieres enviar una respuesta al cliente (por ejemplo, AJAX)
                String output = "Empleado agregado correctamente";
                response.setContentType("text/plain");
                response.getWriter().write(output);

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Stock o precio inválidos.");
            }
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
