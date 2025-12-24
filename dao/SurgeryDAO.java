/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.Surgery;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurgeryDAO {

    // ============================
    // INSERT (EKLE)
    // ============================
    public int insert(Surgery s) {

        String sql = "INSERT INTO SURGERY (PATIENT_ID, ROOM_ID, SURGERY_DATE, SURGERY_TYPE, STATUS, NOTES) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, s.getPatientId());
            ps.setInt(2, s.getRoomId());
            ps.setTimestamp(3, (Timestamp) s.getSurgeryDate());
            ps.setString(4, s.getSurgeryType());
            ps.setString(5, s.getStatus());
            ps.setString(6, s.getNotes());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // yeni ID döner
            }

        } catch (SQLException e) {
            System.err.println("❌ INSERT HATASI: " + e.getMessage());

            // Foreign Key hatası kontrolü
            if (e.getMessage().contains("FK_SURGERY_ROOM")) {
                System.err.println("⚠️ HATA: Girdiğiniz ROOM_ID veritabanında mevcut değil!");
                System.err.println("   Lütfen OPERATION_ROOM tablosunda var olan bir ROOM_ID giriniz.");
            } else if (e.getMessage().contains("FK_SURGERY_PATIENT")) {
                System.err.println("⚠️ HATA: Girdiğiniz PATIENT_ID veritabanında mevcut değil!");
            }

            e.printStackTrace();
        }

        return -1;
    }

    // ============================
    // UPDATE (GÜNCELLE)
    // ============================
    public boolean update(Surgery s) {

        String sql = "UPDATE SURGERY SET PATIENT_ID=?, ROOM_ID=?, SURGERY_DATE=?, "
                + "SURGERY_TYPE=?, STATUS=?, NOTES=? WHERE SURGERY_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getPatientId());
            ps.setInt(2, s.getRoomId());
            ps.setTimestamp(3, (Timestamp) s.getSurgeryDate());
            ps.setString(4, s.getSurgeryType());
            ps.setString(5, s.getStatus());
            ps.setString(6, s.getNotes());
            ps.setInt(7, s.getSurgeryId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ UPDATE HATASI: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // ============================
    // DELETE (SİL) - CASCADE DELETE İLE
    // ============================
    public boolean delete(int surgeryId) {
        String deleteMaterial = "DELETE FROM SURGERY_MATERIAL WHERE SURGERY_ID=?";
        String deleteStaff = "DELETE FROM SURGERY_STAFF WHERE SURGERY_ID=?";
        String deleteSurgery = "DELETE FROM SURGERY WHERE SURGERY_ID=?";

        try ( Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);  // işlem bütünlüğü

            // 1 - SURGERY_MATERIAL kayıtlarını sil
            try ( PreparedStatement ps1 = conn.prepareStatement(deleteMaterial)) {
                ps1.setInt(1, surgeryId);
                ps1.executeUpdate();
            }

            // 2 - SURGERY_STAFF kayıtlarını sil
            try ( PreparedStatement ps2 = conn.prepareStatement(deleteStaff)) {
                ps2.setInt(1, surgeryId);
                ps2.executeUpdate();
            }

            // 3 - SURGERY kaydını sil
            try ( PreparedStatement ps3 = conn.prepareStatement(deleteSurgery)) {
                ps3.setInt(1, surgeryId);
                int rows = ps3.executeUpdate();

                conn.commit(); // başarılı
                return rows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // DELETE - ALTERNATİF: Soft Delete (Durum Güncellemesi)
    // ============================
    public boolean softDelete(int id) {
        String sql = "UPDATE SURGERY SET STATUS='CANCELLED' WHERE SURGERY_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            boolean result = ps.executeUpdate() > 0;

            if (result) {
                System.out.println("✓ SURGERY kaydı iptal edildi (Soft Delete - ID: " + id + ")");
            }

            return result;

        } catch (SQLException e) {
            System.err.println("❌ SOFT DELETE HATASI: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // ============================
    // GET ALL (LİSTELE)
    // ============================
    public List<Surgery> getAll() {

        List<Surgery> list = new ArrayList<>();
        String sql = "SELECT * FROM SURGERY ORDER BY SURGERY_ID DESC";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Surgery s = new Surgery(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("PATIENT_ID"),
                        rs.getInt("ROOM_ID"),
                        rs.getTimestamp("SURGERY_DATE"),
                        rs.getString("SURGERY_TYPE"),
                        rs.getString("STATUS"),
                        rs.getString("NOTES")
                );

                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ============================
    // GET BY ID (ID İLE BULMA)
    // ============================
    public Surgery getById(int id) {
        String sql = "SELECT * FROM SURGERY WHERE SURGERY_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Surgery(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("PATIENT_ID"),
                        rs.getInt("ROOM_ID"),
                        rs.getTimestamp("SURGERY_DATE"),
                        rs.getString("SURGERY_TYPE"),
                        rs.getString("STATUS"),
                        rs.getString("NOTES")
                );
            }

        } catch (SQLException e) {
            System.err.println("❌ GET BY ID HATASI: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // ============================
    // YARDIMCI METODLAR
    // ============================
    /**
     * Belirli bir ROOM_ID'nin OPERATION_ROOM tablosunda var olup olmadığını
     * kontrol eder
     */
    public boolean isRoomExists(int roomId) {
        String sql = "SELECT COUNT(*) FROM OPERATION_ROOM WHERE ROOM_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Belirli bir PATIENT_ID'nin PATIENT tablosunda var olup olmadığını kontrol
     * eder
     */
    public boolean isPatientExists(int patientId) {
        String sql = "SELECT COUNT(*) FROM PATIENT WHERE PATIENT_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Surgery> getAllForCombo() {

        List<Surgery> list = new ArrayList<>();

        String sql = """
        SELECT s.SURGERY_ID, s.SURGERY_DATE,
               p.FIRST_NAME, p.LAST_NAME
        FROM SURGERY s
        JOIN PATIENT p ON s.PATIENT_ID = p.PATIENT_ID
        ORDER BY s.SURGERY_DATE DESC
    """;

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Surgery s = new Surgery();
                s.setSurgeryId(rs.getInt("SURGERY_ID"));
                s.setSurgeryDate(rs.getTimestamp("SURGERY_DATE"));
                s.setDisplayText(
                        "ID:" + s.getSurgeryId() + " | "
                        + rs.getString("FIRST_NAME") + " "
                        + rs.getString("LAST_NAME")
                );
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
