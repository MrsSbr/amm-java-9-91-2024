package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import java.io.IOException;
import java.util.List;

@WebServlet("/protected/home")
public class HomeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HomeServlet.class);
    private final PlanService planService;

    public HomeServlet() {
        planService = new PlanService();
    }

    public HomeServlet(PlanService planService) {
        this.planService = planService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");

        logger.debug("Пользователь {} ({}) запросил главную страницу",
                user.getEmail(), user.getRole());

        try {
            if (user.getRole() == Role.DOCTOR) {
                List<PlanViewDto> doctorPlans = planService.getDoctorPlans(user.getId());
                request.setAttribute("doctorPlans", doctorPlans);
                logger.debug("Для доктора {} загружено {} планов лечения",
                        user.getEmail(), doctorPlans.size());
            }

            List<PlanViewDto> patientPlans = planService.getPatientPlans(user.getId());
            request.setAttribute("patientPlans", patientPlans);
            logger.debug("Для пользователя {} загружено {} планов как пациента",
                    user.getEmail(), patientPlans.size());

            request.getRequestDispatcher("/protected/home.jsp").forward(request, response);
            logger.debug("Главная страница успешно отображена для пользователя {}", user.getEmail());

        } catch (Exception e) {
            logger.error("Ошибка при загрузке главной страницы для пользователя {}: {}",
                    user.getEmail(), e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}