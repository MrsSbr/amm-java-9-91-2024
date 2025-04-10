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
import java.util.List;

@WebServlet("/index")
public class UserPostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            PostRepository postRepository = new PostRepository();
            PostService postService = new PostService(postRepository);
            List<Post> userPosts = postService.getPostsByUserId(user.getId());
            request.setAttribute("posts", userPosts);
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

