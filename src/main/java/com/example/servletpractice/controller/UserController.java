package com.example.servletpractice.controller;

import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.impl.UserRepoImpl;
import com.example.servletpractice.service.UserService;
import com.example.servletpractice.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService(new UserRepoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        try {
            if (path.isEmpty()) {
                List<User> allUsers = userService.getAllUsers();
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.toJson(resp, allUsers);
            } else {
                findUserById(resp, path);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = JsonUtil.readJson(req, User.class);
            if (user != null) {
                User addUser = userService.addUser(user);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.toJson(resp, addUser);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = JsonUtil.readJson(req, User.class);
            User modifyUser = userService.modifyUser(user);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, modifyUser);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        Integer id = Integer.parseInt(pathParts[1]);
        userService.removeUser(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    private void findUserById(HttpServletResponse resp, String path) {
        try {
            String[] pathParts = path.split("/");
            Integer id = Integer.parseInt(pathParts[1]);
            User userById = userService.getUserById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, userById);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
