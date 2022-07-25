package com.pertemuan4.praktikum4.dao;

import com.pertemuan4.praktikum4.entity.Category;
import com.pertemuan4.praktikum4.entity.Items;
import com.pertemuan4.praktikum4.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsDao implements DaoInterface<Items>{
    @Override
    public ObservableList<Items> getData() {

        ObservableList<Items> listItems = FXCollections.observableArrayList();
        Connection conn = MyConnection.getConnection();
        String query = "SELECT * FROM items join category on items.category_id = category.id";
        PreparedStatement ps;

        try {
            ps = conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("items.id");
                String name = result.getString("items.name");
                double price = result.getDouble("price");
                String description = result.getString("description");

                int catId = result.getInt("category.id");
                String catName = result.getString("category.name");
                Category category = new Category(catId,catName);

                Items items = new Items(id,name,price,description,category);
                listItems.add(items);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listItems;
    }

    @Override
    public void addData(Items data) {

        Connection conn = MyConnection.getConnection();
        String query = "insert into items values (?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(data.getId()));
            ps.setString(2,data.getName());
            ps.setString(3, String.valueOf(data.getPrice()));
            ps.setString(4,data.getDescription());
            ps.setString(5, String.valueOf(data.getCategory().getId()));
            int result = ps.executeUpdate();
            if(result > 0) {
                System.out.println("berhasil menambahkan");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delData(Items data) {

        Connection conn = MyConnection.getConnection();
        String query = "delete from items where id = ?";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, data.getId());
            int result = ps.executeUpdate();
            if(result > 0) {
                System.out.println("berhasil delete");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void upData(Items data) {

        Connection conn = MyConnection.getConnection();
        String query = "update items set name=?,price=?," +
                "description=?,category_id=? where id=? ;";
        PreparedStatement ps;
        try {

//            conn.setAutoCommit(false);

            ps = conn.prepareStatement(query);
            ps.setString(1,data.getName());
            ps.setDouble(2,data.getPrice());
            ps.setString(3,data.getDescription());
            ps.setInt(4,data.getCategory().getId());
            ps.setInt(5, data.getId());
            int result = ps.executeUpdate();
            if(result > 0) {

//                conn.commit();

                System.out.println("berhasil update");
            }

//            else {
//                conn.rollback();
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
