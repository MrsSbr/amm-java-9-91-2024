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

@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
    private PostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        UUID postId = UUID.fromString(request.getParameter("postId"));

        boolean isDeleted = postService.delete(postId);

        if (isDeleted) {
            session.setAttribute("success", "Пост успешно удален.");
        } else {
            session.setAttribute("error", "Ошибка при удалении поста.");
        }

        response.sendRedirect("index");
    }
}

