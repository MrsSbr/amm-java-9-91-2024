package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.services.PostService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/index")
public class UserPostsServlet extends HttpServlet {
    private PostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();  // Сервис создается только один раз
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object userIdAttr = session.getAttribute("userId");;

        if (userIdAttr != null) {
            List<Post> userPosts = postService.getPostsByUserId((UUID) userIdAttr);
            request.setAttribute("posts", userPosts);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

