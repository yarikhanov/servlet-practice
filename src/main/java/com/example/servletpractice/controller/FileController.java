package com.example.servletpractice.controller;

import com.example.servletpractice.entity.File;
import com.example.servletpractice.repository.impl.FileRepoImpl;
import com.example.servletpractice.service.FileService;
import com.example.servletpractice.util.JsonUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/files/*")
public class FileController extends HttpServlet {

    private final FileService fileService = new FileService(new FileRepoImpl());
    private final static String PATH = "src/main/resources";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        try {
            if (path.isEmpty()) {
                List<File>fileList = fileService.getAllFiles();
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.toJson(resp, fileList);
            } else {
                findFileById(resp, path);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
                Part filePart = req.getPart("file");
                if (filePart != null){
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    Path filePath = Paths.get(PATH);
                    try(InputStream fileContent = filePart.getInputStream();
                        OutputStream outputFile = new FileOutputStream(filePath.toFile())) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileContent.read(buffer)) != -1) {
                            outputFile.write(buffer, 0, bytesRead);
                        }
                        File file = new File();
                        file.setFilePath(filePath.toString());
                        file.setName(fileName);
                        fileService.addFile(file);
                }catch (Exception e){
                        System.err.println(e.getMessage());}
                }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            File file = JsonUtil.readJson(req, File.class);
            File modifyFile = fileService.modifyFile(file);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, modifyFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String[] pathParts = path.split("/");
        Integer id = Integer.parseInt(pathParts[1]);
        fileService.removeFile(id);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    private void findFileById(HttpServletResponse resp, String path) {
        try {
            String[] pathParts = path.split("/");
            Integer id = Integer.parseInt(pathParts[1]);
            File file = fileService.getFileById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            JsonUtil.toJson(resp, file);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
