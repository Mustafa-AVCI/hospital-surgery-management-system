/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.Patient;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // CREATE
    public boolean insert(Patient p) {
        String sql = "INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, BIRTH_DATE, GENDER, NATIONAL_ID, PHONE) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, p.getBirthDate());
            ps.setString(4, p.getGender());
            ps.setString(5, p.getNationalId());
            ps.setString(6, p.getPhone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================================
    // GET ALL - TÜM HASTALARI LİSTELE
    // ================================
    public List<Patient> getAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM PATIENT";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("PATIENT_ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getDate("BIRTH_DATE"),
                        rs.getString("GENDER"),
                        rs.getString("NATIONAL_ID"),
                        rs.getString("PHONE")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================================
    // DELETE - HASTA SİL
    // ================================
    public boolean deactivate(int patientId) {
        String sql = "UPDATE PATIENT SET ACTIVE = 0 WHERE PATIENT_ID = ?";
        try ( Connection c = DBConnection.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================================
    // UPDATE - HASTA GÜNCELLE
    // ================================
    public boolean update(Patient p) {
        String sql = "UPDATE PATIENT SET FIRST_NAME=?, LAST_NAME=?, BIRTH_DATE=?, GENDER=?, NATIONAL_ID=?, PHONE=? "
                + "WHERE PATIENT_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setDate(3, p.getBirthDate());
            ps.setString(4, p.getGender());
            ps.setString(5, p.getNationalId());
            ps.setString(6, p.getPhone());
            ps.setInt(7, p.getPatientId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByNationalId(String nationalId) {
        String sql = "SELECT COUNT(*) FROM PATIENT WHERE NATIONAL_ID = ?";
        try ( Connection c = DBConnection.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, nationalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
