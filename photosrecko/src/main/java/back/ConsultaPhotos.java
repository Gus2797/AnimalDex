/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author luiss
 */
public class ConsultaPhotos extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConsultaPhotos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConsultaPhotos at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        PrintWriter out = response.getWriter();
         response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
                
                String sqlcond = "";
               String AnName = request.getParameter("nombre");
               
               if(AnName !=null){
               sqlcond +="where nombre_comun = " + "'"+AnName+"'";
               }
       try{
            
            ConnectionBd c = new ConnectionBd();
            Connection cn=c.ConexionBD();
            
            //out.println(cn);
            
            JsonArray array=new JsonArray();
            
            
         String sql;
         sql = "SELECT * FROM fotografia " + sqlcond;
        // out.println(sql);
         PreparedStatement preparedStatement = cn.prepareStatement(sql);
   ResultSet rs = preparedStatement.executeQuery();
            
            
            //out.println(rs);
            
            while(rs.next()){
                JsonObject item=new JsonObject();
                item.addProperty("id", rs.getString(1));
                item.addProperty("nombre_comun", rs.getString(2));
                item.addProperty("especie", rs.getString(3));
                item.addProperty("orden", rs.getString(4));
                item.addProperty("clase", rs.getString(5));
                item.addProperty("familia", rs.getString(6));
                item.addProperty("descripcion", rs.getString(7));
                array.add(item);
                
            }
            out.println(array);
        }
        catch(Exception e){
            out.println(e);
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
