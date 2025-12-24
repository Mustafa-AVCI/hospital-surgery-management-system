/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */

import model.Surgeon;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Surgery;

public class SurgeonDAO {

    // 1) CERRAH EKLE
    public int insert(Surgery s) {
    String sql = "INSERT INTO SURGERY (PATIENT_ID, ROOM_ID, SURGERY_DATE, SURGERY_TYPE, STATUS, NOTES) "
               + "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, s.getPatientId());
        ps.setInt(2, s.getRoomId());
        ps.setTimestamp(3, (Timestamp) s.getSurgeryDate());
        ps.setString(4, s.getSurgeryType());
        ps.setString(5, s.getStatus());
        ps.setString(6, s.getNotes());

        ps.executeUpdate(); // ✔ zorunlu

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}



    // 2) TÜM CERRAHLARI GETİR
    public List<Surgeon> getAll() {

        List<Surgeon> list = new ArrayList<>();
        String sql = "SELECT * FROM SURGEON";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Surgeon s = new Surgeon(
                    rs.getInt("SURGEON_ID"),
                    rs.getInt("STAFF_ID"),
                    rs.getString("LICENSE_NO"),
                    rs.getInt("YEARS_OF_EXPERIENCE")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Surgeon list hata!");
            e.printStackTrace();
        }

        return list;
    }


    // 3) CERRAH GÜNCELLE
    public boolean update(Surgeon s) {

        String sql = "UPDATE SURGEON SET STAFF_ID=?, LICENSE_NO=?, YEARS_OF_EXPERIENCE=? " +
                     "WHERE SURGEON_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, s.getStaffId());
            ps.setString(2, s.getLicenseNo());
            ps.setInt(3, s.getYearsOfExperience());
            ps.setInt(4, s.getSurgeonId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Surgeon update hatası!");
            e.printStackTrace();
            return false;
        }
    }


    // 4) CERRAH SİL
    public boolean delete(int surgeonId) {

        String sql = "DELETE FROM SURGEON WHERE SURGEON_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeonId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Surgeon delete hatası!");
            e.printStackTrace();
            return false;
        }
    }


    // BONUS → CERRAH DETAYLI LİSTE (STAFF JOIN)
    public List<String> getSurgeonWithStaffNames() {

        List<String> list = new ArrayList<>();

        String sql = "SELECT sg.SURGEON_ID, st.FIRST_NAME, st.LAST_NAME, " +
                     "sg.LICENSE_NO, sg.YEARS_OF_EXPERIENCE " +
                     "FROM SURGEON sg " +
                     "JOIN STAFF st ON sg.STAFF_ID = st.STAFF_ID";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String line =
                    rs.getInt("SURGEON_ID") + " - " +
                    rs.getString("FIRST_NAME") + " " +
                    rs.getString("LAST_NAME") + " | Lisans: " +
                    rs.getString("LICENSE_NO") + " | Deneyim: " +
                    rs.getInt("YEARS_OF_EXPERIENCE");

                list.add(line);
            }

        } catch (SQLException e) {
            System.out.println("Surgeon JOIN Staff hata!");
            e.printStackTrace();
        }

        return list;
    }
}

