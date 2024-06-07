package com.example.servletpractice.controller;

import com.example.servletpractice.entity.Event;
import com.example.servletpractice.repository.impl.EventRepoImpl;
import com.example.servletpractice.service.EventService;
import com.example.servletpractice.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/events/*")
public class EventController extends HttpServlet {

    private final EventService eventService = new EventService(new EventRepoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        try {
            if (path.isEmpty()) {
                List<Event> eventList = eventService.getAllEvents();
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.toJson(resp, eventList);
            } else {
                findEventById(resp, path);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Event event = JsonUtil.readJson(req, Event.class);
            if (event != null) {
                Event addEvent = eventService.addEvent(event);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.toJson(resp, addEvent);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Event event = JsonUtil.readJson(req, Event.class);
            Event modifyEvent = eventService.modifyEvent(event);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, modifyEvent);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        Integer id = Integer.parseInt(pathParts[1]);
        eventService.removeEvent(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    private void findEventById(HttpServletResponse resp, String path) {
        try {
            String[] pathParts = path.split("/");
            Integer id = Integer.parseInt(pathParts[1]);
            Event event = eventService.getEventById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, event);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
