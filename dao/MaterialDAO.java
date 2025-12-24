/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.Material;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    /**
     * Tüm malzemeleri getirir
     */
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT m.*, mc.NAME AS CATEGORY_NAME "
                + "FROM MATERIAL m "
                + "LEFT JOIN MATERIAL_CATEGORY mc ON m.CATEGORY_ID = mc.CATEGORY_ID "
                + "ORDER BY m.NAME";

        try ( Connection conn = DBConnection.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                materials.add(extractMaterialFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }

    /**
     * ID'ye göre malzeme getirir
     */
    public Material getMaterialById(int materialId) {
        String sql = "SELECT m.*, mc.NAME AS CATEGORY_NAME "
                + "FROM MATERIAL m "
                + "LEFT JOIN MATERIAL_CATEGORY mc ON m.CATEGORY_ID = mc.CATEGORY_ID "
                + "WHERE m.MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, materialId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractMaterialFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Yeni malzeme ekler
     */
    public boolean addMaterial(Material material) {
        String sql = "INSERT INTO MATERIAL (CATEGORY_ID, NAME, UNIT, CRITICAL_STOCK_LEVEL) "
                + "VALUES (?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, material.getCategoryId());
            pstmt.setString(2, material.getName());
            pstmt.setString(3, material.getUnit());
            pstmt.setInt(4, material.getCriticalStockLevel());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    material.setMaterialId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Malzeme günceller
     */
    public boolean updateMaterial(Material material) {
        String sql = "UPDATE MATERIAL SET CATEGORY_ID = ?, NAME = ?, UNIT = ?, "
                + "CRITICAL_STOCK_LEVEL = ? WHERE MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, material.getCategoryId());
            pstmt.setString(2, material.getName());
            pstmt.setString(3, material.getUnit());
            pstmt.setInt(4, material.getCriticalStockLevel());
            pstmt.setInt(5, material.getMaterialId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Malzeme siler
     */
    public boolean deleteMaterial(int materialId) {
        String sql = "DELETE FROM MATERIAL WHERE MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, materialId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * ResultSet'ten Material objesi oluşturur
     */
    private Material extractMaterialFromResultSet(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setMaterialId(rs.getInt("MATERIAL_ID"));
        material.setCategoryId(rs.getInt("CATEGORY_ID"));
        material.setName(rs.getString("NAME"));
        material.setUnit(rs.getString("UNIT"));
        material.setCriticalStockLevel(rs.getInt("CRITICAL_STOCK_LEVEL"));
        material.setCategoryName(rs.getString("CATEGORY_NAME"));
        return material;
    }
}
