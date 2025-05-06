package ru.vsu.amm.java.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/auth/*")
public class AuthorizedFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorizedFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user != null) {
                logger.log(Level.FINE, MessageFormat.format(
                        "Авторизованный пользователь с id={0} пытается перейти на {1}",
                        user.getId(),
                        httpRequest.getRequestURI()
                ));
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/leaderboard");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
