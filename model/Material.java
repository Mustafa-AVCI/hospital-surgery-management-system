/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Mustafa AVCI
 */
public class Material {

    private int materialId;
    private int categoryId;
    private String name;
    private String unit;
    private int criticalStockLevel;
    private String categoryName;  // ← BU SATIR VAR MI?

    // Constructors
    public Material() {
    }

    public Material(int materialId, int categoryId, String name,
            String unit, int criticalStockLevel) {
        this.materialId = materialId;
        this.categoryId = categoryId;
        this.name = name;
        this.unit = unit;
        this.criticalStockLevel = criticalStockLevel;
    }

    // Getters and Setters
    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCriticalStockLevel() {
        return criticalStockLevel;
    }

    public void setCriticalStockLevel(int criticalStockLevel) {
        this.criticalStockLevel = criticalStockLevel;
    }

    // ← BUNLAR VAR MI KONTROL ET ↓
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return name;
    }
}
