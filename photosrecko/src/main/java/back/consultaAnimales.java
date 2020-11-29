/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author luiss
 */
public class consultaAnimales extends HttpServlet {

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
       /* try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
           /* out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet consultaAnimales</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet consultaAnimales at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
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
        
        
       /* try {
            Class.forName("com.mysql.jdbc.Driver");
            out.println("driver guardado");
            try {
                 String dbName = "animalkarma";
      String userName = "admin";
      String password = "Temshad-18";
      String hostname = "animalkarma.csb1pswpmcbs.us-east-1.rds.amazonaws.com";
      String port = "3306";
      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + "&useSSL=false";
           //cadena = jdbcUrl;
       //cn=(Connection) DriverManager.getConnection("jdbc:mysql://animalkarma.csb1pswpmcbs.us-east-1.rds.amazonaws.com:3306/animalkarma","admin","Temshad-18");
      
            Connection con =  DriverManager.getConnection(jdbcUrl);
           //Connection cn=(Connection) DriverManager.getConnection("jdbc:mysql://animalkarma.csb1pswpmcbs.us-east-1.rds.amazonaws.com:3306/animalkarma","admin","Temshad-18",);
            
           out.println("connectado correctamente");
           out.println(con);
            } catch (SQLException ex) {
                //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
               out.println("error connection " + ex);
            }
                    } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            out.println("classnot found" + ex);
            
        }/*
       
        
        try{
            ConnectionBd c = new ConnectionBd();
            Connection con = c.ConexionBD();
            
            out.println(con);
        }
        catch(Exception e){
            out.println(e);
        }*/
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
         sql = "SELECT * FROM animales " + sqlcond;
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
        
         PrintWriter out = response.getWriter();
        
        try{
            ConnectionBd c = new ConnectionBd();
            
            
            out.println(c.ConexionBD());
        }
        
        catch(Exception e){
            out.println(e.toString());
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
