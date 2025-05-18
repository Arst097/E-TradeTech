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
public class ServletInventarioMostrar extends HttpServlet {

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
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        StringBuilder json = new StringBuilder();
//        json.append("[");
//
//        json.append("{");
//        json.append("\"id\":\"001\",");
//        json.append("\"nombre\":\"Laptop HP Pavilion 15\",");
//        json.append("\"categoria\":\"Computadoras\",");
//        json.append("\"stock\":15,");
//        json.append("\"precio\":3000000");
//        json.append("},");
//
//        json.append("{");
//        json.append("\"id\":\"002\",");
//        json.append("\"nombre\":\"iPhone 13\",");
//        json.append("\"categoria\":\"Teléfonos\",");
//        json.append("\"stock\":10,");
//        json.append("\"precio\":4500000");
//        json.append("}");
//
//        json.append("]");
//
//        response.getWriter().write(json.toString());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String Correo = "carlos@empresa.com";
        String ContrasenaPlana = "gestor123";
        String ContrasenaSHA256 = Servicio_Seguridad.encryptSHA256(ContrasenaPlana);
        String Token = "";
        try {
            Token = Servicio_Usuario.login(Correo, ContrasenaSHA256, false);
        } catch (SQLException ex) {
            Logger.getLogger(ServletInventarioMostrar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String json;
        json = Servicio_Inventario.listaproductosJSON(Token);
        //json = "[{\"id\":\"1\",\"nombre\":\"Apple Watch Series 9\",\"categoria\":\"Otros electronicos\",\"stock\":1,\"precio\":1999.99},{\"id\":\"2\",\"nombre\":\"Dell XPS 13\",\"categoria\":\"Computadoras\",\"stock\":1,\"precio\":4299.0},{\"id\":\"3\",\"nombre\":\"HP Spectre x360\",\"categoria\":\"Computadoras\",\"stock\":1,\"precio\":4599.5},{\"id\":\"4\",\"nombre\":\"iPad Pro 12.9\",\"categoria\":\"Tablets\",\"stock\":1,\"precio\":3299.0},{\"id\":\"5\",\"nombre\":\"iPhone 14 Pro\",\"categoria\":\"Telefonos\",\"stock\":1,\"precio\":3999.99},{\"id\":\"6\",\"nombre\":\"Lenovo ThinkPad X1\",\"categoria\":\"Computadoras\",\"stock\":1,\"precio\":4899.99},{\"id\":\"7\",\"nombre\":\"Nintendo Switch OLED\",\"categoria\":\"Otros electronicos\",\"stock\":1,\"precio\":2499.0},{\"id\":\"8\",\"nombre\":\"Samsung Galaxy S23\",\"categoria\":\"Telefonos\",\"stock\":1,\"precio\":3699.99},{\"id\":\"9\",\"nombre\":\"Samsung Galaxy Tab S8\",\"categoria\":\"Tablets\",\"stock\":1,\"precio\":2999.5},{\"id\":\"10\",\"nombre\":\"Xbox Series X\",\"categoria\":\"Otros electronicos\",\"stock\":1,\"precio\":2999.0}]";

        response.getWriter().write(json);
        
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
