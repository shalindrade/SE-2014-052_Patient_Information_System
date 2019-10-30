package Main;
import java.sql.*;
/**
 *
 * @author kanishkamadhuranga
 */
public class DB_Connector {
    public static Connection c;
    public static void createNewConnection()throws Exception {
        //load and establish the connection.
        Class.forName("com.mysql.jdbc.Driver");
        //Create the conncetion with the database.
        String url="jdbc:mysql://localhost:3308/medicalcases?autoReconnect=true&useSSL=false",uname="root",pass="";
        c=DriverManager.getConnection(url, uname, pass);
        //Create the Statement to pass the Queries.
        Statement st=c.createStatement();   
    }
    
    public static ResultSet search(String sql)throws Exception{
        if (c==null) {
            createNewConnection();
        }
        return c.createStatement().executeQuery(sql);
    }
    public static int iud(String sql)throws Exception{
        if (c==null) {
            createNewConnection();
        }
        return c.createStatement().executeUpdate(sql);
    }
    
}
