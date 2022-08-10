package com.pertemuan4.praktikum4.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiberUtil {

    public  static SessionFactory getSession(){
     SessionFactory sf = new Configuration().configure().buildSessionFactory();
     return sf;
    }

}
