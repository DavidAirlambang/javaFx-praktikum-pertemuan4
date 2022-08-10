package com.pertemuan4.praktikum4.dao;

import javafx.collections.ObservableList;

import java.util.List;

public interface DaoInterface<T> {

    public List<T> getData();
    void addData(T data);
    void delData(T data);
    void upData(T data);

}
