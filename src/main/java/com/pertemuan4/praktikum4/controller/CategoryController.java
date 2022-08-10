package com.pertemuan4.praktikum4.controller;

import com.pertemuan4.praktikum4.dao.CategoryDao;
import com.pertemuan4.praktikum4.entity.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CategoryController {
    public TextField categoryId;
    public TextField categoryName;
    public Button categorySave;
    public TableView tableCategory;
    public TableColumn colCategoryId;
    public TableColumn colCategoryName;

    private ObservableList<Category> categories;

    public void initialize(){

        CategoryDao categoryDao = new CategoryDao();
        categories = FXCollections.observableArrayList(categoryDao.getData());
        tableCategory.setItems(categories);
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    public void categorySave(ActionEvent actionEvent) {

        if (categoryId.getText().isEmpty() || categoryName.getText().isEmpty()){

            Alert gkIsi = new Alert(Alert.AlertType.INFORMATION);
            gkIsi.setTitle("Information");
            gkIsi.setHeaderText("Isi Semua Field!");
            gkIsi.setContentText("Tolong isi semua field yang ada");
            gkIsi.showAndWait();

        } else {

            CategoryDao categoryDao = new CategoryDao();
            Category category = new Category();
            category.setId(Integer.parseInt(categoryId.getText()));
            category.setName(categoryName.getText());
            categoryDao.addData(category);

            categories = FXCollections.observableArrayList(categoryDao.getData());
            tableCategory.setItems(categories);
            colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
            categoryId.setText("");
            categoryName.setText("");

        }

    }
}
