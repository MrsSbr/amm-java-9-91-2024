package ru.vsu.amm.java.filters;

import lombok.extern.slf4j.Slf4j;

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
import java.util.Set;

@Slf4j
@WebFilter("/*")
public class AccessControlFilter implements Filter {

    private static final Set<String> UNRESTRICTED_URLS = Set.of(
            "/login",
            "/register",
            "/login.jsp",
            "/register.jsp",
            "/index.jsp"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI().substring(req.getContextPath().length());

        if (isOpenForAll(uri) || isLoggedIn(req.getSession(false))) {
            log.info("User {} passed filter", sessionId(req));
            chain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("User {} blocked by filter", sessionId(req));
            res.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    private boolean isOpenForAll(String uri) {
        for (String openPath : UNRESTRICTED_URLS) {
            if (uri.startsWith(openPath)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute("email") != null;
    }

    private String sessionId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null ? session.getId() : "anonymous";
    }
}
