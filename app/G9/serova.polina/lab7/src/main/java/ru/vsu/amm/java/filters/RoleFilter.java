package ru.vsu.amm.java.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;

@WebFilter("/protected/doctor/*")
public class RoleFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RoleFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Попытка доступа без аутентификации [URI: {}, IP: {}]",
                    httpReq.getRequestURI(),
                    httpReq.getRemoteAddr());
            httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UserEntity currentUser = (UserEntity) session.getAttribute("user");
        if (currentUser.getRole() != Role.DOCTOR) {
            logger.warn("Отказ в доступе: недостаточно прав [Пользователь: {}, Роль: {}, Требуемая роль: DOCTOR, URI: {}]",
                    currentUser,
                    currentUser.getRole(),
                    httpReq.getRequestURI());
            httpResp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        logger.debug("Доступ разрешен [Пользователь: {}, Роль: DOCTOR, URI: {}]",
                currentUser,
                httpReq.getRequestURI());
        chain.doFilter(request, response);
    }
}