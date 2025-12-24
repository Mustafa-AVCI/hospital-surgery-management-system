package model;

public class Staff {

    private int staffId;
    private String firstName;
    private String lastName;
    private int roleId;
    private int departmentId;
    private String phone;
    private String email;
    private boolean active;

    public Staff() {
    }

    public Staff(int staffId, String firstName, String lastName,
            int roleId, int departmentId,
            String phone, String email, boolean active) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.departmentId = departmentId;
        this.phone = phone;
        this.email = email;
        this.active = active;
    }

    // ✅ ComboBox ve Table için
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    // ===== getters / setters =====
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
