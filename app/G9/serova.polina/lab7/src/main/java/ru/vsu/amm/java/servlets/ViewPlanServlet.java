package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;
import static java.lang.Long.parseLong;
import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

@WebServlet("/protected/view-plan")
public class ViewPlanServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ViewPlanServlet.class);
    private final PlanService planService;
    private final UserService userService;

    public ViewPlanServlet() {
        planService = new PlanService();
        userService = new UserService();
    }

    public ViewPlanServlet(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");
        String ipAddress = req.getRemoteAddr();

        try {
            long planId = parseLong(req.getParameter("id"));
            logger.debug("Запрос на просмотр плана лечения ID: {} (Пользователь: {}, IP: {})",
                    planId, currentUser.getEmail(), ipAddress);

            PlanEntity plan = planService.getPlanById(planId);

            if (plan == null) {
                logger.warn("План лечения ID: {} не найден (Пользователь: {}, IP: {})",
                        planId, currentUser.getEmail(), ipAddress);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (!isAllowed(currentUser, plan)) {
                logger.warn("Попытка несанкционированного доступа к плану ID: {} (Пользователь: {}, Роль: {}, IP: {})",
                        planId, currentUser.getEmail(), currentUser.getRole(), ipAddress);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            UserEntity doctor = userService.getUserById(plan.getDoctorId());
            UserEntity patient = userService.getUserById(plan.getPatientId());

            req.setAttribute("plan", plan);
            req.setAttribute("doctorName", formatName(doctor));
            req.setAttribute("patientName", formatName(patient));

            logger.info("Отображение плана лечения ID: {} для пользователя {} (Роль: {}, IP: {})",
                    planId, currentUser.getEmail(), currentUser.getRole(), ipAddress);

            req.getRequestDispatcher("/protected/view-plan.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            logger.error("Некорректный ID плана лечения (Пользователь: {}, IP: {}): {}",
                    currentUser.getEmail(), ipAddress, e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Ошибка при отображении плана лечения (Пользователь: {}, IP: {}): {}",
                    currentUser.getEmail(), ipAddress, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isAllowed(UserEntity user, PlanEntity plan) {
        boolean allowed = user.getRole() == Role.DOCTOR || user.getId() == plan.getPatientId();
        if (!allowed) {
            logger.debug("Доступ запрещен: пользователь {} (ID: {}, Роль: {}) не имеет прав на план ID: {}",
                    user.getEmail(), user.getId(), user.getRole(), plan.getId());
        }
        return allowed;
    }
}