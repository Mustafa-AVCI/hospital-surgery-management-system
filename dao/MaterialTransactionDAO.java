/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.MaterialTransaction;
import model.Inventory;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialTransactionDAO {

    public boolean insert(MaterialTransaction mt) {
        String sql = "INSERT INTO MATERIAL_TRANSACTION (MATERIAL_ID, LOCATION_ID, QUANTITY, TRANSACTION_TYPE, TRANSACTION_DATE, RELATED_SURGERY_ID, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mt.getMaterialId());
            ps.setInt(2, mt.getLocationId());
            ps.setInt(3, mt.getQuantity());
            ps.setString(4, mt.getType());
            ps.setTimestamp(5, mt.getDate());
            ps.setObject(6, null); // RELATED_SURGERY_ID - set to null or add to your model
            ps.setObject(7, null); // USER_ID - set to null or add to your model
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MaterialTransaction> getAll() {
        List<MaterialTransaction> list = new ArrayList<>();
        String sql = "SELECT * FROM MATERIAL_TRANSACTION";
        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MaterialTransaction(
                        rs.getInt("TRANSACTION_ID"),
                        rs.getInt("MATERIAL_ID"),
                        rs.getInt("LOCATION_ID"),
                        rs.getInt("QUANTITY"),
                        rs.getString("TRANSACTION_TYPE"),
                        rs.getTimestamp("TRANSACTION_DATE"),
                        rs.getString("RELATED_SURGERY_ID") != null ? rs.getString("RELATED_SURGERY_ID") : ""
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(model.MaterialTransaction mt) {

        String sql
                = "UPDATE MATERIAL_TRANSACTION "
                + "SET MATERIAL_ID = ?, "
                + "    LOCATION_ID = ?, "
                + "    TRANSACTION_TYPE = ?, "
                + "    QUANTITY = ?, "
                + "    TRANSACTION_DATE = ? "
                + "WHERE TRANSACTION_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mt.getMaterialId());
            ps.setInt(2, mt.getLocationId());
            ps.setString(3, mt.getType());
            ps.setInt(4, mt.getQuantity());
            ps.setTimestamp(5, mt.getDate());
            ps.setInt(6, mt.getTransactionId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM MATERIAL_TRANSACTION WHERE TRANSACTION_ID=?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
