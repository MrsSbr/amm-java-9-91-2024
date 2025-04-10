package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;

import java.io.IOException;
import java.util.List;

@WebServlet("/protected/home")
public class HomeServlet extends HttpServlet {
    private final PlanService planService = new PlanService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user.getRole() == Role.DOCTOR) {
            List<PlanViewDto> doctorPlans = planService.getDoctorPlans(user.getId());
            request.setAttribute("doctorPlans", doctorPlans);
        }
        List<PlanViewDto> patientPlans = planService.getPatientPlans(user.getId());
        request.setAttribute("patientPlans", patientPlans);
        request.getRequestDispatcher("/protected/home.jsp").forward(request, response);
    }
}
