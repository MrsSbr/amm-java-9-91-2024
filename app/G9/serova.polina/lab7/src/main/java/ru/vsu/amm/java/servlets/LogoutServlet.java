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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String ipAddress = request.getRemoteAddr();

        if (session != null) {
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user != null) {
                logger.info("Пользователь {} (ID: {}) вышел из системы (IP: {})",
                        user.getEmail(), user.getId(), ipAddress);
            } else {
                logger.debug("Завершение сессии для анонимного пользователя (IP: {})", ipAddress);
            }
            session.invalidate();
        } else {
            logger.debug("Попытка выхода без активной сессии (IP: {})", ipAddress);
        }

        response.sendRedirect(request.getContextPath() + "/login");
        logger.debug("Перенаправление на страницу входа после выхода (IP: {})", ipAddress);
    }
}