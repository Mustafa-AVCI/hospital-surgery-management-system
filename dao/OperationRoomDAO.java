/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.OperationRoom;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationRoomDAO {

    // EKLE
    public boolean insert(OperationRoom r) {
        String sql = "INSERT INTO OPERATION_ROOM (ROOM_CODE, DEPARTMENT_ID, CAPACITY, STATUS) "
                + "VALUES (?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getRoomCode());
            ps.setInt(2, r.getDepartmentId());
            ps.setInt(3, r.getCapacity());
            ps.setString(4, r.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // TÜM ODA LİSTELE
    public List<OperationRoom> getAll() {
        List<OperationRoom> list = new ArrayList<>();
        String sql = "SELECT * FROM OPERATION_ROOM";

        try ( Connection conn = DBConnection.getConnection();  Statement st = conn.createStatement();  ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                OperationRoom r = new OperationRoom(
                        rs.getInt("ROOM_ID"),
                        rs.getString("ROOM_CODE"),
                        rs.getInt("DEPARTMENT_ID"),
                        rs.getInt("CAPACITY"),
                        rs.getString("STATUS")
                );
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ========== UPDATE ==========
    public boolean update(OperationRoom r) {
        String sql = "UPDATE OPERATION_ROOM "
                + "SET ROOM_CODE = ?, DEPARTMENT_ID = ?, CAPACITY = ?, STATUS = ? "
                + "WHERE ROOM_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getRoomCode());
            ps.setInt(2, r.getDepartmentId());
            ps.setInt(3, r.getCapacity());
            ps.setString(4, r.getStatus());
            ps.setInt(5, r.getRoomId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("OperationRoom update hatası!");
            e.printStackTrace();
            return false;
        }
    }

    // ========== DELETE ==========
    public boolean delete(int roomId) {
        String sql = "DELETE FROM OPERATION_ROOM WHERE ROOM_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("OperationRoom delete hatası!");
            e.printStackTrace();
            return false;
        }
    }
}
