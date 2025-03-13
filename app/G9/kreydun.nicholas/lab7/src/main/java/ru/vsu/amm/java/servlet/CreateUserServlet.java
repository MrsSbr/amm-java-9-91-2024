package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreatedAt(LocalTime.now());

        UserRepository userRepository = new UserRepository();
        userRepository.create(user);

        response.sendRedirect("userCreated.jsp");
    }
}
