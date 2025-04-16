package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static ru.vsu.amm.java.util.TimeParser.parseTimes;
import static ru.vsu.amm.java.util.UserNameFormatter.formatName;

@WebServlet("/protected/doctor/edit-plan")
public class EditPlanServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(EditPlanServlet.class);
    private final PlanService planService;
    private final UserService userService;

    public EditPlanServlet() {
        planService = new PlanService();
        userService = new UserService();
    }

    public EditPlanServlet(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");
        long planId = parseLong(req.getParameter("id"));

        logger.debug("Доктор {} запросил редактирование плана лечения ID: {}",
                currentUser.getEmail(), planId);

        PlanEntity plan = planService.getPlanById(planId);

        if (plan == null) {
            logger.warn("План лечения ID: {} не найден (запросил доктор: {})",
                    planId, currentUser.getEmail());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (plan.getDoctorId() != currentUser.getId()) {
            logger.warn("Попытка несанкционированного доступа к плану ID: {} (доктор: {})",
                    planId, currentUser.getEmail());
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            UserEntity patient = userService.getUserById(plan.getPatientId());
            req.setAttribute("plan", plan);
            req.setAttribute("patientName", formatName(patient));

            logger.debug("Отображение формы редактирования плана ID: {} для доктора {}",
                    planId, currentUser.getEmail());

            req.getRequestDispatcher("/protected/doctor/edit-plan.jsp").forward(req, resp);

        } catch (Exception e) {
            logger.error("Ошибка при подготовке формы редактирования плана ID: {} (доктор: {}): {}",
                    planId, currentUser.getEmail(), e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UserEntity currentUser = (UserEntity) req.getSession().getAttribute("user");
        long planId = parseLong(req.getParameter("id"));

        logger.info("Доктор {} обновляет план лечения ID: {}",
                currentUser.getEmail(), planId);

        try {
            PlanEntity existingPlan = planService.getPlanById(planId);
            if (existingPlan == null || existingPlan.getDoctorId() != currentUser.getId()) {
                logger.warn("Попытка обновления несуществующего или чужого плана ID: {} (доктор: {})",
                        planId, currentUser.getEmail());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            planService.updatePlan(
                    planId,
                    req.getParameter("medicationName"),
                    parseDouble(req.getParameter("dosageMg")),
                    parseTimes(req.getParameterValues("takingTime")),
                    parseInt(req.getParameter("durationDays")),
                    currentUser.getId(),
                    existingPlan.getPatientId()
            );

            logger.debug("План лечения ID: {} успешно обновлен доктором {}",
                    planId, currentUser.getEmail());

            resp.sendRedirect(req.getContextPath() + "/protected/view-plan?id=" + planId);

        } catch (Exception e) {
            logger.error("Ошибка при обновлении плана лечения ID: {} (доктор: {}): {}",
                    planId, currentUser.getEmail(), e.getMessage(), e);

            req.setAttribute("error", "Ошибка обновления: " + e.getMessage());
            doGet(req, resp);
        }
    }
}