/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.SurgeryMaterial;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurgeryMaterialDAO {

    // ===========================
    // 1) INSERT (Surgery Material)
    // ===========================
    public boolean insert(SurgeryMaterial sm) {
        String sql = "INSERT INTO SURGERY_MATERIAL (SURGERY_ID, MATERIAL_ID, QUANTITY_USED) "
                + "VALUES (?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sm.getSurgeryId());
            ps.setInt(2, sm.getMaterialId());
            ps.setInt(3, sm.getQuantityUsed());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SurgeryMaterial insert hatası!");
            e.printStackTrace();
            return false;
        }
    }

    // =======================================
    // 2) GET ALL MATERIALS FOR A SURGERY
    // =======================================
    public List<SurgeryMaterial> getBySurgery(int surgeryId) {
        List<SurgeryMaterial> list = new ArrayList<>();

        String sql = "SELECT * FROM SURGERY_MATERIAL WHERE SURGERY_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SurgeryMaterial sm = new SurgeryMaterial(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("MATERIAL_ID"),
                        rs.getInt("QUANTITY_USED")
                );
                list.add(sm);
            }

        } catch (SQLException e) {
            System.out.println("getBySurgery hatası!");
            e.printStackTrace();
        }

        return list;
    }

    // =======================================
    // 3) STOKTAN DÜŞME (ameliyatta kullanılan)
    // =======================================
    public boolean decreaseInventory(int materialId, int quantityUsed) {

        String sql = "UPDATE INVENTORY "
                + "SET QUANTITY = QUANTITY - ? "
                + "WHERE MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantityUsed);
            ps.setInt(2, materialId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Inventory stok düşme hatası!");
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // UPDATE
    // ============================
    public boolean update(SurgeryMaterial sm) {
        String sql = "UPDATE SURGERY_MATERIAL SET QUANTITY_USED = ? WHERE SURGERY_ID = ? AND MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sm.getQuantityUsed());
            ps.setInt(2, sm.getSurgeryId());
            ps.setInt(3, sm.getMaterialId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ============================
    // DELETE
    // ============================
    public boolean delete(int surgeryId, int materialId) {
        String sql = "DELETE FROM SURGERY_MATERIAL WHERE SURGERY_ID = ? AND MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeryId);
            ps.setInt(2, materialId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ============================
    // GET ALL
    // ============================
    public List<SurgeryMaterial> getAll() {
        List<SurgeryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM SURGERY_MATERIAL";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                SurgeryMaterial sm = new SurgeryMaterial(
                        rs.getInt("SURGERY_ID"),
                        rs.getInt("MATERIAL_ID"),
                        rs.getInt("QUANTITY_USED")
                );

                list.add(sm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // =======================================
// KAYIT VAR MI? (PK kontrolü)
// =======================================
    public boolean exists(int surgeryId, int materialId) {
        String sql = "SELECT COUNT(*) FROM SURGERY_MATERIAL WHERE SURGERY_ID=? AND MATERIAL_ID=?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, surgeryId);
            ps.setInt(2, materialId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// =======================================
// MİKTAR ARTIR (aynı malzeme tekrar eklenirse)
// =======================================
    public boolean increaseQuantity(int surgeryId, int materialId, int qty) {
        String sql
                = "UPDATE SURGERY_MATERIAL "
                + "SET QUANTITY_USED = QUANTITY_USED + ? "
                + "WHERE SURGERY_ID=? AND MATERIAL_ID=?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, surgeryId);
            ps.setInt(3, materialId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
