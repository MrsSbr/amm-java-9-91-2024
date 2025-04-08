package ru.vsu.amm.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.service.ViewService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewSessions")
public class ViewSessionsServlet extends HttpServlet {

    private final ViewService viewService;

    public ViewSessionsServlet() {

        viewService = new ViewService();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<Session> sessionList = viewService.viewSessions(user);

        request.setAttribute("sessions", sessionList);
        request.setAttribute("isUser", user.getRole() == Role.USER);
        request.getRequestDispatcher("userSessions.jsp").forward(request, response);

    }

}
