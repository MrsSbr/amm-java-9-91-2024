package ru.vsu.amm.java.filter;

import ru.vsu.amm.java.entities.Customer;

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
import java.util.logging.Logger;

@WebFilter(filterName = "AuthentificationFilter", urlPatterns = "/*")
public class AuthentificationFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthentificationFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("start doFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession httpSession = req.getSession(false);

        String loginUrl = req.getContextPath() + "/login";
        String registerUrl = req.getContextPath() + "/register";
        String addToyUrl = req.getContextPath() + "/addToy";
        String deleteToyUrl = req.getContextPath() + "/deleteToy";

        boolean loggedIn = httpSession != null && httpSession.getAttribute("customer") != null;
        boolean loginRequest = req.getRequestURI().equals(loginUrl) || req.getRequestURI().endsWith("login.jsp");
        boolean registerRequest = req.getRequestURI().equals(registerUrl) || req.getRequestURI().endsWith("register.jsp");

        // Check if the user is trying to add/delete toys
        boolean addToyRequest = req.getRequestURI().equals(addToyUrl);
        boolean deleteToyRequest = req.getRequestURI().equals(deleteToyUrl);
        boolean isAdmin = false;

        if (loggedIn) {
            Customer customer = (Customer) httpSession.getAttribute("customer");
            if ("admin".equals(customer.getName())) {
                isAdmin = true;
            }
        }

        if (loginRequest || registerRequest || loggedIn || (addToyRequest && isAdmin) || (deleteToyRequest && isAdmin)) {
            log.info("filter passed!");
            chain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
