package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.mapper.UserMapper;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.Redirection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            UserMapper userMapper = new UserMapper();
            User user = userMapper.mapRequestToObject(request);

            if (userRepository.getByLoginAndPassword(user.getLogin(), user.getPassword()).isEmpty()) {

                userRepository.save(user);

                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                String redirect = Redirection.redirectBasedOnRole(user);
                response.sendRedirect(redirect);

            } else {

                response.sendRedirect("register.jsp?error=%User already exists!");

            }

        } catch (RuntimeException e) {

            response.sendRedirect(String.format("register.jsp?error=%s", e.getMessage()));

        }
    }
}
