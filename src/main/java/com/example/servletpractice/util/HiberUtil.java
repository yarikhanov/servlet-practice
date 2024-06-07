package com.example.servletpractice.util;

import com.example.servletpractice.entity.Event;
import com.example.servletpractice.entity.File;
import com.example.servletpractice.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiberUtil {

    private static SessionFactory sessionfactory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(Event.class)
                    .addAnnotatedClass(File.class)
                    .addAnnotatedClass(User.class);

            sessionfactory = configuration.buildSessionFactory();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static Session getSession(){
        return sessionfactory.openSession();
    }
}
