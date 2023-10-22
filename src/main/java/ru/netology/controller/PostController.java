package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        if (data.isEmpty()) {
            response.getWriter().println("List is empty, there are no posts yet");
        } else {
            response.getWriter().print(gson.toJson(data));
        }
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var data = service.getById(id);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException ex) {
            response.getWriter().println(ex);
        }
        // TODO: deserialize request & serialize response
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException ex) {
            response.getWriter().println(ex);
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            service.removeById(id);
            response.getWriter().println("Пост " + id + " удален");
        } catch (NotFoundException ex) {
            response.getWriter().println(ex);
        }
        // TODO: deserialize request & serialize response
    }
}