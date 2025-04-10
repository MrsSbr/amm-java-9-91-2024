package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.io.IOException;

import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

@WebServlet("/protected/view-plan")
public class ViewPlanServlet extends HttpServlet {
    private final PlanRepository planRepo = new PlanRepository();
    private final UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            long planId = Long.parseLong(req.getParameter("id"));
            PlanEntity plan = planRepo.findById(planId);
            UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");

            if (plan == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (!isAllowed(currentUser, plan)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            UserEntity doctor = userRepo.findById(plan.getDoctorId());
            UserEntity patient = userRepo.findById(plan.getPatientId());

            req.setAttribute("plan", plan);
            req.setAttribute("doctorName", formatName(doctor));
            req.setAttribute("patientName", formatName(patient));
            req.getRequestDispatcher("/protected/view-plan.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean isAllowed(UserEntity user, PlanEntity plan) {
        return user.getRole() == Role.DOCTOR
                || user.getId() == plan.getPatientId();
    }
}
