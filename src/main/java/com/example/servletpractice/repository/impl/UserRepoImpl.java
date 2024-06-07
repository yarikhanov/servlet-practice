package com.example.servletpractice.repository.impl;

import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.UserRepo;
import com.example.servletpractice.util.HiberUtil;
import org.hibernate.Session;

import java.util.List;

public class UserRepoImpl implements UserRepo {
    @Override
    public User getById(Integer id) {
        try (Session session = HiberUtil.getSession()) {
            User user =
                    session.createQuery(
                            "SELECT u from User u left join fetch u.events where u.id = :id", User.class)
                            .setParameter("id", id)
                            .uniqueResult();

            if (user == null){
                System.err.println("User not found");
            }

            return user;
        }
    }

    @Override
    public List<User> getAll() {
        try(Session session = HiberUtil.getSession()){
            return session.createQuery("SELECT u FROM User u left join u.events", User.class)
                            .getResultList();
        }
    }

    @Override
    public User save(User user) {
        try (Session session = HiberUtil.getSession()){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();

            return user;
        }
    }

    @Override
    public User update(User user) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();
            User byId = getById(user.getId());
            if (byId == null){
                System.err.println("user not found");
                return null;
            }

            session.merge(user);
            session.getTransaction().commit();

            return user;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();
            User byId = getById(id);
            if (byId == null){
                System.err.println("user not found");
                return;
            }
            session.remove(byId);
            session.getTransaction().commit();
        }
    }
}
