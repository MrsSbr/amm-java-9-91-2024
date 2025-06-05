package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.repository.CourseRepository;
import ru.vsu.amm.java.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = {"/","/home"})
public class MainServlet extends HttpServlet {
    private final CourseRepository courseRepository = new CourseRepository();
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("courses", courseRepository.getAll());
        req.setAttribute("usersCount", userRepository.getAll().size());
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
