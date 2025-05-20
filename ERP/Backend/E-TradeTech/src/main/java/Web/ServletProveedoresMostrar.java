/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import Proveedores.Servicio_visualizarProv;
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
public class ServletProveedoresMostrar extends HttpServlet {

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
//            out.println("<title>Servlet ServletProveedoresMostrar</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ServletProveedoresMostrar at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

//            String json = "[" +
//                "{\"id\":\"001\",\"nombre\":\"TecnoGlobal S.A.S\",\"telefono\":\"3124568497\",\"Descripcion\":Proveedor principal de hardware empresarial,\"Estado\":Activo,\"Oferta\":Infraestructura}" +
//                "{\"id\":\"002\",\"nombre\":\"test INC\",\"telefono\":\"312420691\",\"Descripcion\":Prueba servlet importante,\"Estado\":Inactivo,\"Oferta\":Infraestructura}" +
////                "{\"id\":\"002\",\"nombre\":\"iPhone 13\",\"categoria\":\"Teléfonos\",\"stock\":10,\"precio\":4500000}" +
//            "]";

            String json = "[" +
                "{\"id\":\"001\",\"nombre\":\"TecnoGlobal S.A.S\",\"telefono\":\"3124568497\",\"Descripcion\":\"Proveedor principal de hardware empresarial\",\"Estado\":\"Activo\",\"Oferta\":\"Infraestructura\"}," +
                "{\"id\":\"002\",\"nombre\":\"test INC\",\"telefono\":\"312420691\",\"Descripcion\":\"Prueba servlet importante\",\"Estado\":\"Inactivo\",\"Oferta\":\"Infraestructura\"}" +
            "]";
            
            json = Servicio_visualizarProv.listaproveedoresJSON_Directo(5);
            
            json = "[{\"id\":\"1\",\"nombre\":\"TecnoParts\",\"telefono\":\"555-4001\",\"Descripcion\":\"Proveedor de componentes premium\",\"Estado\":\"null\",\"Oferta\":\"Tarjeta Gráfica RTX 4090\"},{\"id\":\"2\",\"nombre\":\"ElectroHard\",\"telefono\":\"555-4002\",\"Descripcion\":\"Distribuidor de hardware\",\"Estado\":\"null\",\"Oferta\":\"Placa Base Z790\"},{\"id\":\"3\",\"nombre\":\"ChipMaster\",\"telefono\":\"555-4003\",\"Descripcion\":\"Especialistas en procesadores\",\"Estado\":\"null\",\"Oferta\":\"Procesador Ryzen 9 7950X\"},{\"id\":\"4\",\"nombre\":\"StorageTech\",\"telefono\":\"555-4004\",\"Descripcion\":\"Soluciones de almacenamiento\",\"Estado\":\"null\",\"Oferta\":\"Disco SSD 2TB NVMe\"},{\"id\":\"5\",\"nombre\":\"Suministros PC\",\"telefono\":\"555-4005\",\"Descripcion\":\"Proveedor general de TI\",\"Estado\":\"null\",\"Oferta\":\"Monitor 4K 32\"}]";

            response.getWriter().write(json);
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
