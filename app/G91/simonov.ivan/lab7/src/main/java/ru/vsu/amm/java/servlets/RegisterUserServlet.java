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

@WebServlet("/register_user")
public class RegisterUserServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapRequestToObject(request);

        try {

            userRepository.save(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirect = Redirection.redirectBasedOnRole(user);

            // доп сообщение

            response.sendRedirect(redirect);

        } catch (RuntimeException e) {

            // ошибка

        }
    }
}
