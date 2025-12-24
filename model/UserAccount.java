/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */
public class UserAccount {

    private int userId;
    private Integer staffId;  // Nullable - admin kullanıcılar için staff olmayabilir
    private String username;
    private String passwordHash;
    private String role;  // ADMIN, DOCTOR, NURSE, STORAGE_MANAGER, VIEWER

    // Constructors
    public UserAccount() {
    }

    public UserAccount(int userId, Integer staffId, String username,
            String passwordHash, String role) {
        this.userId = userId;
        this.staffId = staffId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    private String password; // sadece GUI → DAO arası

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserAccount{"
                + "userId=" + userId
                + ", staffId=" + staffId
                + ", username='" + username + '\''
                + ", role='" + role + '\''
                + '}';
    }
}
