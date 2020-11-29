/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luiss
 */
public class ConnectionBd {
    Connection cn;    
    String cadena;
    
    public Connection ConexionBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                 String dbName = "animalkarma";
      String userName = "admin";
      String password = "Temshad-18";
      String hostname = "animalkarma.csb1pswpmcbs.us-east-1.rds.amazonaws.com";
      String port = "3306";
      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password + "&useSSL=false";
           cadena = jdbcUrl;
      // cn=(Connection) DriverManager.getConnection("jdbc:mysql://animalkarma.csb1pswpmcbs.us-east-1.rds.amazonaws.com:3306/animalkarma","admin","Temshad-18");
             cn =   DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                cadena += ex;
            }
                    } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            cadena+= "error 2" + ex;
            
        }
    return cn; 
    }

    
    
    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    }
    
    public Connection getConexion() {
        return cn;
    }    
    
    public void setConexion(Connection conexion) {
        this.cn = conexion;
    }    

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
    
   
    
    
}
