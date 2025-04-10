package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.services.PostService;

import java.io.IOException;
import java.util.UUID;

import static ru.vsu.amm.java.services.Logg.logger;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("content");
        String userIdParam = request.getParameter("userId");

        logger.info("Creating post in servlet");

        UUID postId = postService.create(content, userIdParam);

        if (postId == null) {
            request.setAttribute("error", "Ошибка: некорректный ID пользователя.");
            request.getRequestDispatcher("index").forward(request, response);
        } else {
            request.setAttribute("success", "Пост успешно создан!");
            response.sendRedirect("index");
        }
    }
}
