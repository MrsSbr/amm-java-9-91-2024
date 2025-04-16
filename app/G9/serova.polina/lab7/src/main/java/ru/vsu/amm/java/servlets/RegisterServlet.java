package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegisterServlet.class);
    private final UserService userService;

    public RegisterServlet() {
        userService = new UserService();
    }

    public RegisterServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String ipAddress = request.getRemoteAddr();
        String email = request.getParameter("email");
        logger.info("Попытка регистрации нового пользователя с email: {} (IP: {})", email, ipAddress);

        try {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String patronymic = request.getParameter("patronymic");
            LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
            String password = request.getParameter("password");
            Role role = Role.valueOf(request.getParameter("role"));

            if (userService.getUserByEmail(email) != null) {
                logger.warn("Попытка регистрации с уже существующим email: {} (IP: {})", email, ipAddress);
                response.sendRedirect("/register.jsp?error=email_exists");
                return;
            }

            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            userService.addUser(name, surname, patronymic, birthday, email, passwordHash, role);

            logger.info("Успешная регистрация нового пользователя: {} {} {} (email: {}, роль: {})",
                    surname, name, patronymic, email, role);

            response.sendRedirect("/login.jsp?success=1");

        } catch (Exception e) {
            logger.error("Ошибка при регистрации пользователя с email: {} (IP: {}): {}",
                    email, ipAddress, e.getMessage(), e);
            response.sendRedirect("/register.jsp?error=system_error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        logger.debug("Запрос страницы регистрации (IP: {})", request.getRemoteAddr());
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}