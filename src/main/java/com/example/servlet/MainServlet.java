package com.example.servlet;

import com.example.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String DELETE = "DELETE";
    private static final String PATH = "/api/posts";
    private static final String AND_ID = "/\\d+";
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("com.example");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (method.equals(GET) && path.equals(PATH)) {
                controller.getAllPosts(resp);
                return;
            }
            if (method.equals(GET) && path.matches(PATH + AND_ID)) {
                controller.getById(parseID(path), resp);
                return;
            }
            if (method.equals(POST) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(PATH + AND_ID)) {
                controller.removeById(parseID(path), resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long parseID(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}