/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Uİ;

import dao.SurgeryDAO;
import java.awt.HeadlessException;
import model.Surgery;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.List;
import util.SessionManager;

/**
 *
 * @author Mustafa AVCI
 */
public class SurgeryManagement extends javax.swing.JFrame {

    /**
     */
    public SurgeryManagement() {
        initComponents();
        loadSurgeryTable();
         setLocationRelativeTo(null);
         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
         // 1) Session guard
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Oturum bulunamadı! Giriş ekranına yönlendiriliyorsunuz.");
            new LoginScreen().setVisible(true);
            this.dispose();
            return;
        }

        // 2) Role guard (ADMIN)
        if (!SessionManager.getInstance().isAdmin()) {
            JOptionPane.showMessageDialog(this, "Bu ekrana yetkiniz yok!");
            this.dispose();
            return;
        }
    }

    private SurgeryDAO surgeryDAO = new SurgeryDAO();

    private void loadSurgeryTable() {
        List<Surgery> list = surgeryDAO.getAll();
        DefaultTableModel model = (DefaultTableModel) tblSurgery.getModel();
        model.setRowCount(0);

        for (Surgery s : list) {
            model.addRow(new Object[]{
                s.getSurgeryId(),
                s.getPatientId(),
                s.getRoomId(),
                s.getSurgeryDate(),
                s.getSurgeryType(),
                s.getStatus(),
                s.getNotes()
            });
        }
    }

    private void addSurgery() {
        try {
            Surgery s = new Surgery();
            s.setPatientId(Integer.parseInt(txtPatientId.getText()));
            s.setRoomId(Integer.parseInt(txtRoomId.getText()));
            s.setSurgeryDate(Timestamp.valueOf(txtDate.getText() + " 00:00:00"));
            s.setSurgeryType(txtSurgeryType.getText());
            s.setStatus(cmbStatus.getSelectedItem().toString());
            s.setNotes(txtNotes.getText());

            int ok = surgeryDAO.insert(s);
            JOptionPane.showMessageDialog(this, ok > 0 ? "Ameliyat eklendi!" : "Hata oluştu");

            loadSurgeryTable();
            clearFields();

        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verileri kontrol et: " + e.getMessage());
        }
    }

    private void updateSurgery() {
        int row = tblSurgery.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Güncellemek için bir satır seç!");
            return;
        }

        try {
            // BOŞ ALAN KONTROLÜ
            if (txtPatientId.getText().trim().isEmpty()
                    || txtRoomId.getText().trim().isEmpty()
                    || txtDate.getText().trim().isEmpty()
                    || txtSurgeryType.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "❌ Lütfen tüm alanları doldurunuz!",
                        "Eksik Bilgi",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = (int) tblSurgery.getValueAt(row, 0);
            int patientId = Integer.parseInt(txtPatientId.getText().trim());
            int roomId = Integer.parseInt(txtRoomId.getText().trim());

            // KONTROL: Room ID var mı?
            if (!surgeryDAO.isRoomExists(roomId)) {
                JOptionPane.showMessageDialog(this,
                        "❌ Room ID " + roomId + " bulunamadı!\nLütfen geçerli bir Room ID giriniz.",
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // KONTROL: Patient ID var mı?
            if (!surgeryDAO.isPatientExists(patientId)) {
                JOptionPane.showMessageDialog(this,
                        "❌ Patient ID " + patientId + " bulunamadı!\nLütfen geçerli bir Patient ID giriniz.",
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Surgery s = new Surgery();
            s.setSurgeryId(id);
            s.setPatientId(patientId);
            s.setRoomId(roomId);
            s.setSurgeryDate(Timestamp.valueOf(txtDate.getText().trim() + " 00:00:00"));
            s.setSurgeryType(txtSurgeryType.getText().trim());
            s.setStatus(cmbStatus.getSelectedItem().toString());
            s.setNotes(txtNotes.getText().trim());

            boolean ok = surgeryDAO.update(s);
            JOptionPane.showMessageDialog(this, ok ? "✓ Güncellendi!" : "❌ Güncelleme hatası");
            loadSurgeryTable();
            clearFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Patient ID ve Room ID sayı olmalıdır!",
                    "Geçersiz Veri",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Veri hatası: " + e.getMessage(),
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deleteSurgery() {
        int row = tblSurgery.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Silmek için bir satır seç!");
            return;
        }

        int id = (int) tblSurgery.getValueAt(row, 0);

        boolean ok = surgeryDAO.delete(id);
        JOptionPane.showMessageDialog(this, ok ? "Silindi!" : "Silme hatası");

        loadSurgeryTable();
        clearFields();
    }

    private void clearFields() {
        txtPatientId.setText("");
        txtSurgeryId.setText("");  // ← BU EKSİK (eğer böyle bir field varsa)
        txtRoomId.setText("");
        txtDate.setText("");
        txtSurgeryType.setText("");
        txtNotes.setText("");
        cmbStatus.setSelectedIndex(0);
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
        txtPatientId = new javax.swing.JTextField();
        txtRoomId = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtSurgeryType = new javax.swing.JTextField();
        txtNotes = new javax.swing.JTextField();
        lblpatientid = new javax.swing.JLabel();
        lblRoomId = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        Nodes = new javax.swing.JLabel();
        s = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSurgery = new javax.swing.JTable();
        cmbStatus = new javax.swing.JComboBox<>();
        lblDate1 = new javax.swing.JLabel();
        txtSurgeryId = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Surgery Management Screen");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        txtPatientId.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtRoomId.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtSurgeryType.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtNotes.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lblpatientid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblpatientid.setForeground(new java.awt.Color(255, 255, 255));
        lblpatientid.setText("Patient  ID");

        lblRoomId.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblRoomId.setForeground(new java.awt.Color(255, 255, 255));
        lblRoomId.setText("Room ID");

        lblDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("Date");

        lblType.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblType.setForeground(new java.awt.Color(255, 255, 255));
        lblType.setText("Type");

        Nodes.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        Nodes.setForeground(new java.awt.Color(255, 255, 255));
        Nodes.setText("Notes");

        s.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        s.setForeground(new java.awt.Color(255, 255, 255));
        s.setText("Status");

        btnAdd.setBackground(new java.awt.Color(0, 51, 204));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 204, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 153));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 153));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        tblSurgery.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblSurgery.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "SurgeryId", "PatientId", "RoomId", "Data", "Type", "Status", "Notes"
            }
        ));
        tblSurgery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSurgeryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSurgery);

        cmbStatus.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PLANNED", "ONGOING", "COMPLETED", "CANCELLED" }));

        lblDate1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblDate1.setForeground(new java.awt.Color(255, 255, 255));
        lblDate1.setText("Surgery_ID");

        txtSurgeryId.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblpatientid)
                            .addComponent(lblDate1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSurgeryId)
                    .addComponent(txtPatientId)
                    .addComponent(txtRoomId)
                    .addComponent(txtDate)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(btnUpdate)))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Nodes, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNotes, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtSurgeryType, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cmbStatus, 0, 101, Short.MAX_VALUE))
                    .addComponent(btnClear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPatientId)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSurgeryType, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblpatientid, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSurgeryId, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nodes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(s)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
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

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateSurgery();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        addSurgery();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteSurgery();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblSurgeryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSurgeryMouseClicked

        int row = tblSurgery.getSelectedRow();

        if (row != -1) {
            try {
                // SurgeryId (kolon 0)
                txtSurgeryId.setText(tblSurgery.getValueAt(row, 0).toString());

                // PatientId (kolon 1)
                txtPatientId.setText(tblSurgery.getValueAt(row, 1).toString());

                // RoomId (kolon 2)
                txtRoomId.setText(tblSurgery.getValueAt(row, 2).toString());

                // Date (kolon 3) - Tarih formatını düzelt
                String dateStr = tblSurgery.getValueAt(row, 3).toString();
                if (dateStr.contains(" ")) {
                    dateStr = dateStr.substring(0, dateStr.indexOf(" "));
                }
                txtDate.setText(dateStr);

                // SurgeryType (kolon 4)
                txtSurgeryType.setText(tblSurgery.getValueAt(row, 4).toString());

                // Status (kolon 5)
                cmbStatus.setSelectedItem(tblSurgery.getValueAt(row, 5).toString());

                // Notes (kolon 6)
                Object notes = tblSurgery.getValueAt(row, 6);
                txtNotes.setText(notes != null ? notes.toString() : "");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        " Veri yüklenirken hata: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblSurgeryMouseClicked

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
            java.util.logging.Logger.getLogger(SurgeryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SurgeryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SurgeryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SurgeryManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SurgeryManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Nodes;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDate1;
    private javax.swing.JLabel lblRoomId;
    private javax.swing.JLabel lblType;
    private javax.swing.JLabel lblpatientid;
    private javax.swing.JLabel s;
    private javax.swing.JTable tblSurgery;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtNotes;
    private javax.swing.JTextField txtPatientId;
    private javax.swing.JTextField txtRoomId;
    private javax.swing.JTextField txtSurgeryId;
    private javax.swing.JTextField txtSurgeryType;
    // End of variables declaration//GEN-END:variables
}
