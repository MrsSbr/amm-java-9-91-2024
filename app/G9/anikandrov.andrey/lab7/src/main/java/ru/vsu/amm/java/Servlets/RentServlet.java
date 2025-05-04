package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Services.RentalObjectService;
import ru.vsu.amm.java.Enums.ObjectType;
import ru.vsu.amm.java.Repository.RentalObjectRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/rent")
public class RentServlet extends HttpServlet {
    private RentalObjectService objectService;

    @Override
    public void init() {
        this.objectService = new RentalObjectService(new RentalObjectRepository());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            request.setAttribute("items", objectService.getObjectsByType(ObjectType.ITEM));
            request.setAttribute("events", objectService.getObjectsByType(ObjectType.EVENT));
            request.setAttribute("habitations", objectService.getObjectsByType(ObjectType.HABITATION));
            request.getRequestDispatcher("/rent.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error loading objects", e);
        }
    }
}