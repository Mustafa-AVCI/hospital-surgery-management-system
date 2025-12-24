/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */

public class SurgeryStaff {

    private int surgeryId;
    private int staffId;
    private String assignedRole;

    public SurgeryStaff() {}

    public SurgeryStaff(int surgeryId, int staffId, String assignedRole) {
        this.surgeryId = surgeryId;
        this.staffId = staffId;
        this.assignedRole = assignedRole;
    }

    public int getSurgeryId() { return surgeryId; }
    public void setSurgeryId(int surgeryId) { this.surgeryId = surgeryId; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }

    public String getAssignedRole() { return assignedRole; }
    public void setAssignedRole(String assignedRole) { this.assignedRole = assignedRole; }
}

