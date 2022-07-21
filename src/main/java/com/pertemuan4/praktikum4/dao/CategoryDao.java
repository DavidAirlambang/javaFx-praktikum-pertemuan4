package com.pertemuan4.praktikum4.dao;

import com.pertemuan4.praktikum4.entity.Category;
import com.pertemuan4.praktikum4.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao implements DaoInterface<Category> {

    @Override
    public ObservableList<Category> getData() {

        ObservableList<Category> listCategory = FXCollections.observableArrayList();
        Connection conn = MyConnection.getConnection();
        String query = "select * from category";
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                Category category = new Category(id, name);
                listCategory.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCategory;
    }

    @Override
    public void addData(Category data) {

        Connection conn = MyConnection.getConnection();
        String query = "insert into category values (?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(data.getId()));
            ps.setString(2,data.getName());
            int result = ps.executeUpdate();
            if(result > 0) {
                System.out.println("berhasil menambahkan");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
