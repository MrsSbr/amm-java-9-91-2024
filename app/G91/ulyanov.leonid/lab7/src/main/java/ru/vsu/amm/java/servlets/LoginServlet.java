package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.services.AuthService;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;

    public LoginServlet() {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String successPath = "read";
        String failurePath = "/login.jsp";
        try {
            LoginRequest loginRequest = new LoginRequest(
                    request.getParameter("email"),
                    request.getParameter("password")
            );

            User user = authService.login(loginRequest);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            String redirectUrl = request.getContextPath()
                                 + successPath + "?message="
                                 + URLEncoder.encode("Вы успешно вошли!", StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (AuthenticationException e) {
            String redirectUrl = request.getContextPath()
                                 + failurePath
                                 + String.format("?error=%s", e.getMessage());
            response.sendRedirect(redirectUrl);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
