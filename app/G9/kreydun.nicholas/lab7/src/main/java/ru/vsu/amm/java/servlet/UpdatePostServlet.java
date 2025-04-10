package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.entities.User;
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

            Post existingPost = postService.getById(postId);

            if (existingPost == null) {
                session.setAttribute("error", "Пост не найден.");
                response.sendRedirect("index");
                return;
            }

            if (!existingPost.getUserId().equals(user.getId())) {
                session.setAttribute("error", "Вы не можете редактировать чужой пост.");
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
