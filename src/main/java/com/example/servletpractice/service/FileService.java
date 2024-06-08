package com.example.servletpractice.service;

import com.example.servletpractice.entity.File;
import com.example.servletpractice.entity.User;
import com.example.servletpractice.repository.FileRepo;

import java.util.List;

public class FileService {

    private final FileRepo fileRepo;

    public FileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }



    public File getFileById(Integer id) {
        return fileRepo.getById(id);
    }

    public List<File> getAllFiles() {
        return fileRepo.getAll();
    }

    public File addFile(File file) {
        return fileRepo.save(file);
    }

    public File modifyFile(File file) {
        return fileRepo.save(file);
    }

    public void removeFile(Integer id) {
        fileRepo.deleteById(id);
    }
}
