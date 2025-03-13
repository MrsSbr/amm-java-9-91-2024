package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.services.Logg.logger;

@WebServlet("/listUsers")
public class ListUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getAll();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
        logger.info("get list users " + ListUsersServlet.class.getName());
    }
}
