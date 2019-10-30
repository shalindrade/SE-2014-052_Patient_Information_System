/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author kanishkamadhuranga
 */
public class RecoveryEmailThread extends Thread {
    public void run(){
        
        System.out.println("this is useranme"+LoginFrame.UName);
        //lf.getLebel().setBackground(Color.red);
        String a, b, x, y;
        a = "wijesinghekamal7@gmail.com";
        String sql = "SELECT * FROM Users Where uid='" + LoginFrame.UName + "'  ";
        ResultSet rs=null;
        try {
            DB_Connector.createNewConnection();
             rs = DB_Connector.search(sql);
             rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        Random randomNums = new Random();
        int rand = (int) (randomNums.nextDouble() * 10000);
        int password = rand;
        
        sql="UPDATE Users SET password='"+password+"' WHERE uid='"+LoginFrame.UName+"'  ";
        try {
            DB_Connector.createNewConnection();
            int row=DB_Connector.iud(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try{
            String[] to = {rs.getString(3)}; // to - email address.
            b = "openhereiam7";//password
            x = "Medical Centre - University of Kelaniya Password Reset";//subject
            y = "Welcome! Your Password was sucessfully recovered. This is your new Password "+password+" \nThis is an auto generated password.You are adviced to change it after login.\n Thank You.";
            E_mail.sendFromGMail(a, b, to, x, y);
            
        }catch(Exception e){
            e.printStackTrace();
        }    
        //lf.getLebel().setBackground(Color.GREEN);
    }
}
