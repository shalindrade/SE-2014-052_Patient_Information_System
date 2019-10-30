package reporting;


import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasindu
 */
public class DataAccess
{
    public Connection getConnection(){
        try {
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Medicle_Centre?autoReconnect=true&useSSL=false","root","12345");
            return (Connection) con;
        } catch (SQLException sQLException) {
            return null;

        }
    }

    public boolean saveEmployee(String eid, String name, String add, String pno){
        String qry = "insert into employee values ('"+eid+"', '"+name+"', '"+add+"', '"+pno+"')";
        Connection con = getConnection();

        if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                con.close();
                return true;

            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean delEmp(String eid)
    {
        String qry = "delete from employee where emp_id = '"+eid+"'";
        Connection con = getConnection();
        if(con != null)
        {
            try {
                 java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                return true;
            }

            catch (SQLException sQLException)
            {
                return false;
            }
        }

        else
            return false;
    }

    public boolean modProj(String EmpId,String ProjNo,String DateAssign,String Role)
    {
        String qry = "insert into assigned values ('"+EmpId+"','"+ProjNo+"','"+DateAssign+"','"+Role+"')";
           Connection con = getConnection();
           if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                con.close();
                return true;

            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean insertProject(String project_no,String budjet,String title,String start_Date,String end_Date){
        double b = Double.parseDouble(budjet);

        String qry = "insert into project values ('"+project_no+"',"+b+",'"+title+"','"+start_Date+"','"+end_Date+"')";
        Connection con = getConnection();
        if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                con.close();
                return true;

            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean deleteProject(String pno){
        String qry = "delete from project where project_no='"+pno+"'";
        Connection con = getConnection();
        if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                return true;
            } catch (SQLException sQLException) {
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean deleteEmpFromproject(String eid, String pno){
        String qry = "delete from assigned where emp_id='"+eid+"' and project_no='"+pno+"'";
        Connection con = getConnection();
        if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                return true;
            } catch (SQLException sQLException) {
                return false;
            }
        }
        else{
            return false;
        }
    }


        public boolean addToProject(String empid, String projno, String dateass, String role){
        String qry = "insert into assigned values('"+empid+"','"+projno+"','"+dateass+"','"+role+"')";
        Connection con = getConnection();
        if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                return true;
            } catch (SQLException sQLException) {
                return false;
            }
        }
        else{
            return false;
        }
    }



         public boolean update(String qry){

            Connection con = getConnection();
            if(con != null){
            try {
                java.sql.Statement st = con.createStatement();
                st.executeUpdate(qry);
                con.close();
                return true;

            } catch (Exception e) {
                return false;
            }
        }
        else{
            return false;
        }

        }



}


