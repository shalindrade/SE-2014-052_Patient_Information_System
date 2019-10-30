/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author kanishkamadhuranga
 */
public class AddUserThread extends Thread{
    int rows;
    
    public void run(){
        System.out.println("add user thread started!!!");
        Add_user o=new Add_user();
        try {
            System.out.println("start");
            
            DB_Connector.createNewConnection();
            String name = o.getName(), nic_num =o.getNic() , e_mail =o.getEmail() , position =o.getPosition(),slmcRegNum=o.getSLMC() ;
            String sid = "";
            if (nic_num.length()!=10) {
                System.out.println(nic_num.length());
                JOptionPane.showMessageDialog(null, "Incorrect NIC Number\nPlease enter again"," Error!!!" ,JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ResultSet rs = null;
            if (position.equals("Doctor")) {
                rs = DB_Connector.search("SELECT * FROM Users WHERE position='Doctor'  ");
            } else {
                rs = DB_Connector.search("SELECT * FROM Users WHERE position='Admin'  ");
            }
            boolean match_found = false;
            while (rs.next()) {
                if (rs.getString(1).equals(name)) {
                    JOptionPane.showMessageDialog(null, "Sorry! the user name you entered already here. Please enter another one");
                    match_found = true;
                    break;
                }
            }
            if (match_found) {
                return;
            } else {
                sid = name;
            }
            System.out.println("before the random generator");
            Random randomNums = new Random();
            int rand = (int) (randomNums.nextDouble() * 10000);
            int password = rand;
            System.out.println("before the excution of the query");
            String sql = " INSERT INTO Users(uid,name,email,position,password,nic,SLMC_RegNo) VALUES('" + sid + "' , '" + name + "' , '" + e_mail + "' , '" + position + "' , '" + password + "' , '" + nic_num + "' , '"+slmcRegNum+"' )  ";
            rows = DB_Connector.iud(sql);
            
            //progressbar
            
            System.out.println("just before");
            
            String a, b, x, y;
            a = "wijesinghekamal7@gmail.com";
            try {
                String[] to = {e_mail}; // to - email address.
                b = "openhereiam7";//password
                x = "WELCOME!    ----Medical Centre - University of Kelaniya----";//subject
                y = "Welcome! "+position+" "+name+". Here is your User Name - "+name+" and Password - "+password+" \n(please note this is an auto generated password.you are advised to change it on your first log.)";
                E_mail.sendFromGMail(a, b, to, x, y);
            } catch (Exception e) {
                System.out.println("exception has came...stop mailing");
                e.printStackTrace();
            }
            System.out.println("just now sent");
            

        } catch (Exception ex) {
            System.out.println("Exception from the last catch{}");
            ex.printStackTrace();
        }
    }
}
