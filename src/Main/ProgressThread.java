/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author kanishkamadhuranga
 */
public class ProgressThread extends Thread implements ActionListener{
    int i;
    Timer t;
    JProgressBar loadingBar=new Add_user().getLoadingBar();
    public void actionPerformed(ActionEvent e){
        int rows=1;
        System.out.println("inside the progress thread 1");
        if(i==20){
            System.out.println("inside the progress thread 2");
            if (rows != 0) {
                JOptionPane.showMessageDialog(null, "New User has been sucessfully Added!!!"," Done!!!" ,JOptionPane.INFORMATION_MESSAGE);
                t.stop();
                loadingBar.setValue(0);
            } else {
                JOptionPane.showMessageDialog(null, "An Error has Occured"," Error!!!" ,JOptionPane.ERROR_MESSAGE);
                loadingBar.setValue(0);
            }
        }
        System.out.println("inside the progress thread 3");
        i++;
        loadingBar.setValue(i);
    }
    public void run(){
        System.out.println("progress thread started!!!");
        t= new Timer(1000, this);
        t.start();
        loadingBar.setMinimum(0);
        loadingBar.setMaximum(20);
    }
}
