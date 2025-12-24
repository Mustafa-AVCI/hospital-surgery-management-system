/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.Hospital;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {

    public List<Hospital> getAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        String sql = "SELECT * FROM HOSPITAL ORDER BY NAME";

        try ( Connection conn = DBConnection.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                hospitals.add(extractHospitalFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospitals;
    }

    /**
     * ID'ye göre hastane getirir
     */
    public Hospital getHospitalById(int hospitalId) {
        String sql = "SELECT * FROM HOSPITAL WHERE HOSPITAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hospitalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractHospitalFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Yeni hastane ekler
     */
    public boolean addHospital(Hospital hospital) {
        String sql = "INSERT INTO HOSPITAL (NAME, ADDRESS, PHONE) VALUES (?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, hospital.getName());
            pstmt.setString(2, hospital.getAddress());
            pstmt.setString(3, hospital.getPhone());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    hospital.setHospitalId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Hastane bilgilerini günceller
     */
    public boolean updateHospital(Hospital hospital) {
        String sql = "UPDATE HOSPITAL SET NAME = ?, ADDRESS = ?, PHONE = ? "
                + "WHERE HOSPITAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, hospital.getName());
            pstmt.setString(2, hospital.getAddress());
            pstmt.setString(3, hospital.getPhone());
            pstmt.setInt(4, hospital.getHospitalId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Hastane siler
     */
    public boolean deleteHospital(int hospitalId) {
        String sql = "DELETE FROM HOSPITAL WHERE HOSPITAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hospitalId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Hastane altında departman var mı kontrol eder
     */
    public boolean hasDepartments(int hospitalId) {
        String sql = "SELECT COUNT(*) FROM DEPARTMENT WHERE HOSPITAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hospitalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Hastane istatistiklerini getirir
     */
    public int getDepartmentCount(int hospitalId) {
        String sql = "SELECT COUNT(*) FROM DEPARTMENT WHERE HOSPITAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hospitalId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * ResultSet'ten Hospital objesi oluşturur
     */
    private Hospital extractHospitalFromResultSet(ResultSet rs) throws SQLException {
        Hospital hospital = new Hospital();
        hospital.setHospitalId(rs.getInt("HOSPITAL_ID"));
        hospital.setName(rs.getString("NAME"));
        hospital.setAddress(rs.getString("ADDRESS"));
        hospital.setPhone(rs.getString("PHONE"));
        return hospital;
    }
}
