/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */
public class SurgeryMaterial {

    private int surgeryId;
    private int materialId;
    private int quantityUsed;

    public SurgeryMaterial() {
    }

    public SurgeryMaterial(int surgeryId, int materialId, int quantityUsed) {
        this.surgeryId = surgeryId;
        this.materialId = materialId;
        this.quantityUsed = quantityUsed;
    }

    public int getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(int surgeryId) {
        this.surgeryId = surgeryId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(int quantityUsed) {
        this.quantityUsed = quantityUsed;
    }
}
