package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.repository.PlanRepository;

import java.io.IOException;

@WebServlet("/protected/doctor/delete-plan")
public class DeletePlanServlet extends HttpServlet {
    private final PlanRepository planRepository = new PlanRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long planId = Long.parseLong(request.getParameter("id"));
            planRepository.delete(planRepository.findById(planId));
            response.sendRedirect(request.getContextPath() + "/protected/home");
        } catch (Exception e) {
            request.setAttribute("error", "Ошибка при удалении плана: " + e.getMessage());
            request.getRequestDispatcher("/protected/doctor/edit-plan").forward(request, response);
        }
    }
}
