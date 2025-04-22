package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.io.IOException;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static ru.vsu.amm.java.util.TimeParser.parseTimes;
import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

@WebServlet("/protected/doctor/edit-plan")
public class EditPlanServlet extends HttpServlet {
    private final PlanRepository planRepo = new PlanRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");
        long planId = Long.parseLong(req.getParameter("id"));
        PlanEntity plan = planRepo.findById(planId);

        if (plan == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (plan.getDoctorId() != currentUser.getId()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        UserEntity patient = userRepo.findById(plan.getPatientId());
        req.setAttribute("plan", plan);
        req.setAttribute("patientName", formatName(patient));
        req.getRequestDispatcher("/protected/doctor/edit-plan.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");
            long planId = Long.parseLong(req.getParameter("id"));

            PlanEntity existingPlan = planRepo.findById(planId);
            if (existingPlan == null || existingPlan.getDoctorId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            System.out.println(req.getParameter("dosageMg"));
            PlanEntity updatedPlan = new PlanEntity(
                    planId,
                    req.getParameter("medicationName"),
                    parseDouble(req.getParameter("dosageMg")),
                    parseTimes(req.getParameterValues("takingTime")),
                    parseInt(req.getParameter("durationDays")),
                    currentUser.getId(),
                    existingPlan.getPatientId()
            );

            planRepo.update(updatedPlan);
            resp.sendRedirect(req.getContextPath() + "/protected/view-plan?id=" + planId);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка обновления: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
