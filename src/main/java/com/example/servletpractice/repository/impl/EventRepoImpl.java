package com.example.servletpractice.repository.impl;

import com.example.servletpractice.entity.Event;
import com.example.servletpractice.repository.EventRepo;
import com.example.servletpractice.util.HiberUtil;
import org.hibernate.Session;

import java.util.List;

public class EventRepoImpl implements EventRepo {
    @Override
    public Event getById(Integer id) {
        try(Session session = HiberUtil.getSession()){
            Event event = session
                    .createQuery("select e from Event e left join fetch e.user left join fetch e.file where e.id = :id", Event.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (event == null) {
                System.err.println("Event not found");
            }

            return event;
        }
    }

    @Override
    public List<Event> getAll() {
        try(Session session = HiberUtil.getSession()){
            return session
                    .createQuery("select e from Event e left join fetch e.user left join fetch e.file", Event.class)
                    .getResultList();
        }
    }

    @Override
    public Event save(Event entity) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

            return entity;
        }
    }

    @Override
    public Event update(Event entity) {
        try(Session session = HiberUtil.getSession()){
            session.beginTransaction();
            Event byId = getById(entity.getId());

            if (byId == null){
                System.err.println("Event not found");
                return null;
            }

            session.merge(entity);
            session.getTransaction().commit();

            return entity;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = HiberUtil.getSession()){
            session.beginTransaction();
            Event byId = getById(id);

            if (byId == null){
                System.err.println("Event not found");
                return;
            }

            session.remove(byId);
            session.getTransaction().commit();
        }
    }
}
