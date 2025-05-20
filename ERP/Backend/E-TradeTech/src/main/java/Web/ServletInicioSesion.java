/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Web;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Uso_Comun.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author Kevin Zambrano
 */
public class ServletInicioSesion extends HttpServlet {
    
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
            throws ServletException, IOException, SQLException {
//        Servicio_Usuario.contrasena= request.getParameter("ContraseñaSin");
//        String dios=Servicio_Usuario.encryptSHA256(Servicio_Usuario.contrasena);
        System.out.println("Iniciando login en servlet");
        String email = request.getParameter("Email");
        String contraseña = request.getParameter("ContrasenaSin");
        System.out.println("Encriptando contraseña");
        String contraseña_encriptada = Servicio_Usuario.encryptSHA256(contraseña);
        System.out.println("Logeando");
        String token = Servicio_Usuario.login(email, contraseña_encriptada,false);

        
        if(Servicio_Usuario.TokenValido(token)== true){
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ServletInicioSesion</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet ServletInicioSesion at " + request.getContextPath()+ "; Contraseña Encriptada: "+ contraseña_encriptada + "; Token:"+ token + "email"+email+"contrasena"+ contraseña+ "</h1>");
                out.println("<a href=\"Modulo Inventario/Html Inventario/inventario.html\"></a>");
                out.println("</body>");
                out.println("</html>");
            }
        }else{
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ServletInicioSesion</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>No se genero token pues el usuario o la contraseña son incorrectas </h1>");
                out.println("<h1>Servlet ServletInicioSesion at " + request.getContextPath()+ "; Contraseña Encriptada: "+ contraseña_encriptada + "email"+email+"contrasena"+ contraseña+ "</h1>");
                out.println("</body>");
                out.println("</html>");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ServletInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
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
