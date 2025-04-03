package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

import java.io.IOException;
import java.util.UUID;
import java.time.LocalTime;

import static ru.vsu.amm.java.services.Logg.logger;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");
        String userIdParam = request.getParameter("userId");
        UUID userId;

        logger.info("");
        try {
            userId = UUID.fromString(userIdParam);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Ошибка: некорректный ID пользователя.");
            request.getRequestDispatcher("index").forward(request, response);
            return;
        }

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedAt(LocalTime.now());

        PostRepository postRepository = new PostRepository();
        postRepository.create(post);

        request.setAttribute("success", "Пост успешно создан!");
        response.sendRedirect("index");
    }
}
