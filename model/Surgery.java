package model;

import java.util.Date;

public class Surgery {

    private int surgeryId;
    private int patientId;
    private int roomId;
    private Date surgeryDate;
    private String surgeryType;
    private String status;
    private String notes;
    private String displayText;

    public Surgery() {
    }

    public Surgery(int surgeryId, int patientId, int roomId, Date surgeryDate,
            String surgeryType, String status, String notes) {
        this.surgeryId = surgeryId;
        this.patientId = patientId;
        this.roomId = roomId;
        this.surgeryDate = surgeryDate;
        this.surgeryType = surgeryType;
        this.status = status;
        this.notes = notes;
    }

    @Override
    public String toString() {
        if (displayText != null && !displayText.isEmpty()) {
            return displayText;
        }
        return "Surgery #" + surgeryId + " - " + surgeryType;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public int getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(int surgeryId) {
        this.surgeryId = surgeryId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(Date surgeryDate) {
        this.surgeryDate = surgeryDate;
    }

    public String getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(String surgeryType) {
        this.surgeryType = surgeryType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
