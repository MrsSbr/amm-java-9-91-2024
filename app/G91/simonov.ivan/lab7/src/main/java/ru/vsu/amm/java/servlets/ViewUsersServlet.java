package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.ViewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.utils.Redirection.redirectToViewUsers;

@WebServlet("/viewUsers")
public class ViewUsersServlet extends HttpServlet {

    private final ViewService viewService;

    public ViewUsersServlet() {

        viewService = new ViewService();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<User> userList = viewService.viewUsers(user);

        request.setAttribute("users", userList);

        String redirect = redirectToViewUsers(user);
        request.getRequestDispatcher(redirect).forward(request, response);

    }
}
