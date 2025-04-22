package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.services.FoundPropertyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listProperties")
public class DisplayPropertiesServlet extends HttpServlet {
    private FoundPropertyService foundPropertyService = new FoundPropertyService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        boolean editable = "true".equals(request.getParameter("editable"));

        if (user == null && editable) {
            session = request.getSession();
            session.setAttribute("redirectTo", "listProperties?editable=true");
            response.sendRedirect("authorization");
            return;
        }

        List<FoundProperty> notReturnedProperties = foundPropertyService.getNotReturnedProperties();
        request.setAttribute("properties", notReturnedProperties);

        request.setAttribute("editable", user != null);
        request.getRequestDispatcher("listProperties.jsp").forward(request, response);
    }
}

