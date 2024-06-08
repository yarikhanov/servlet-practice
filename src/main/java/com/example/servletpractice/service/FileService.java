package com.example.servletpractice.service;

import com.example.servletpractice.entity.Event;
import com.example.servletpractice.entity.File;
import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.EventRepo;
import com.example.servletpractice.repository.FileRepo;
import com.example.servletpractice.repository.UserRepo;

import java.util.List;

public class FileService {

    private final FileRepo fileRepo;

    private final UserRepo userRepo;

    private final EventRepo eventRepo;

    public FileService(FileRepo fileRepo, UserRepo userRepo, EventRepo eventRepo) {
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public File getFileById(Integer id) {
        return fileRepo.getById(id);
    }

    public List<File> getAllFiles() {
        return fileRepo.getAll();
    }

    public File addFile(File file, Integer userId) {
        File saveFile = fileRepo.save(file);
        Event event = new Event();
        event.setUser(userRepo.getById(userId));
        event.setFile(saveFile);
        eventRepo.save(event);
        return saveFile;
    }

    public File modifyFile(File file) {
        return fileRepo.save(file);
    }

    public void removeFile(Integer id) {
        fileRepo.deleteById(id);
    }
}
