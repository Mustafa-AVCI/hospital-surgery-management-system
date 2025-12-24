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

public class SurgeonSpecialtyMapDAO {

    /**
     * Bir cerraha ait tüm uzmanlık alanlarını getirir (ValidationUtil için)
     */
    public List<model.SurgeonSpecialtyMap> getSpecialtiesBySurgeon(int surgeonId) {
        List<model.SurgeonSpecialtyMap> specialties = new ArrayList<>();
        String sql = "SELECT ssm.SURGEON_ID, ssm.SPECIALTY_ID, "
                + "       (st.FIRST_NAME + ' ' + st.LAST_NAME) AS SURGEON_NAME, "
                + "       sp.NAME AS SPECIALTY_NAME "
                + "FROM SURGEON_SPECIALTY_MAP ssm "
                + "JOIN SURGEON s ON ssm.SURGEON_ID = s.SURGEON_ID "
                + "JOIN STAFF st ON s.STAFF_ID = st.STAFF_ID "
                + "JOIN SURGEON_SPECIALTY sp ON ssm.SPECIALTY_ID = sp.SPECIALTY_ID "
                + "WHERE ssm.SURGEON_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, surgeonId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.SurgeonSpecialtyMap map = new model.SurgeonSpecialtyMap();
                map.setSurgeonId(rs.getInt("SURGEON_ID"));
                map.setSpecialtyId(rs.getInt("SPECIALTY_ID"));
                map.setSurgeonName(rs.getString("SURGEON_NAME"));
                map.setSpecialtyName(rs.getString("SPECIALTY_NAME"));
                specialties.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialties;
    }

    // 1) CERRAHA UZMANLIK EKLE
    public boolean addSpecialtyToSurgeon(int surgeonId, int specialtyId) {
        String sql = "INSERT INTO SURGEON_SPECIALTY_MAP (SURGEON_ID, SPECIALTY_ID) VALUES (?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeonId);
            ps.setInt(2, specialtyId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2) CERRAHIN BİR UZMANLIĞINI SİL
    public boolean removeSpecialtyFromSurgeon(int surgeonId, int specialtyId) {
        String sql = "DELETE FROM SURGEON_SPECIALTY_MAP WHERE SURGEON_ID=? AND SPECIALTY_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeonId);
            ps.setInt(2, specialtyId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3) BELİRLİ CERAHIN UZMANLIKLARI
    public List<SurgeonSpecialty> getSpecialtiesOfSurgeon(int surgeonId) {
        List<SurgeonSpecialty> list = new ArrayList<>();

        String sql
                = "SELECT ssp.SPECIALTY_ID, ssp.NAME "
                + "FROM SURGEON_SPECIALTY_MAP sm "
                + "JOIN SURGEON_SPECIALTY ssp ON sm.SPECIALTY_ID = ssp.SPECIALTY_ID "
                + "WHERE sm.SURGEON_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeonId);
            ResultSet rs = ps.executeQuery();

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
}
