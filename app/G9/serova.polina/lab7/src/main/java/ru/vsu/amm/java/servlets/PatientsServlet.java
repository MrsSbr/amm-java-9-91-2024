package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;
import java.io.IOException;
import java.util.List;

@WebServlet("/protected/doctor/patients")
public class PatientsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PatientsServlet.class);
    private final UserService userService;

    public PatientsServlet() {
        userService = new UserService();
    }

    public PatientsServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        UserEntity currentUser = (UserEntity) session.getAttribute("user");
        String ipAddress = request.getRemoteAddr();

        logger.debug("Запрос списка пациентов от доктора {} (ID: {}, IP: {})",
                currentUser.getEmail(), currentUser.getId(), ipAddress);

        if (currentUser.getRole() != Role.DOCTOR) {
            logger.warn("Попытка доступа к списку пациентов не-доктором {} (ID: {}, Роль: {}, IP: {})",
                    currentUser.getEmail(), currentUser.getId(), currentUser.getRole(), ipAddress);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            List<UserEntity> patients = userService.getAllUsers()
                    .stream()
                    .filter(it -> it.getId() != currentUser.getId())
                    .toList();

            logger.debug("Для доктора {} загружено {} пациентов",
                    currentUser.getEmail(), patients.size());

            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/protected/doctor/patients.jsp").forward(request, response);

            logger.debug("Список пациентов успешно отображен для доктора {}", currentUser.getEmail());

        } catch (Exception e) {
            logger.error("Ошибка при загрузке списка пациентов для доктора {}: {}",
                    currentUser.getEmail(), e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}