package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.time.LocalTime;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = request.getParameter("content");
        UUID userId = UUID.fromString(request.getParameter("userId"));

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedAt(LocalTime.now());

        PostRepository postRepository = new PostRepository();
        postRepository.create(post);

        response.sendRedirect("postCreated.jsp");
    }
}