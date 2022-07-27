module com.pertemuan4.praktikum4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.pertemuan4.praktikum4 to javafx.fxml;
    opens com.pertemuan4.praktikum4.controller to javafx.fxml;
    opens com.pertemuan4.praktikum4.dao to javafx.fxml;
    opens com.pertemuan4.praktikum4.entity to javafx.fxml;
    opens com.pertemuan4.praktikum4.util to javafx.fxml;
    exports com.pertemuan4.praktikum4;
    exports com.pertemuan4.praktikum4.controller;
    exports com.pertemuan4.praktikum4.dao;
    exports com.pertemuan4.praktikum4.entity;
    exports com.pertemuan4.praktikum4.util;
}