package ru.vsu.amm.java.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String ip = request.getRemoteAddr();
        if (session != null) {
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user != null) {
                session.removeAttribute("user");
                session.removeAttribute("isReferee");
                logger.log(Level.FINE, MessageFormat.format(
                        "Успешный logout для пользователя с nickname={0}, ip={1}",
                        ip,
                        user.getNickname())
                );
            }
        } else {
            logger.log(Level.WARNING, MessageFormat.format("Попытка logout неавторизованным пользователем с ip={0}", ip));
        }
        response.sendRedirect("/leaderboard");
    }
}
