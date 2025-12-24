/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Mustafa AVCI
 */

import model.MaterialCategory;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialCategoryDAO {

    // INSERT
    public boolean insert(MaterialCategory c) {
        String sql = "INSERT INTO MATERIAL_CATEGORY (NAME) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("MaterialCategory insert hata!");
            e.printStackTrace();
            return false;
        }
    }

    // LIST ALL
    public List<MaterialCategory> getAll() {
        List<MaterialCategory> list = new ArrayList<>();
        String sql = "SELECT CATEGORY_ID, NAME FROM MATERIAL_CATEGORY";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                MaterialCategory mc = new MaterialCategory(
                    rs.getInt("CATEGORY_ID"),
                    rs.getString("NAME")
                );
                list.add(mc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

