package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;
import ru.vsu.amm.java.services.AuthService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final AuthService authService;

    public RegisterServlet() {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String successPath = "read";
        String failurePath = "/register.jsp";
        try {
            RegisterRequest registerRequest = new RegisterRequest(
                    request.getParameter("email"),
                    request.getParameter("password"),
                    request.getParameter("lastName"),
                    request.getParameter("firstName"),
                    request.getParameter("patronymic"),
                    request.getParameter("phoneNumber")
            );

            User user = authService.register(registerRequest);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            String redirectUrl = request.getContextPath()
                                 + successPath + "?message="
                                 + URLEncoder.encode("Добро пожаловать!", StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (AuthenticationException e) {
            String redirectUrl = request.getContextPath()
                                 + failurePath
                                 + String.format("?error=%s", e.getMessage());
            response.sendRedirect(redirectUrl);
        }
    }
}
