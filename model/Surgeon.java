/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */

public class Surgeon {

    private int surgeonId;
    private int staffId;
    private String licenseNo;
    private int yearsOfExperience;

    public Surgeon() {}

    public Surgeon(int surgeonId, int staffId, String licenseNo, int yearsOfExperience) {
        this.surgeonId = surgeonId;
        this.staffId = staffId;
        this.licenseNo = licenseNo;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(int surgeonId) {
        this.surgeonId = surgeonId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return "Surgeon{" +
                "surgeonId=" + surgeonId +
                ", staffId=" + staffId +
                ", licenseNo='" + licenseNo + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}

