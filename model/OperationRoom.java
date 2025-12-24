/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */

public class OperationRoom {
    private int roomId;
    private String roomCode;
    private int departmentId;
    private int capacity;
    private String status;

    public OperationRoom() {}

    public OperationRoom(int roomId, String roomCode, int departmentId, int capacity, String status) {
        this.roomId = roomId;
        this.roomCode = roomCode;
        this.departmentId = departmentId;
        this.capacity = capacity;
        this.status = status;
    }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return roomCode;
    }
}
