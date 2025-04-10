package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.PostRepository;
import ru.vsu.amm.java.services.PostService;

import java.io.IOException;
import java.util.UUID;
@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UUID postId = UUID.fromString(request.getParameter("postId"));;

        PostRepository postRepository = new PostRepository();
        PostService postService = new PostService(postRepository);

        boolean isDeleted = postService.delete(postId);

        if (isDeleted) {
            session.setAttribute("success", "Пост успешно удален.");
        } else {
            session.setAttribute("error", "Ошибка при удалении поста.");
        }

        response.sendRedirect("index");
    }
}
