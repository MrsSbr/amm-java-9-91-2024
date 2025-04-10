package ru.vsu.amm.java.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.vsu.amm.java.utils.LoggerInitializer.initializeLogger;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Logger logger = initializeLogger(
            "app/G91/simonov.ivan/lab7/src/main/java/ru/vsu/amm/java/logs/auth-filter-logs.log",
            AuthFilter.class.getName());

    private static final List<String> PUBLIC_PATHS = List.of(
            "/signIn",
            "/registerUser",
            "/signIn.jsp",
            "/register.jsp",
            "/index.jsp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        boolean isPublicPath = PUBLIC_PATHS.stream()
                .anyMatch(publicPath -> path.equals(publicPath) || path.startsWith(publicPath));

        HttpSession session = httpRequest.getSession(false);
        boolean isSignedIn = session != null && session.getAttribute("user") != null;

        if (isPublicPath || isSignedIn) {

            logger.log(Level.INFO,
                    String.format("Filter passed by user %s!",
                            session == null ? "unknown" : session.getId()));

            chain.doFilter(request, response);

        } else {

            logger.log(Level.INFO,
                    String.format("Filter not passed by %s user!",
                            session == null ? "unknown" : session.getId()));

            httpResponse.sendRedirect(String.format("%s/index.jsp", httpRequest.getContextPath()));

        }
    }

}
