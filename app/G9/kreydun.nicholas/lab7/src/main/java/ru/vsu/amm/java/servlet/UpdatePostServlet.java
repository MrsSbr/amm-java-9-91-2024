package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.PostRepository;
import ru.vsu.amm.java.services.PostService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/updatePost")
public class UpdatePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute("error", "Вы должны войти в систему.");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UUID postId = UUID.fromString(request.getParameter("postId"));
            String newContent = request.getParameter("content");

            PostRepository postRepository = new PostRepository();
            PostService postService = new PostService(postRepository);
            Post existingPost = postService.getById(postId);

            if (existingPost == null) {
                session.setAttribute("error", "Пост не найден.");
                response.sendRedirect("index");
                return;
            }

            // Проверяем, принадлежит ли пост пользователю
            if (!existingPost.getUserId().equals(user.getId())) {
                session.setAttribute("error", "Вы не можете редактировать чужой пост.");
                response.sendRedirect("index");
                return;
            }

            existingPost.setContent(newContent);
            boolean isUpdated = postRepository.update(existingPost);

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
