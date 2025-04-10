package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.PlanRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static ru.vsu.amm.java.util.TimeParser.parseTimes;

@WebServlet("/protected/doctor/create-plan")
public class CreatePlanServlet extends HttpServlet {
    private final PlanRepository planRepository = new PlanRepository();
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");

        List<UserEntity> patients = userRepository.findAll()
                .stream().filter(it -> it.getId() != user.getId())
                .collect(Collectors.toList());
        request.setAttribute("patients", patients);

        String patientId = request.getParameter("patientId");
        request.setAttribute("selectedPatientId", patientId);

        request.getRequestDispatcher("/protected/doctor/create-plan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            PlanEntity plan = new PlanEntity();
            plan.setMedicationName(request.getParameter("medicationName"));
            plan.setDosageMg(parseDouble(request.getParameter("dosageMg")));
            plan.setTakingTime(parseTimes(request.getParameterValues("takingTime")));
            plan.setDurationDays(parseInt(request.getParameter("durationDays")));
            plan.setPatientId(parseLong(request.getParameter("patientId")));
            plan.setDoctorId(((UserEntity) request.getSession().getAttribute("user")).getId());

            planRepository.upsert(plan);
            response.sendRedirect(request.getContextPath() + "/protected/home");

        } catch (Exception e) {
            request.setAttribute("error", "Ошибка при создании плана: " + e.getMessage());
            doGet(request, response);
        }
    }
}
