package com.pertemuan4.praktikum4.dao;

import com.pertemuan4.praktikum4.entity.Category;
import com.pertemuan4.praktikum4.util.HiberUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CategoryDao implements DaoInterface<Category> {


    @Override
    public List<Category> getData() {

        List listCategory;

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();

        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery(Category.class);
        query.from(Category.class);
        listCategory = s.createQuery(query).getResultList();

        s.close();
        return listCategory;

    }

    @Override
    public void addData(Category data) {

        SessionFactory sf = HiberUtil.getSession();
        Session s = sf.openSession();
        s.save(data);
        s.close();

    }

    @Override
    public void delData(Category data) {

    }

    @Override
    public void upData(Category data) {

    }
}
