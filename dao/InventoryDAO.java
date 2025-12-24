package dao;

import model.Inventory;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public boolean upsert(Inventory inv) {
        String sql = "IF EXISTS (SELECT 1 FROM INVENTORY WHERE LOCATION_ID=? AND MATERIAL_ID=?) "
                + "UPDATE INVENTORY SET QUANTITY = QUANTITY + ? WHERE LOCATION_ID=? AND MATERIAL_ID=? "
                + "ELSE "
                + "INSERT INTO INVENTORY (LOCATION_ID, MATERIAL_ID, QUANTITY) VALUES (?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inv.getLocationId());
            ps.setInt(2, inv.getMaterialId());
            ps.setInt(3, inv.getQuantity());
            ps.setInt(4, inv.getLocationId());
            ps.setInt(5, inv.getMaterialId());
            ps.setInt(6, inv.getLocationId());
            ps.setInt(7, inv.getMaterialId());
            ps.setInt(8, inv.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Inventory upsert hata!");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Inventory> getAll() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Inventory(
                        rs.getInt("LOCATION_ID"),
                        rs.getInt("MATERIAL_ID"),
                        rs.getInt("QUANTITY")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public boolean update(Inventory i) {
        String sql = "UPDATE INVENTORY SET QUANTITY=? WHERE LOCATION_ID=? AND MATERIAL_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, i.getQuantity());
            ps.setInt(2, i.getLocationId());
            ps.setInt(3, i.getMaterialId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean delete(int locationId, int materialId) {
        String sql = "DELETE FROM INVENTORY WHERE LOCATION_ID=? AND MATERIAL_ID=?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, locationId);
            ps.setInt(2, materialId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean decrease(int locationId, int materialId, int amount) {
        String sql = "UPDATE INVENTORY SET QUANTITY = QUANTITY - ? "
                + "WHERE LOCATION_ID=? AND MATERIAL_ID=? AND QUANTITY >= ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, amount);
            ps.setInt(2, locationId);
            ps.setInt(3, materialId);
            ps.setInt(4, amount);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Inventory getInventory(int locationId, int materialId) {
        String sql = "SELECT * FROM INVENTORY WHERE LOCATION_ID = ? AND MATERIAL_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, locationId);
            pstmt.setInt(2, materialId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setLocationId(rs.getInt("LOCATION_ID"));
                inventory.setMaterialId(rs.getInt("MATERIAL_ID"));
                inventory.setQuantity(rs.getInt("QUANTITY"));
                return inventory;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
