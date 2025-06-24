package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.utils.Validator;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger((ColumnServlet.class));

    private final UserService userService = new UserService(new UserRepository());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing GET request to /users{}", req.getPathInfo());

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
            log.warn("Invalid GET request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.debug("Processing POST request to /users{}", req.getPathInfo());

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/register")) {
            resp.sendRedirect("/auth/register");
        } else {
            log.warn("Invalid POST request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing PUT request to /users{}", req.getPathInfo());

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            log.warn("Empty PUT request path");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID userId = UUID.fromString(parts[1]);
            updateUser(userId, req, resp);
        } else {
            log.warn("Invalid PUT request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing DELETE request to /users{}", req.getPathInfo());

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            log.warn("Empty path DELETE request path");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length == 2) {
            UUID userId = UUID.fromString(parts[1]);
            deleteUser(userId, req, resp);
        } else {
            log.warn("Invalid DELETE request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showCurrentUserProfile(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            log.warn("Unauthorized access attempt to user profile");
            resp.sendRedirect("/auth/login");
            return;
        }
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/WEB-INF/views/users/profile.jsp").forward(req, resp);
    }

    private void getUserById(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            log.debug("Fetching user by ID: {}", userId);
            User user = userService.getByID(userId);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/users/view.jsp").forward(req, resp);
        } catch (Exception e) {
            log.warn("User not found: {}", userId);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void updateUser(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getUserID().equals(userId)) {
            log.warn("Unauthorized access attempt to updating user profile");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав для выполнения операции");
            return;
        }

        String newName = req.getParameter("name");
        if (newName == null || newName.trim().isEmpty()) {
            log.warn("Empty name provided for user update: {}", userId);
            req.setAttribute("error", "Имя пользователя не может быть пустым");
            req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
            return;
        }

        try {
            if (!Validator.isValidName(newName)) {
                throw new IllegalArgumentException("Некорректное имя пользователя");
            }
            log.debug("Updating name for user: {}", userId);
            userService.updateUserName(userId, newName.trim());
            currentUser.setName(newName.trim());
            req.getSession().setAttribute("user", currentUser);
            log.info("Successfully updated user: {}", userId);
            resp.sendRedirect("/users");
        } catch (IllegalArgumentException e) {
            log.warn("Validation error for user {}: {}", userId, e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.setAttribute("currentName", newName);
            req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error updating user {}: {}", userId, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Произошла ошибка при обновлении данных");
        }
    }

    private void deleteUser(UUID userId, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getUserID().equals(userId)) {
            log.warn("Unauthorized access attempt to deleting user profile");
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            log.debug("Deleting user: {}", userId);
            userService.deleteUser(userId);
            req.getSession().invalidate();
            log.info("Successfully deleted user: {}", userId);
            resp.sendRedirect("/auth/login");
        } catch (Exception e) {
            log.error("Error deleting user {}: {}", userId, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
