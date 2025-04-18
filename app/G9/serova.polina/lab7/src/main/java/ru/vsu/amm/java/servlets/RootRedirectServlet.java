package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.UserEntity;
import java.io.IOException;

@WebServlet("/")
public class RootRedirectServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RootRedirectServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String ipAddress = request.getRemoteAddr();

        if (session != null && session.getAttribute("user") != null) {
            UserEntity user = (UserEntity) session.getAttribute("user");
            logger.debug("Перенаправление аутентифицированного пользователя {} (ID: {}, Роль: {}) на /protected/home (IP: {})",
                    user.getEmail(), user.getId(), user.getRole(), ipAddress);
            response.sendRedirect(request.getContextPath() + "/protected/home");
        } else {
            logger.debug("Перенаправление неаутентифицированного пользователя на страницу входа (IP: {})", ipAddress);
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}