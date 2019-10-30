/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author kanishkamadhuranga
 */
public class MyCombo {
    private ArrayList<String> ar;
    private  JTextField txt;
    public MyCombo(){
        
    }

    public void setSearchableCombo(final JComboBox cmb, boolean mustSort, final String noReultsText) {
        ar = new ArrayList<String>();
        final int itemCount = cmb.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            boolean exists = false;
            for (int j = 0; j < ar.size(); j++) {
                if (ar.get(j).equalsIgnoreCase(String.valueOf(cmb.getItemAt(i)))) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                ar.add(String.valueOf(cmb.getItemAt(i)));
            }
        }
        if (mustSort) {
            Collections.sort(ar);
        }
        cmb.setEditable(true);
        txt = (JTextField) cmb.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (!(key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP)) {
                    String text = txt.getText();
                    int caret = txt.getCaretPosition();
                    cmb.hidePopup();
                    cmb.removeAllItems();
                    for (int i = 0; i < ar.size(); i++) {
                        if (ar.get(i).toUpperCase().startsWith(text.substring(0, caret).toUpperCase())) {
                            cmb.addItem(ar.get(i));
                        }
                    }
                    cmb.showPopup();
                    if (cmb.getItemCount() == 0) {
                        cmb.addItem(noReultsText);
                    }
                    txt.setText(text);
                    txt.setCaretPosition(caret);
                } else if (key == KeyEvent.VK_ESCAPE) {
                    cmb.setSelectedItem(txt.getText());
                    cmb.hidePopup();
                } else if (key == KeyEvent.VK_ENTER && cmb.getSelectedIndex()==-1) {
                    if(cmb.getItemCount()==1 && !cmb.getItemAt(0).equals(noReultsText)){
                        cmb.setSelectedIndex(0) ;
                    }else if(cmb.getItemCount()>1){
                        cmb.setSelectedIndex(0);
                    }
                }
            }
        });
    }
}
