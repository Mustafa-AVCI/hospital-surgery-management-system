/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */

import model.SurgeonSpecialty;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurgeonSpecialtyDAO {

    // INSERT
    public boolean insert(SurgeonSpecialty sp) {
        String sql = "INSERT INTO SURGEON_SPECIALTY (NAME) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getName());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL
    public List<SurgeonSpecialty> getAll() {
        List<SurgeonSpecialty> list = new ArrayList<>();
        String sql = "SELECT SPECIALTY_ID, NAME FROM SURGEON_SPECIALTY";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                SurgeonSpecialty sp = new SurgeonSpecialty(
                        rs.getInt("SPECIALTY_ID"),
                        rs.getString("NAME")
                );
                list.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // DELETE
    public boolean delete(int specialtyId) {
        String sql = "DELETE FROM SURGEON_SPECIALTY WHERE SPECIALTY_ID=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, specialtyId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
