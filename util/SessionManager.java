/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Mustafa AVCI
 */
import model.UserAccount;


public class SessionManager {

    private static SessionManager instance;
    private UserAccount currentUser;
    private long loginTime;

    // Private constructor - Singleton
    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(UserAccount user) {
        this.currentUser = user;
        this.loginTime = System.currentTimeMillis();
    }

    public void logout() {
        this.currentUser = null;
        this.loginTime = 0;
    }

  
    public UserAccount getCurrentUser() {
        return currentUser;
    }

    
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;
    }

 
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : "Guest";
    }

    
    public boolean hasRole(String role) {
        return currentUser != null && role.equals(currentUser.getRole());
    }

    
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

  
    public boolean isDoctor() {
        return hasRole("DOCTOR");
    }

   
    public boolean isNurse() {
        return hasRole("NURSE");
    }

    
    public boolean isStorageManager() {
        return hasRole("STORAGE_MANAGER");
    }

    public long getSessionDuration() {
        if (!isLoggedIn()) {
            return 0;
        }
        return (System.currentTimeMillis() - loginTime) / 1000;
    }

   
    public String getSessionDurationString() {
        long seconds = getSessionDuration();
        long minutes = seconds / 60;
        long hours = minutes / 60;

        if (hours > 0) {
            return hours + " saat " + (minutes % 60) + " dakika";
        } else if (minutes > 0) {
            return minutes + " dakika";
        } else {
            return seconds + " saniye";
        }
    }
}
