package com.example.servletpractice.service;

import com.example.servletpractice.entity.Event;
import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.EventRepo;

import java.util.List;

public class EventService {

    private EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public Event getEventById(Integer id) {
        return eventRepo.getById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepo.getAll();
    }

    public Event addEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event modifyEvent(Event event) {
        return eventRepo.save(event);
    }

    public void removeEvent(Integer id) {
        eventRepo.deleteById(id);
    }
}
