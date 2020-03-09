/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author fields
 */
public class DbConnection {
    private static final String USER ="root";
    private static final String PASSWORD="";
    private static final String DB ="iot";
    private static final String URL = "jdbc:mysql://localhost:3306/"+DB+"?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public static Connection con = null;
    
    public static Connection connect(){
        try{
            
            Connection con = DriverManager.getConnection(URL ,USER, PASSWORD);
            return con;
        }catch(SQLException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos", "ERROR DE CONEXIÓN DB", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
}
