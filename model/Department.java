/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Department {
    private int departmentId;
    private int hospitalId;
    private String name;

    public Department() {}

    public Department(int departmentId, int hospitalId, String name) {
        this.departmentId = departmentId;
        this.hospitalId = hospitalId;
        this.name = name;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getHospitalId() { return hospitalId; }
    public void setHospitalId(int hospitalId) { this.hospitalId = hospitalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return departmentId + " - " + name;
    }
}

