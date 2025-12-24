package dao;

import model.StorageLocation;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageLocationDAO {

    // INSERT
    public boolean insert(StorageLocation sl) {
        String sql = "INSERT INTO STORAGE_LOCATION (NAME, DEPARTMENT_ID, STATUS) VALUES (?, ?, ?)";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sl.getName());
            ps.setInt(2, sl.getDepartmentId());
            ps.setString(3, sl.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // SELECT ALL
    public List<StorageLocation> getAll() {
        List<StorageLocation> list = new ArrayList<>();
        String sql = "SELECT * FROM STORAGE_LOCATION";
        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                StorageLocation sl = new StorageLocation(
                        rs.getInt("LOCATION_ID"),
                        rs.getString("NAME"),
                        rs.getInt("DEPARTMENT_ID"),
                        rs.getString("STATUS")
                );
                list.add(sl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean update(StorageLocation sl) {
        String sql = "UPDATE STORAGE_LOCATION SET NAME=?, DEPARTMENT_ID=?, STATUS=? WHERE LOCATION_ID=?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sl.getName());
            ps.setInt(2, sl.getDepartmentId());
            ps.setString(3, sl.getStatus());
            ps.setInt(4, sl.getLocationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM STORAGE_LOCATION WHERE LOCATION_ID=?";
        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
