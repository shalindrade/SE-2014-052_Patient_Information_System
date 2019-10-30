/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import reporting.ReportView;

/**
 *
 * @author kanishkamadhuranga
 */
public class Patient_checkup extends javax.swing.JFrame {
    
    /**
     * Creates new form Patient_checkup
     */
    public Patient_checkup() {
        initComponents();
        alertLabel.setVisible(false);
        setExtendedState(MAXIMIZED_BOTH);
        getDetail();
        setComboBoxes();
        loadHistory();
        loadHealthRecord();
        System.out.println(surgeryTxt.getText());
        if(!surgeryTxt.getText().equals("No previous surgery records.")){
            previousSurgeryCheckBox.setSelected(true);
            alertLabel.setVisible(true);
        }
        if(!nonInfectiousTxt.getText().equals("No non-infectious diseases")){
            nonInfectiousCheckBox.setSelected(true);
            alertLabel.setVisible(true);
        }
        suggestionTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                int row=suggestionTable.rowAtPoint(evt.getPoint());
                int col=suggestionTable.columnAtPoint(evt.getPoint());
                
                DefaultTableModel dtm= (DefaultTableModel)prescribeTable.getModel();
                Vector v=new Vector();
                boolean found=false;
                if(dtm.getRowCount()>0){
                    for(int j=0;j<dtm.getRowCount();j++){
                        if(dtm.getValueAt(j, 0).toString().equals(suggestionTable.getValueAt(row, col).toString())){
                           found=true;
                        }
                    }
                }
                
                if(!found){
                    v.add(suggestionTable.getValueAt(row, col));
                    dtm.addRow(v);
                    removeRow(suggestionTable, row);
                }else{
                    removeRow(suggestionTable, row);
                }
            }
        });
        
        medicineComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent event) {
                     if (event.getKeyChar() == KeyEvent.VK_ENTER) {
                        System.out.println("Hello");
                        DefaultTableModel dtm= (DefaultTableModel)suggestionTable.getModel();
                        
                        Vector v=new Vector();
                        if(checkDuplicate(medicineComboBox.getSelectedItem())){
                            v.add(medicineComboBox.getSelectedItem());
                            dtm.addRow(v);
                        }    
                     }
                }
        });
        
        medicalCaseComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
           @Override
           public void keyReleased(KeyEvent evt){
               if(evt.getKeyChar()==KeyEvent.VK_ENTER){
                   DefaultTableModel dtm=(DefaultTableModel)suggestionTable.getModel();
                   
                   String name=(String)medicalCaseComboBox.getSelectedItem();
                   String sql="SELECT caseID FROM Medicle_Centre.MedicalCases WHERE name='"+name+"' ";
                   ResultSet rs=null;
                   String caseID="";
                   try{
                       DB_Connector.createNewConnection();
                       rs=DB_Connector.search(sql);
                       
                       rs.next();
                       caseID=rs.getString(1);
                   }catch(Exception e){
                   
                   }
                   System.out.println(name);
                   sql="SELECT name FROM Medicines, Medicle_Centre.`MedicalCases-Medicines` WHERE caseID like '"+caseID+"' AND Medicines.medicineID like Medicle_Centre.`MedicalCases-Medicines`.medicineID";
                   
                   try{
                       DB_Connector.createNewConnection();
                       rs=DB_Connector.search(sql);
                       
                       while(rs.next()){
                           Vector v=new Vector();
                           String medName=rs.getString(1);
                           System.out.println(medName);
                           v.add(medName);
                           dtm.addRow(v);
                       }
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                           
               }
           }
        });
        
        
        historyTitleTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt){
                DefaultTableModel dtm=(DefaultTableModel) historyPrescribedMedicine.getModel();
                dtm.setRowCount(0);
                prescibedMedDescriptionArea.setText("");
                int row=historyTitleTable.rowAtPoint(evt.getPoint());
                
                //dtm= (DefaultTableModel)historyPrescribedMedicine.getModel();
                
                String selectedTitle=historyTitleTable.getValueAt(row, 0).toString();   
                System.out.println(selectedTitle);
                String refID=selectedTitle.substring(81);
                
                ResultSet rs=null;
                String sql="SELECT description,docID FROM Medicle_Centre.MedicalHistory WHERE historyID='"+refID+"' ";
               
                try{
                    DB_Connector.createNewConnection();
                    rs=DB_Connector.search(sql);
                    rs.next();
                    String diagnosedDocID=rs.getString(2);
                    String prescribedMedArray[]=rs.getString(1).split("~~~");
                    for(int i=0;i<prescribedMedArray.length-1;i++){
                        Vector v=new Vector();
                        v.add(prescribedMedArray[i]);
                        dtm.addRow(v);
                    }
                    sql="SELECT name FROM Users WHERE uid='"+diagnosedDocID+"' ";
                    rs=DB_Connector.search(sql);
                    rs.next();
                    String diagnoedDocName=rs.getString(1);
                    if(!prescribedMedArray[prescribedMedArray.length-1].equals("@@@"))
                        prescibedMedDescriptionArea.setText(prescribedMedArray[prescribedMedArray.length-1].substring(3)+"\n\nDiagnosed by : Dr."+diagnoedDocName);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        });
        
    }
    
    
    
    public void removeRow(JTable t,int row){
        DefaultTableModel dtm=(DefaultTableModel)t.getModel();
        dtm.removeRow(row);
    }
    
    public boolean checkDuplicate(Object o){
        DefaultTableModel dtm= (DefaultTableModel)suggestionTable.getModel();
        
        for(int i=0;i<dtm.getRowCount();i++){
            if(dtm.getValueAt(i, 0).toString().equals(o.toString())){
                return false;
            }            
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        historyTitleTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        historyPrescribedMedicine = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        prescibedMedDescriptionArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        prescribeTable = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        medicalCaseComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        suggestionTable = new javax.swing.JTable();
        medicineComboBox = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        permanentAddressTxt = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        temporaryAddressTxt = new javax.swing.JTextArea();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        personalTelNumberTxt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        immergencyTelNumberTxt = new javax.swing.JTextField();
        bloodTypeTxt = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        allergictxt = new javax.swing.JTextArea();
        previousSurgeryCheckBox = new javax.swing.JCheckBox();
        nonInfectiousCheckBox = new javax.swing.JCheckBox();
        jScrollPane10 = new javax.swing.JScrollPane();
        surgeryTxt = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        nonInfectiousTxt = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        Label3 = new javax.swing.JLabel();
        Label1 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        sidLabel = new javax.swing.JLabel();
        Label4 = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        AgeLabel = new javax.swing.JLabel();
        AllergiesLabel = new javax.swing.JLabel();
        LastVistLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        alertLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 3, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 785));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Best Care");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 190, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image 4.png"))); // NOI18N
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 130, 110));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 0, 255), null));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Prescribed Medicines and Description");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 240, 28));

        historyTitleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Diagnosis History"
            }

        ){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        }

    );
    jScrollPane4.setViewportView(historyTitleTable);

    jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 40, 443, 410));

    historyPrescribedMedicine.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Prescribed Medicines"
        }
    ){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    });
    jScrollPane5.setViewportView(historyPrescribedMedicine);

    jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(586, 40, 480, 266));

    jScrollPane6.setEnabled(false);

    prescibedMedDescriptionArea.setEditable(false);
    prescibedMedDescriptionArea.setColumns(20);
    prescibedMedDescriptionArea.setRows(5);
    jScrollPane6.setViewportView(prescibedMedDescriptionArea);

    jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(586, 318, 480, 130));

    jLabel6.setText("Diagnosis History");
    jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 28));

    jTabbedPane1.addTab("History", jPanel3);

    jPanel4.setLayout(null);

    prescribeTable.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
    prescribeTable.setForeground(new java.awt.Color(0, 204, 0));
    prescribeTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Prescribed Medicine"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }
        public boolean isCellEditable(int row, int column){
            return false;
        }
    });
    jScrollPane2.setViewportView(prescribeTable);

    jPanel4.add(jScrollPane2);
    jScrollPane2.setBounds(833, 17, 340, 370);

    jLabel20.setText("Medical Cases");
    jPanel4.add(jLabel20);
    jLabel20.setBounds(19, 34, 197, 14);

    medicalCaseComboBox.setEditable(true);
    medicalCaseComboBox.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
    medicalCaseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Medical cases" }));
    medicalCaseComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            medicalCaseComboBoxActionPerformed(evt);
        }
    });
    jPanel4.add(medicalCaseComboBox);
    medicalCaseComboBox.setBounds(19, 62, 336, 22);

    descriptionTextArea.setColumns(20);
    descriptionTextArea.setRows(5);
    jScrollPane1.setViewportView(descriptionTextArea);

    jPanel4.add(jScrollPane1);
    jScrollPane1.setBounds(20, 200, 340, 190);

    jLabel21.setText("Description");
    jPanel4.add(jLabel21);
    jLabel21.setBounds(19, 164, 158, 27);

    suggestionTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Suggestions"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.String.class
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int row, int column){
            return false;
        }
    }

    );
    jScrollPane3.setViewportView(suggestionTable);

    jPanel4.add(jScrollPane3);
    jScrollPane3.setBounds(440, 17, 310, 370);

    medicineComboBox.setEditable(true);
    medicineComboBox.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
    medicineComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Medicines" }));
    medicineComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            medicineComboBoxFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            medicineComboBoxFocusLost(evt);
        }
    });
    medicineComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            medicineComboBoxActionPerformed(evt);
        }
    });
    jPanel4.add(medicineComboBox);
    medicineComboBox.setBounds(19, 127, 336, 22);

    jLabel22.setText("Medicine List");
    jPanel4.add(jLabel22);
    jLabel22.setBounds(19, 99, 197, 14);

    jButton2.setText("Print Now");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    jPanel4.add(jButton2);
    jButton2.setBounds(530, 430, 140, 40);

    jButton4.setText("<");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
        }
    });
    jPanel4.add(jButton4);
    jButton4.setBounds(760, 130, 50, 40);

    jButton3.setText(">>");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    jPanel4.add(jButton3);
    jButton3.setBounds(760, 200, 50, 40);

    jTabbedPane1.addTab("Prescribe", jPanel4);

    jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel5.setText("Address");
    jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 6, 112, 27));

    jLabel23.setText("Allergic Conditions");
    jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 271, 199, 27));

    jLabel24.setText("Contact Numbers");
    jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 120, 122, 27));

    jLabel25.setText("Blood Type");
    jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 214, 86, 27));

    jLabel26.setText("Permanent - ");
    jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

    permanentAddressTxt.setEditable(false);
    permanentAddressTxt.setColumns(20);
    permanentAddressTxt.setRows(4);
    jScrollPane7.setViewportView(permanentAddressTxt);

    jPanel5.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 39, 338, -1));

    temporaryAddressTxt.setEditable(false);
    temporaryAddressTxt.setColumns(20);
    temporaryAddressTxt.setRows(4);
    jScrollPane8.setViewportView(temporaryAddressTxt);

    jPanel5.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(736, 39, 338, -1));

    jLabel27.setText("Temperaly - ");
    jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(643, 39, -1, -1));

    jLabel28.setText("Personal - ");
    jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 86, -1));

    personalTelNumberTxt.setEditable(false);
    jPanel5.add(personalTelNumberTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 153, 338, -1));

    jLabel29.setText("In Case of Immergency - ");
    jPanel5.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, -1, -1));

    immergencyTelNumberTxt.setEditable(false);
    jPanel5.add(immergencyTelNumberTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, 210, -1));

    bloodTypeTxt.setEditable(false);
    jPanel5.add(bloodTypeTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 214, 86, -1));

    allergictxt.setEditable(false);
    allergictxt.setColumns(20);
    allergictxt.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
    allergictxt.setRows(5);
    allergictxt.setText("No Allergic Conditions\n");
    jScrollPane9.setViewportView(allergictxt);

    jPanel5.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 271, 321, 175));

    previousSurgeryCheckBox.setText("Preivious Medical Surgeries");
    previousSurgeryCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            previousSurgeryCheckBoxMousePressed(evt);
        }
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            previousSurgeryCheckBoxMouseClicked(evt);
        }
    });
    previousSurgeryCheckBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            previousSurgeryCheckBoxActionPerformed(evt);
        }
    });
    jPanel5.add(previousSurgeryCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 210, -1, -1));

    nonInfectiousCheckBox.setText("Non-infectious Diseases");
    nonInfectiousCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mousePressed(java.awt.event.MouseEvent evt) {
            nonInfectiousCheckBoxMousePressed(evt);
        }
    });
    jPanel5.add(nonInfectiousCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 330, 200, -1));

    surgeryTxt.setEditable(false);
    surgeryTxt.setColumns(20);
    surgeryTxt.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
    surgeryTxt.setRows(5);
    surgeryTxt.setText("No previous surgery records.");
    jScrollPane10.setViewportView(surgeryTxt);

    jPanel5.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, 360, 90));

    nonInfectiousTxt.setEditable(false);
    nonInfectiousTxt.setColumns(20);
    nonInfectiousTxt.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
    nonInfectiousTxt.setRows(5);
    nonInfectiousTxt.setText("No non-infectious diseases");
    jScrollPane11.setViewportView(nonInfectiousTxt);

    jPanel5.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, 360, 90));

    jButton5.setText("Edit");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });
    jPanel5.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 470, -1, -1));

    jButton6.setText("Save");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
        }
    });
    jPanel5.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 459, 100, 40));

    jTabbedPane1.addTab("Health Record", jPanel5);

    jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 1100, 550));

    jLabel7.setText("photo of the patient");
    jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, 140, 120));

    Label3.setText("Allergies");
    jPanel1.add(Label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 100, 20));

    Label1.setText("Name");
    jPanel1.add(Label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 110, 20));

    Label2.setText("Age");
    jPanel1.add(Label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 110, 20));

    jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
    jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 210, 30));

    sidLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
    sidLabel.setText("SE/2014/000");
    jPanel1.add(sidLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 150, 140, 30));

    Label4.setText("Last Visit");
    jPanel1.add(Label4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 80, 20));
    jPanel1.add(NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 280, 20));
    jPanel1.add(AgeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 280, 20));
    jPanel1.add(AllergiesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 280, 20));
    jPanel1.add(LastVistLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 280, 20));

    jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jButton1.setText("Back");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });
    jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 690, 80, 40));

    alertLabel.setBackground(new java.awt.Color(255, 0, 51));
    alertLabel.setForeground(new java.awt.Color(0, 0, 51));
    alertLabel.setText("   ");
    alertLabel.setOpaque(true);
    jPanel1.add(alertLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 30, 30));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Patients().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void medicineComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicineComboBoxActionPerformed

    }//GEN-LAST:event_medicineComboBoxActionPerformed

    
    
    private void medicalCaseComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicalCaseComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medicalCaseComboBoxActionPerformed

    private void previousSurgeryCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousSurgeryCheckBoxMouseClicked
       
    }//GEN-LAST:event_previousSurgeryCheckBoxMouseClicked

    private void previousSurgeryCheckBoxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousSurgeryCheckBoxMousePressed
         previousSurgeryCheckBox.doClick(); 
         previousSurgeryCheckBox.doClick();       
    }//GEN-LAST:event_previousSurgeryCheckBoxMousePressed

    private void previousSurgeryCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousSurgeryCheckBoxActionPerformed
        
    }//GEN-LAST:event_previousSurgeryCheckBoxActionPerformed

    private void nonInfectiousCheckBoxMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nonInfectiousCheckBoxMousePressed
         nonInfectiousCheckBox.doClick(); 
         nonInfectiousCheckBox.doClick();
    }//GEN-LAST:event_nonInfectiousCheckBoxMousePressed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        String descriptionTextArea= this.descriptionTextArea.getText();
        String sql=null;
        
        ResultSet rs=null;
        String sid=Patients.selectedSID;
        sql="UPDATE Students SET LastVisitDate='"+currentDate()+"' WHERE sid='"+sid+"'  ";
        try {
            DB_Connector.createNewConnection();
            int rows=DB_Connector.iud(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        String Description="";
        DefaultTableModel dtm=(DefaultTableModel) prescribeTable.getModel();
        String[] prescribedMedStrings=new String[dtm.getRowCount()];
        if(dtm.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"No Prescribed Medicines...\nPlease prescribed the medicines", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        for(int i=0;i<dtm.getRowCount();i++){
            prescribedMedStrings[i]=dtm.getValueAt(i, 0).toString();
            Description+=dtm.getValueAt(i, 0)+"~~~";
        }
        Description+="@@@"+descriptionTextArea;
        
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:aaa");
        String date_and_time=sdf.format(d);
        String docID=Home.LoggedDocID;
        String docName="Dr."+Home.Username;
        sql="INSERT INTO Medicle_Centre.MedicalHistory(date_time, description, sid, docID) VALUES('"+date_and_time+"', '"+Description+"', '"+sid+"', '"+docID+"')";
        try{
            DB_Connector.createNewConnection();
            DB_Connector.iud(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        sql="SELECT * FROM Medicle_Centre.MedicalHistory";
        rs=null;
        String finalhistoryID="";
        
        try{
            DB_Connector.createNewConnection();
            rs=DB_Connector.search(sql);
            while(rs.next()){finalhistoryID=rs.getString(1);}
            for(int i=0;i<prescribedMedStrings.length;i++){
                sql="INSERT INTO Medicle_Centre.prescribedMedicine(prescribedHistoryID,prescribedMedicineName,description,docName,age) VALUES('"+finalhistoryID+"' , '"+prescribedMedStrings[i]+"' , '"+descriptionTextArea+"','"+docName+"','"+age+"' )";
                DB_Connector.iud(sql);
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
        
        HashMap hm=new HashMap();
        hm.put("precriptionID", finalhistoryID);
//        ReportView r=new ReportView("/Users/kanishkamadhuranga/NetBeansProjects/Java_testing/src/newReport.jasper",hm);
          ReportView r=new ReportView("/Users/kanishkamadhuranga/Desktop/DBMS Project/Medical Centre - University of Kelaniya/Software(Medical_Centre)/src/Main/report1.jasper",hm);  
        r.setVisible(true); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void medicineComboBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_medicineComboBoxFocusGained
        
    }//GEN-LAST:event_medicineComboBoxFocusGained

    private void medicineComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_medicineComboBoxFocusLost
        
    }//GEN-LAST:event_medicineComboBoxFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel dtm=(DefaultTableModel)suggestionTable.getModel();
        DefaultTableModel dtm2=(DefaultTableModel)prescribeTable.getModel();
        
        for(int i=0;i<dtm.getRowCount();i++){
            Vector v=new Vector();
            boolean found=false;
            if(dtm2.getRowCount()>0){
                for(int j=0;j<dtm2.getRowCount();j++){
                    if(dtm2.getValueAt(j, 0).toString().equals(dtm.getValueAt(i, 0))){
                        found=true;
                        break;
                    }
                }
                if(!found){
                   v.add(dtm.getValueAt(i, 0));
                   dtm2.addRow(v); 
                }
            }else{
                v.add(dtm.getValueAt(i, 0));
                dtm2.addRow(v);
            }   
        }
        dtm.setRowCount(0);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int selectedRow=prescribeTable.getSelectedRow();
        DefaultTableModel dtm=(DefaultTableModel) prescribeTable.getModel();
        DefaultTableModel dtm2=(DefaultTableModel) suggestionTable.getModel();
        Vector v=new Vector();
        v.add(dtm.getValueAt(selectedRow, 0));
        dtm2.addRow(v);
        removeRow(prescribeTable, selectedRow);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String input=JOptionPane.showInputDialog(null, "Enter the password", "Enable Editing", JOptionPane.PLAIN_MESSAGE);
        if(Home.password.equals(input)){
           permanentAddressTxt.setEditable(true);
           temporaryAddressTxt.setEditable(true);
           personalTelNumberTxt.setEditable(true);
           immergencyTelNumberTxt.setEditable(true);
           bloodTypeTxt.setEditable(true);
           allergictxt.setEditable(true);
           surgeryTxt.setEditable(true);
           nonInfectiousTxt.setEditable(true);
        }else{
            JOptionPane.showMessageDialog(null, "Incorrect Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try{
            String sql="UPDATE Students SET Address='"+permanentAddressTxt.getText()+"',tempAddress='"+temporaryAddressTxt.getText()+"',personalTelNum='"+personalTelNumberTxt.getText()+"',immergencyTelNumber='"+immergencyTelNumberTxt.getText()+"',BloodGroup='"+bloodTypeTxt.getText()+"',AllergicConditions='"+allergictxt.getText()+"',PriviousSurgery='"+surgeryTxt.getText()+"',NonInfectiousDiseases='"+nonInfectiousTxt.getText()+"'  WHERE sid='"+Patients.selectedSID+"'  ";
            DB_Connector.createNewConnection();
            int rows=DB_Connector.iud(sql);
            if(rows>0){
                JOptionPane.showMessageDialog(null, "Patient detatils updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Unexpected Error Occured.Patient detatils not updated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    
    public void loadHistory(){
        String sql="SELECT historyID,date_time,description,docID FROM Medicle_Centre.MedicalHistory WHERE sid='"+Patients.selectedSID+"' ";
        String historyTitle="defaultTitle";
        ResultSet rs=null;
        try{
            DB_Connector.createNewConnection();
            rs=DB_Connector.search(sql);
            
            while(rs.next()){
                
                historyTitle=rs.getString(2)+"                                                      refID : "+rs.getString(1);
                Vector v=new Vector();
                //String refID= historyTitle.substring(81);
                v.add(historyTitle);
                DefaultTableModel dtm=(DefaultTableModel)historyTitleTable.getModel();
                dtm.addRow(v);
            }
            System.out.println("after the while");
        }catch(Exception e){
            
        }
    }
    static String age;
    public void getDetail(){
        ResultSet rs=null;
        String selectedSID=Patients.selectedSID;
        String sql="SELECT * FROM Students WHERE sid='"+selectedSID+"' ";
        try {
            DB_Connector.createNewConnection();
            rs=DB_Connector.search(sql);
            rs.next();
            sidLabel.setText(rs.getString(1));
            NameLabel.setText(rs.getString(2));
            int dobYear=Integer.parseInt(rs.getString(3).substring(0, 4));
            int thisYear=Integer.parseInt(currentDate().substring(0, 4));
            age=(thisYear-dobYear)+"";
            AgeLabel.setText(age+"");
            if(rs.getString(9).equals("No allergic condition"))
                AllergiesLabel.setText("No allergic condition");
            else{
                AllergiesLabel.setText("Have");
            }
            LastVistLabel.setText(rs.getString(11));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//2016-12-12
    
    public String currentDate(){
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(d);            
        return today;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Patient_checkup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Patient_checkup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Patient_checkup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Patient_checkup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Patient_checkup().setVisible(true);
            }
        });
    }
    
    public  void setComboBoxes(){
        ResultSet rs=null;
        String sql="SELECT name FROM Medicines";
        try {
            DB_Connector.createNewConnection();
            rs=DB_Connector.search(sql);
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                medicineComboBox.addItem(rs.getString(1));
            }
            
            sql="SELECT name FROM MedicalCases";
            rs=DB_Connector.search(sql);
            
            while(rs.next()){
                System.out.println(rs.getString(1));
                medicalCaseComboBox.addItem(rs.getString(1));
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
        }
        
        new MyCombo().setSearchableCombo(medicineComboBox, true, "no result found");
        new MyCombo().setSearchableCombo(medicalCaseComboBox, true, "no result found");
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AgeLabel;
    private javax.swing.JLabel AllergiesLabel;
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    private javax.swing.JLabel Label3;
    private javax.swing.JLabel Label4;
    private javax.swing.JLabel LastVistLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel alertLabel;
    private javax.swing.JTextArea allergictxt;
    private javax.swing.JTextField bloodTypeTxt;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JTable historyPrescribedMedicine;
    private javax.swing.JTable historyTitleTable;
    private javax.swing.JTextField immergencyTelNumberTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> medicalCaseComboBox;
    private javax.swing.JComboBox<String> medicineComboBox;
    private javax.swing.JCheckBox nonInfectiousCheckBox;
    private javax.swing.JTextArea nonInfectiousTxt;
    private javax.swing.JTextArea permanentAddressTxt;
    private javax.swing.JTextField personalTelNumberTxt;
    private javax.swing.JTextArea prescibedMedDescriptionArea;
    private javax.swing.JTable prescribeTable;
    private javax.swing.JCheckBox previousSurgeryCheckBox;
    private javax.swing.JLabel sidLabel;
    private javax.swing.JTable suggestionTable;
    private javax.swing.JTextArea surgeryTxt;
    private javax.swing.JTextArea temporaryAddressTxt;
    // End of variables declaration//GEN-END:variables

    private void loadHealthRecord() {
        String sql="SELECT Address,PriviousSurgery,NonInfectiousDiseases,EyeCondition,AllergicConditions,BloodGroup,tempAddress,personalTelNum,immergencyTelNumber FROM Medicle_Centre.Students WHERE sid='"+Patients.selectedSID+"' ";
        ResultSet rs=null;
        try{
            DB_Connector.createNewConnection();
            rs=DB_Connector.search(sql);
            rs.next();
            permanentAddressTxt.setText(rs.getString(1));
            temporaryAddressTxt.setText(rs.getString(7));
            personalTelNumberTxt.setText(rs.getString(8));
            immergencyTelNumberTxt.setText(rs.getString(9));
            bloodTypeTxt.setText(rs.getString(6));
            surgeryTxt.setText(rs.getString(2));
            nonInfectiousTxt.setText(rs.getString(3));
            allergictxt.setText(rs.getString(5));
        }catch(Exception e){
            
        }
        
    }
}
