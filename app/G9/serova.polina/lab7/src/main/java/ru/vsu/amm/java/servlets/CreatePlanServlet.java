package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;
import java.util.List;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static ru.vsu.amm.java.util.TimeParser.parseTimes;

@WebServlet("/protected/doctor/create-plan")
public class CreatePlanServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CreatePlanServlet.class);
    private final PlanService planService;
    private final UserService userService;

    public CreatePlanServlet() {
        planService = new PlanService();
        userService = new UserService();
    }

    public CreatePlanServlet(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        UserEntity doctor = (UserEntity) session.getAttribute("user");
        logger.debug("Доктор {} запросил форму создания плана лечения",
                doctor.getEmail());

        try {
            List<UserEntity> patients = userService.getAllUsers().stream()
                    .filter(it -> it.getId() != doctor.getId())
                    .toList();

            request.setAttribute("patients", patients);
            String patientId = request.getParameter("patientId");
            request.setAttribute("selectedPatientId", patientId);

            logger.debug("Отображение формы создания плана для доктора {} (найдено {} пациентов)",
                    doctor.getEmail(), patients.size());

            request.getRequestDispatcher("/protected/doctor/create-plan.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            logger.error("Ошибка при подготовке формы создания плана для доктора {}",
                    doctor.getEmail(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        UserEntity doctor = (UserEntity) session.getAttribute("user");

        try {
            long patientId = parseLong(request.getParameter("patientId"));
            String medicationName = request.getParameter("medicationName");
            double dosageMg = parseDouble(request.getParameter("dosageMg"));
            int durationDays = parseInt(request.getParameter("durationDays"));

            logger.info("Доктор {} создает план лечения для пациента {}: препарат '{}', дозировка {} мг",
                    doctor.getEmail(), patientId, medicationName, dosageMg);

            planService.addPlan(
                    medicationName,
                    dosageMg,
                    parseTimes(request.getParameterValues("takingTime")),
                    durationDays,
                    doctor.getId(),
                    patientId
            );

            logger.debug("План лечения успешно создан (доктор: {}, пациент: {})",
                    doctor.getEmail(), patientId);

            response.sendRedirect(request.getContextPath() + "/protected/home");

        } catch (Exception e) {
            logger.error("Ошибка при создании плана лечения (доктор: {}): {}",
                    doctor.getEmail(), e.getMessage(), e);

            request.setAttribute("error", "Ошибка при создании плана: " + e.getMessage());
            doGet(request, response);
        }
    }
}