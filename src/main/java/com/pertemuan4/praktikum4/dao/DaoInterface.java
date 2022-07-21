package com.pertemuan4.praktikum4.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {

    public ObservableList<T> getData();
    void addData(T data);
//    void delData(T data);

}
