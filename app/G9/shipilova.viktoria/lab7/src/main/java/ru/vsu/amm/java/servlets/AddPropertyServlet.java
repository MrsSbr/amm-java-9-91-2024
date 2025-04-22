package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.services.FoundPropertyService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/addProperty")
public class AddPropertyServlet extends HttpServlet {
    private FoundPropertyService foundPropertyService = new FoundPropertyService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            session = request.getSession();
            session.setAttribute("redirectTo", "addProperty.jsp");
            response.sendRedirect("authorization");
            return;
        }

        String propType = request.getParameter("propertyType");
        String placeOfFinding = request.getParameter("placeOfFinding");
        String description = request.getParameter("description");

        foundPropertyService.addFoundProperty(propType, placeOfFinding, description, user);

        String message = URLEncoder.encode("Вещь успешно добавлена", StandardCharsets.UTF_8.toString());
        response.sendRedirect("listProperties?message=" + message);
    }
}

