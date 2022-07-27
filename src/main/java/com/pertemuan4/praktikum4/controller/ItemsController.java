package com.pertemuan4.praktikum4.controller;

import com.pertemuan4.praktikum4.HelloApplication;
import com.pertemuan4.praktikum4.dao.CategoryDao;
import com.pertemuan4.praktikum4.dao.ItemsDao;
import com.pertemuan4.praktikum4.entity.Category;
import com.pertemuan4.praktikum4.entity.Items;
import com.pertemuan4.praktikum4.util.MyConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ItemsController {
    public MenuItem goCategory;
    public MenuItem close;
    public TextField itemsId;
    public TextField itemsName;
    public TextField itemsPrice;
    public TextArea itemsDesc;
    public ComboBox<Category> itemsCategory;
    public Button saveItems;
    public Button resetItems;
    public Button updateItems;
    public Button deleteItems;
    public TableView<Items> tableItems;
    public TableColumn colItemsId;
    public TableColumn colItemsName;
    public TableColumn colItemsPrice;
    public TableColumn colItemsCategory;
    public MenuItem simpleReport;
    public MenuItem categoryReport;

    private ObservableList<Items> items;
    private ObservableList<Category> categories;

    public void initialize(){

        goCategory.setAccelerator(KeyCombination.keyCombination("Alt+C"));
        close.setAccelerator(KeyCombination.keyCombination("Alt+X"));
        simpleReport.setAccelerator(KeyCombination.keyCombination("Alt+S"));
        categoryReport.setAccelerator(KeyCombination.keyCombination("Alt+G"));

        CategoryDao categoryDao = new CategoryDao();
        categories = categoryDao.getData();
        itemsCategory.setItems(categories);

        ItemsDao itemsDao = new ItemsDao();
        items = itemsDao.getData();
        tableItems.setItems(items);
        colItemsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colItemsCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    public void goCategory(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(itemsId.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.showAndWait();

        CategoryDao categoryDao = new CategoryDao();
        categories = categoryDao.getData();
        itemsCategory.setItems(categories);
    }

    public void close(ActionEvent actionEvent) {
        itemsId.getScene().getWindow().hide();
    }

    public void saveItems(ActionEvent actionEvent) {

        if(itemsId.getText().isEmpty() || itemsName.getText().isEmpty() || itemsPrice.getText().isEmpty() ||
        itemsDesc.getText().isEmpty() || itemsCategory.getValue()==null){
            Alert gkIsi = new Alert(Alert.AlertType.INFORMATION);
            gkIsi.setTitle("Information");
            gkIsi.setHeaderText("Isi Semua Field!");
            gkIsi.setContentText("Tolong isi semua field yang ada");
            gkIsi.showAndWait();
        } else {
            ItemsDao itemsDao = new ItemsDao();
            itemsDao.addData(new Items(Integer.parseInt(itemsId.getText()), itemsName.getText(),
                    Double.parseDouble(itemsPrice.getText()), itemsDesc.getText(), itemsCategory.getValue()));
            items = itemsDao.getData();
            tableItems.setItems(items);
            colItemsId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colItemsName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colItemsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colItemsCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            resetItems();
        }
    }

    public void itemsSelected(MouseEvent mouseEvent) {

        if(!tableItems.getSelectionModel().getSelectedCells().isEmpty()){
            saveItems.setDisable(true);
            resetItems.setDisable(true);
            updateItems.setDisable(false);
            deleteItems.setDisable(false);
            itemsId.setDisable(true);
        }
        itemsId.setText(String.valueOf(tableItems.getSelectionModel().getSelectedItem().getId()));
        itemsName.setText(tableItems.getSelectionModel().getSelectedItem().getName());
        itemsPrice.setText(String.valueOf(tableItems.getSelectionModel().getSelectedItem().getPrice()));
        itemsDesc.setText(tableItems.getSelectionModel().getSelectedItem().getDescription());
        itemsCategory.valueProperty().setValue(tableItems.getSelectionModel().getSelectedItem().getCategory());

    }

    public void resetItems() {
        itemsId.setText("");
        itemsName.setText("");
        itemsPrice.setText("");
        itemsDesc.setText("");
        itemsCategory.valueProperty().setValue(null);

        saveItems.setDisable(false);
        resetItems.setDisable(false);
        updateItems.setDisable(true);
        deleteItems.setDisable(true);
        itemsId.setDisable(false);
    }

    public void deleteItems(ActionEvent actionEvent) {

        Items selected;
        selected = tableItems.getSelectionModel().getSelectedItem();

        ItemsDao transaksiDao = new ItemsDao();
        transaksiDao.delData(selected);

        items = transaksiDao.getData();
        tableItems.setItems(items);
        resetItems();

    }

    public void updateItems(ActionEvent actionEvent) {

        Items selected;
        selected = tableItems.getSelectionModel().getSelectedItem();

        selected.setName(itemsName.getText());
        selected.setPrice(Double.parseDouble(itemsPrice.getText()));
        selected.setDescription(itemsDesc.getText());
        selected.setCategory(itemsCategory.getValue());

        ItemsDao transaksiDao = new ItemsDao();
        transaksiDao.upData(selected);

        items = transaksiDao.getData();
        tableItems.setItems(items);
        resetItems();

    }

    public void simpleReport(ActionEvent actionEvent) {

        JasperPrint jp;
        Connection conn = MyConnection.getConnection();
        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("reports/ReportAll.jasper",param,conn);
            JasperViewer viewer = new JasperViewer(jp,false);
            viewer.setTitle("All Report");
            viewer.setVisible(true);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    public void categoryReport(ActionEvent actionEvent) {

        JasperPrint jp;
        Connection conn = MyConnection.getConnection();
        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("reports/ReportGroup.jasper",param,conn);
            JasperViewer viewer = new JasperViewer(jp,false);
            viewer.setTitle("Group Report");
            viewer.setVisible(true);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }
}
