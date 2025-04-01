package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.Redirection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/sign_in")
public class SignInServlet extends HttpServlet {

    private UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        Optional<User> user = userRepository.getByLoginAndPassword(
                request.getParameter("login"),
                request.getParameter("password"));

        if (user.isPresent()) {

            session.setAttribute("user", user.get());

            String redirect = Redirection.redirectBasedOnRole(user.get());

            response.sendRedirect(redirect);

        } else {

            response.sendRedirect("signIn.jsp?error=Invalid login or password!");

        }
    }
}
