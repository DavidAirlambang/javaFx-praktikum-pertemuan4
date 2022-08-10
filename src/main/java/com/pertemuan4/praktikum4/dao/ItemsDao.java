package com.pertemuan4.praktikum4.dao;

import com.pertemuan4.praktikum4.entity.Items;
import com.pertemuan4.praktikum4.util.HiberUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemsDao implements DaoInterface<Items>{


    @Override
    public List<Items> getData() {

        List listItems;

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();

        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(Items.class);
        query.from(Items.class);
        listItems = s.createQuery(query).getResultList();

        s.close();
        return listItems;

    }

    @Override
    public void addData(Items data) {

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();
        s.save(data);
        s.close();

    }

    @Override
    public void delData(Items data) {

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();
        try {
            s.delete(data);
            transaction.commit();
        } catch(Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        s.close();

    }

    @Override
    public void upData(Items data) {

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();
        try {
            s.update(data);
            transaction.commit();
        } catch(Exception e) {
            System.out.println(e);
            transaction.rollback();
        }
        s.close();

    }
}

