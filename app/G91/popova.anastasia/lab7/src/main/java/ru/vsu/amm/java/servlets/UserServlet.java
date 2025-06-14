package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.utils.PasswordHasher;
import ru.vsu.amm.java.utils.Validator;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            showCurrentUserProfile(req, resp);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID userId = UUID.fromString(parts[1]);
            getUserById(userId, req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/register")) {
            handleRegister(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID userId = UUID.fromString(parts[1]);
            updateUser(userId, req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID userId = UUID.fromString(parts[1]);
            deleteUser(userId, req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showCurrentUserProfile(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/WEB-INF/views/users/profile.jsp").forward(req, resp);
    }

    private void getUserById(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            User user = userService.getByID(userId);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/users/view.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (name == null || email == null || password == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Все поля обязательны для заполнения!");
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userService.register(name, email, password);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/boards");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
        }
    }

    private void updateUser(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getUserID().equals(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав для выполнения операции");
            return;
        }

        String newName = req.getParameter("name");

        if (newName == null || newName.trim().isEmpty()) {
            req.setAttribute("error", "Имя пользователя не может быть пустым");
            req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
            return;
        }

        try {
            if (!Validator.isValidName(newName)) {
                throw new IllegalArgumentException("Некорректное имя пользователя");
            }

            userService.updateUserName(userId, newName.trim());

            currentUser.setName(newName.trim());
            req.getSession().setAttribute("user", currentUser);

            resp.sendRedirect("/users");
        } catch (IllegalArgumentException e) {
            // ошибки валидации
            req.setAttribute("error", e.getMessage());
            req.setAttribute("currentName", newName);
            req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            // Системные ошибки
            //тут будет логирование
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Произошла ошибка при обновлении данных");
        }
    }


    private void deleteUser(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getUserID().equals(userId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            userService.deleteUser(userId);
            req.getSession().invalidate();
            resp.sendRedirect("/auth/login");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }



}
