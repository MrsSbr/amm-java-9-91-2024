package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.services.PostService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/updatePost")
public class UpdatePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        try {
            UUID postId = UUID.fromString(request.getParameter("postId"));
            String newContent = request.getParameter("content");

            boolean result = postService.getById(postId);

            if (result) {
                session.setAttribute("error", "Пост не найден.");
                response.sendRedirect("index");
                return;
            }

            boolean isUpdated = postService.update(postId, newContent);

            if (isUpdated) {
                session.setAttribute("success", "Пост успешно обновлён.");
            } else {
                session.setAttribute("error", "Ошибка при обновлении поста.");
            }
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", "Некорректный ID поста.");
        }

        response.sendRedirect("index");
    }
}
