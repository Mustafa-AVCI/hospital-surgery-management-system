/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.SurgeryStaff;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurgeryStaffDAO {

    // EKLE
    public boolean insert(SurgeryStaff ss) {
        String sql = "INSERT INTO SURGERY_STAFF (SURGERY_ID, STAFF_ID, ASSIGNED_ROLE) "
                + "VALUES (?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ss.getSurgeryId());
            ps.setInt(2, ss.getStaffId());
            ps.setString(3, ss.getAssignedRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SurgeryStaff insert hatası!");
            e.printStackTrace();
            return false;
        }
    }

    // BELİRLİ BİR AMELİYATTAKİ TÜM PERSONELİ GETİR
    public List<SurgeryStaff> getBySurgery(int surgeryId) {
        List<SurgeryStaff> list = new ArrayList<>();
        String sql = "SELECT * FROM SURGERY_STAFF WHERE SURGERY_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SurgeryStaff ss = new SurgeryStaff(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("STAFF_ID"),
                        rs.getString("ASSIGNED_ROLE")
                );
                list.add(ss);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // =========================================================
    // 2) UPDATE
    // =========================================================
    public boolean update(SurgeryStaff ss) {
        String sql = "UPDATE SURGERY_STAFF SET STAFF_ID=?, ASSIGNED_ROLE=? WHERE SURGERY_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ss.getStaffId());
            ps.setString(2, ss.getAssignedRole());
            ps.setInt(3, ss.getSurgeryId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SurgeryStaff update hatası:");
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // 3) DELETE
    // =========================================================
    public boolean delete(int surgeryId, int staffId) {
        String sql = "DELETE FROM SURGERY_STAFF WHERE SURGERY_ID=? AND STAFF_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeryId);
            ps.setInt(2, staffId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SurgeryStaff delete hatası:");
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // 4) GET ALL  ✔✔ (Senin formun bunu istiyor!)
    // =========================================================
    public List<SurgeryStaff> getAll() {
        List<SurgeryStaff> list = new ArrayList<>();
         String sql = "SELECT SURGERY_ID, STAFF_ID, ASSIGNED_ROLE FROM SURGERY_STAFF";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                SurgeryStaff ss = new SurgeryStaff(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("STAFF_ID"),
                        rs.getString("ASSIGNED_ROLE")
                );
                list.add(ss);
            }

        } catch (SQLException e) {
            System.out.println("SurgeryStaff getAll hatası:");
            e.printStackTrace();
        }

        return list;
    }
}
