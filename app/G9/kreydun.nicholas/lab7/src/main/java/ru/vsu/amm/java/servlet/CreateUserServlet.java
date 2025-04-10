package ru.vsu.amm.java.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.services.UserService;

import java.io.IOException;
import java.util.UUID;
import java.time.LocalTime;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

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
        UserService userService = new UserService(userRepository);

        UUID resultId = userService.create(user);

        if (resultId != null) {
            response.sendRedirect("userCreated.jsp");
        } else {
            session.setAttribute("error", "Такой пользователь уже есть!");
            response.sendRedirect("register.jsp");
        }
    }
}
