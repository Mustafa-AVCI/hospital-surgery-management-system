/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Uİ;

import com.sun.jdi.connect.spi.Connection;
import dao.StaffDAO;
import dao.SurgeryDAO;
import dao.SurgeryStaffDAO;
import java.beans.Statement;
import model.SurgeryStaff;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Staff;
import model.Surgery;
import util.DBConnection;
import util.SessionManager;

/**
 *
 * @author Mustafa AVCI
 */
public class SurgeryStaffManagement extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")

    private SurgeryStaffDAO ssDAO = new SurgeryStaffDAO();
    private SurgeryDAO surgeryDAO = new SurgeryDAO();
    private StaffDAO staffDAO = new StaffDAO();

    private List<Surgery> surgeryList;
    private List<Staff> staffList;

    /**
     * Creates new form SurgeryStaffManagement
     */
    public SurgeryStaffManagement() {

        // ÖNCELİKLE yetki kontrolü yap - initComponents'tan ÖNCE
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Please log in to continue. !");
            new LoginScreen().setVisible(true);
            return;  // Constructor'dan çık, ekran oluşturulmasın
        }

        if (!SessionManager.getInstance().isAdmin()) {
            JOptionPane.showMessageDialog(null, "You do not have permission to access this screen!");
            return;  // Constructor'dan çık, ekran oluşturulmasın
        }

        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        fillCombos();
        loadTable();

    }

    private void fillCombos() {
        // Surgery listesini doldur
        surgeryList = surgeryDAO.getAllForCombo();
        cmbSurgery.removeAllItems();
        for (Surgery s : surgeryList) {
            cmbSurgery.addItem(s.getDisplayText());
        }

        // Staff listesini doldur
        staffList = staffDAO.getAllForCombo();
        cmbStaff.removeAllItems();
        for (Staff st : staffList) {
            cmbStaff.addItem(st.getFirstName() + " " + st.getLastName());
        }
    }

    private void addRecord() {
        try {
            int surgeryIndex = cmbSurgery.getSelectedIndex();
            int staffIndex = cmbStaff.getSelectedIndex();

            if (surgeryIndex == -1 || staffIndex == -1) {
                JOptionPane.showMessageDialog(this, "You must select a surgery and a staff member!");
                return;
            }

            Surgery s = surgeryList.get(surgeryIndex);
            Staff st = staffList.get(staffIndex);

            SurgeryStaff ss = new SurgeryStaff();
            ss.setSurgeryId(s.getSurgeryId());
            ss.setStaffId(st.getStaffId());
            ss.setAssignedRole(txtRole.getText().trim());

            boolean ok = ssDAO.insert(ss);
            JOptionPane.showMessageDialog(this, ok ? "Added successfully!" : "Add operation failed!");

            loadTable();
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateRecord() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row!!");
            return;
        }
        try {
            int surgeryIndex = cmbSurgery.getSelectedIndex();
            int staffIndex = cmbStaff.getSelectedIndex();

            Surgery s = surgeryList.get(surgeryIndex);
            Staff st = staffList.get(staffIndex);

            SurgeryStaff ss = new SurgeryStaff();
            ss.setSurgeryId(s.getSurgeryId());
            ss.setStaffId(st.getStaffId());
            ss.setAssignedRole(txtRole.getText().trim());

            boolean ok = ssDAO.update(ss);
            JOptionPane.showMessageDialog(this, ok ? "Updated successfully!" : "Update failed");
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // ================= TABLE =================
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (SurgeryStaff ss : ssDAO.getAll()) {
            model.addRow(new Object[]{
                ss.getSurgeryId(),
                ss.getStaffId(),
                ss.getAssignedRole()
            });
        }
    }

    private void clearFields() {
        if (cmbSurgery.getItemCount() > 0) {
            cmbSurgery.setSelectedIndex(0);
        }
        if (cmbStaff.getItemCount() > 0) {
            cmbStaff.setSelectedIndex(0);
        }
        txtRole.setText("");
    }

    // ================= DELETE =================
    private void deleteRecord() {

        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }

        int surgeryId = (int) jTable1.getValueAt(row, 0);
        int staffId = (int) jTable1.getValueAt(row, 1);

        ssDAO.delete(surgeryId, staffId);
        loadTable();
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
        cmbSurgery = new javax.swing.JComboBox<>();
        lblSurgery = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        cmbStaff = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtRole = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        cmbSurgery.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbSurgery.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Surgeon", "Assistant Surgeon", "Nurse", "Anesthesia Specialist", "Technician" }));
        cmbSurgery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSurgeryActionPerformed(evt);
            }
        });

        lblSurgery.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblSurgery.setForeground(new java.awt.Color(255, 255, 255));
        lblSurgery.setText("Surgery");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "SURGERY_ID", "STAFF_ID", "ASSIGNED_ROLE"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnAdd.setBackground(new java.awt.Color(0, 51, 255));
        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 204, 102));
        btnUpdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 0, 102));
        btnDelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 51));
        btnClear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        cmbStaff.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbStaff.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Surgeon", "Assistant Surgeon", "Nurse", "Anesthesia Specialist", "Technician" }));
        cmbStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStaffActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Staff");

        txtRole.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Role");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblSurgery, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbSurgery, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnUpdate)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRole))))))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSurgery, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSurgery, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        addRecord();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateRecord();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteRecord();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }

        Object v0 = jTable1.getValueAt(row, 0);
        Object v1 = jTable1.getValueAt(row, 1);
        Object v2 = jTable1.getValueAt(row, 2);

        if (v0 == null || v1 == null || v2 == null) {
            return;
        }

        int surgeryId = (int) v0;
        int staffId = (int) v1;
        String role = v2.toString();

        for (int i = 0; i < surgeryList.size(); i++) {
            if (surgeryList.get(i).getSurgeryId() == surgeryId) {
                cmbSurgery.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffId() == staffId) {
                cmbStaff.setSelectedIndex(i);
                break;
            }
        }

        txtRole.setText(role);

    }//GEN-LAST:event_jTable1MouseClicked

    private void cmbSurgeryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSurgeryActionPerformed
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            return;
        }

        Object v0 = jTable1.getValueAt(row, 0);
        Object v1 = jTable1.getValueAt(row, 1);
        Object v2 = jTable1.getValueAt(row, 2);

        if (v0 == null || v1 == null) {
            return;
        }

        int surgeryId = Integer.parseInt(v0.toString());
        int staffId = Integer.parseInt(v1.toString());
        String role = (v2 != null) ? v2.toString() : "";

        // Surgery ComboBox'ı seç
        for (int i = 0; i < surgeryList.size(); i++) {
            if (surgeryList.get(i).getSurgeryId() == surgeryId) {
                cmbSurgery.setSelectedIndex(i);
                break;
            }
        }

        // Staff ComboBox'ı seç
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getStaffId() == staffId) {
                cmbStaff.setSelectedIndex(i);
                break;
            }
        }

        txtRole.setText(role);
    }//GEN-LAST:event_cmbSurgeryActionPerformed

    private void cmbStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStaffActionPerformed
        // TODO add your handling code here:
        int index = cmbStaff.getSelectedIndex();
        if (index >= 0) {
            Staff st = staffList.get(index);
        }
    }//GEN-LAST:event_cmbStaffActionPerformed

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
            java.util.logging.Logger.getLogger(SurgeryStaffManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SurgeryStaffManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SurgeryStaffManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SurgeryStaffManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SurgeryStaffManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStaff;
    private javax.swing.JComboBox<String> cmbSurgery;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblSurgery;
    private javax.swing.JTextField txtRole;
    // End of variables declaration//GEN-END:variables
}
