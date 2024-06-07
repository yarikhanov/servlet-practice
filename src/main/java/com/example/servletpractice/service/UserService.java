package com.example.servletpractice.service;


import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.UserRepo;

import java.util.List;

public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public User getUserById(Integer id) {
        return userRepo.getById(id);
    }

    public List<User> getAllUsers() {
        return userRepo.getAll();
    }

    public User addUser(User user) {
      return userRepo.save(user);
    }

    public User modifyUser(User user) {
        return userRepo.save(user);
    }

    public void removeUser(Integer id) {
        userRepo.deleteById(id);
    }
}
