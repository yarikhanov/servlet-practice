package com.example.servletpractice.repository.impl;

import com.example.servletpractice.entity.File;
import com.example.servletpractice.repository.FileRepo;
import com.example.servletpractice.util.HiberUtil;
import org.hibernate.Session;

import java.util.List;

public class FileRepoImpl implements FileRepo {
    @Override
    public File getById(Integer id) {
        try(Session session = HiberUtil.getSession()){
            File byId = session
                    .createQuery("select f from File f where f.id = :id", File.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (byId == null){
                System.err.println("File not found");
            }

            return byId;
        }
    }

    @Override
    public List<File> getAll() {
        try(Session session = HiberUtil.getSession()){
            return session
                    .createQuery("select f from File f", File.class)
                    .getResultList();
        }
    }

    @Override
    public File save(File entity) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

            return entity;
        }
    }

    @Override
    public File update(File entity) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();

            File byId = getById(entity.getId());
            if (byId == null){
                System.err.println("File not found");
                return null;
            }
            session.merge(entity);
            session.getTransaction().commit();

            return entity;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();

            File byId = getById(id);
            if (byId == null){
                System.err.println("File not found");
                return;
            }

            session.remove(byId);
            session.getTransaction().commit();
        }
    }
}
