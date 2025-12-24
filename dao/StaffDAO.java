/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Staff;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // CREATE
    public int insert(Staff s) {
        String sql = "INSERT INTO STAFF (FIRST_NAME, LAST_NAME, ROLE_ID, DEPARTMENT_ID, PHONE, EMAIL, ACTIVE) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setInt(3, s.getRoleId());
            ps.setInt(4, s.getDepartmentId());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getEmail());
            ps.setBoolean(7, s.isActive());

            int affected = ps.executeUpdate();

            if (affected == 0) {
                System.out.println("STAFF EKLENMEDİ!");
                return -1;
            }

            // OLUŞAN ID’Yİ ÇEK
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    System.out.println("Generated key bulunamadı!");
                    return -1;
                }
            }

        } catch (SQLException e) {
            System.out.println("Staff insert hata!");
            e.printStackTrace();
            return -1;
        }
    }

    // READ – tüm personel
    public List<Staff> getAll() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM STAFF";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Staff s = new Staff(
                        rs.getInt("STAFF_ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getInt("ROLE_ID"),
                        rs.getInt("DEPARTMENT_ID"),
                        rs.getString("PHONE"),
                        rs.getString("EMAIL"),
                        rs.getBoolean("ACTIVE")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(Staff s) {
        String sql = "UPDATE STAFF SET FIRST_NAME=?, LAST_NAME=?, ROLE_ID=?, DEPARTMENT_ID=?, "
                + "PHONE=?, EMAIL=?, ACTIVE=? WHERE STAFF_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setInt(3, s.getRoleId());
            ps.setInt(4, s.getDepartmentId());
            ps.setString(5, s.getPhone());
            ps.setString(6, s.getEmail());
            ps.setBoolean(7, s.isActive());
            ps.setInt(8, s.getStaffId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(int staffId) {
        String sql = "DELETE FROM STAFF WHERE STAFF_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Staff> getAllForCombo() {

        List<Staff> list = new ArrayList<>();

        String sql = "SELECT STAFF_ID, FIRST_NAME, LAST_NAME FROM STAFF";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Staff s = new Staff();
                s.setStaffId(rs.getInt("STAFF_ID"));
                s.setFirstName(rs.getString("FIRST_NAME"));
                s.setLastName(rs.getString("LAST_NAME"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
