package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import java.io.IOException;
import static java.lang.Long.parseLong;

@WebServlet("/protected/doctor/delete-plan")
public class DeletePlanServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeletePlanServlet.class);
    private final PlanService planService;

    public DeletePlanServlet() {
        planService = new PlanService();
    }

    public DeletePlanServlet(PlanService planService) {
        this.planService = planService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserEntity currentUser = (UserEntity) request.getSession().getAttribute("user");
        long planId = parseLong(request.getParameter("id"));

        try {
            logger.info("Доктор {} инициировал удаление плана лечения ID: {}",
                    currentUser.getEmail(), planId);

            planService.deletePlanById(planId);

            logger.debug("План лечения ID: {} успешно удален доктором {}",
                    planId, currentUser.getEmail());

            response.sendRedirect(request.getContextPath() + "/protected/home");

        } catch (Exception e) {
            logger.error("Ошибка при удалении плана лечения ID: {} доктором {}: {}",
                    planId, currentUser.getEmail(), e.getMessage(), e);

            request.setAttribute("error", "Ошибка при удалении плана: " + e.getMessage());
            request.getRequestDispatcher("/protected/doctor/edit-plan").forward(request, response);
        }
    }
}