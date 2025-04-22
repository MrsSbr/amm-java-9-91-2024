package ru.vsu.amm.java.presentation.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.data.service.AuthService;
import ru.vsu.amm.java.domain.entities.Player;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;

    private static final Logger log = Logger.getLogger(LoginServlet.class.getName());


    public LoginServlet() {
        this.authService = new AuthService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Player player = authService.login(login, password);

        if (player != null) {
            log.info("Пользователь найден");
            HttpSession session = req.getSession();
            session.setAttribute("player", player);

            resp.sendRedirect("/laba7/game");
        }
        else {
            req.setAttribute("error", "Пользователь с таким логином не найден");
            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
        }

    }
}
