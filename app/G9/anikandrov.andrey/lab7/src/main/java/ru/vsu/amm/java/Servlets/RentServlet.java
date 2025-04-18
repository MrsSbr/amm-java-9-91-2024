package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Enums.ObjectType;
import ru.vsu.amm.java.Repository.RentalObjectRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/rent")
public class RentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            RentalObjectRepository repository = new RentalObjectRepository();
            List<RentalObjectEntity> allObjects = repository.findAll();

            List<RentalObjectEntity> items = filterByType(allObjects, ObjectType.ITEM);
            List<RentalObjectEntity> events = filterByType(allObjects, ObjectType.EVENT);
            List<RentalObjectEntity> habitations = filterByType(allObjects, ObjectType.HABITATION);

            request.setAttribute("items", items);
            request.setAttribute("events", events);
            request.setAttribute("habitations", habitations);
            request.getRequestDispatcher("/rent.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private List<RentalObjectEntity> filterByType(List<RentalObjectEntity> objects, ObjectType type) {
        return objects.stream()
                .filter(o -> o.getObjectType() == type)
                .collect(Collectors.toList());
    }
}