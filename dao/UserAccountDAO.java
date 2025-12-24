/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */
import model.UserAccount;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.PasswordUtil;

public class UserAccountDAO {

    /**
     * Kullanıcı adı ve şifre ile kullanıcıyı doğrular
     */
    public UserAccount authenticate(String username, String password) {

        String sql = "SELECT * FROM USER_ACCOUNT WHERE USERNAME = ? AND PASSWORD_HASH = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.simpleHash(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Tüm kullanıcıları getirir
     */
    public List<UserAccount> getAllUsers() {
        List<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM USER_ACCOUNT ORDER BY USERNAME";

        try ( Connection conn = DBConnection.getConnection();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * ID'ye göre kullanıcı getirir
     */
    public UserAccount getUserById(int userId) {
        String sql = "SELECT * FROM USER_ACCOUNT WHERE USER_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Yeni kullanıcı ekler
     */
    public boolean addUser(UserAccount user) {

        if (isUsernameExists(user.getUsername())) {
            return false;
        }

        String sql = "INSERT INTO USER_ACCOUNT (STAFF_ID, USERNAME, PASSWORD_HASH, ROLE) "
                + "VALUES (?, ?, ?, ?)";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (user.getStaffId() != null) {
                pstmt.setInt(1, user.getStaffId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }

            pstmt.setString(2, user.getUsername());

            // HASH BURADA YAPILIYOR
            pstmt.setString(3, PasswordUtil.simpleHash(user.getPassword()));

            pstmt.setString(4, user.getRole());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Kullanıcı bilgilerini günceller
     */
    public boolean updateUser(UserAccount user) {
        String sql = "UPDATE USER_ACCOUNT SET STAFF_ID = ?, USERNAME = ?, "
                + "PASSWORD_HASH = ?, ROLE = ? WHERE USER_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (user.getStaffId() != null) {
                pstmt.setInt(1, user.getStaffId());
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getRole());
            pstmt.setInt(5, user.getUserId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sadece şifre günceller
     */
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE USER_ACCOUNT SET PASSWORD_HASH = ? WHERE USER_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPasswordHash);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kullanıcı siler
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM USER_ACCOUNT WHERE USER_ID = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Kullanıcı adının mevcut olup olmadığını kontrol eder
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM USER_ACCOUNT WHERE USERNAME = ?";

        try ( Connection conn = DBConnection.getConnection();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
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
     * ResultSet'ten UserAccount objesi oluşturur
     */
    private UserAccount extractUserFromResultSet(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setUserId(rs.getInt("USER_ID"));

        int staffId = rs.getInt("STAFF_ID");
        if (!rs.wasNull()) {
            user.setStaffId(staffId);
        }

        user.setUsername(rs.getString("USERNAME"));
        user.setPasswordHash(rs.getString("PASSWORD_HASH"));
        user.setRole(rs.getString("ROLE"));

        return user;
    }
}
