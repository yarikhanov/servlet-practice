package com.example.servletpractice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void toJson(HttpServletResponse response, Object object)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), object);
    }

    public static <T> T readJson(HttpServletRequest request, Class<T> clazz)
            throws IOException {
        return objectMapper.readValue(request.getInputStream(), clazz);
    }
}
