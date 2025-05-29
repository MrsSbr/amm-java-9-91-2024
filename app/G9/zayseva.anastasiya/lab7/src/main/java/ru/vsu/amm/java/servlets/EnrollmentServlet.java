package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.UserCourse;
import ru.vsu.amm.java.enams.EnrollmentStatus;
import ru.vsu.amm.java.repository.CourseRepository;
import ru.vsu.amm.java.repository.UserCourseRepository;
import ru.vsu.amm.java.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/enrollments/*")
public class EnrollmentServlet extends HttpServlet {
    private final UserCourseRepository enrollmentRepository = new UserCourseRepository();
    private final UserRepository userRepository = new UserRepository();
    private final CourseRepository courseRepository = new CourseRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null || action.equals("/list")) {
            req.setAttribute("enrollments", enrollmentRepository.getAll());
            req.setAttribute("users", userRepository.getAll());
            req.setAttribute("courses", courseRepository.getAll());
            req.getRequestDispatcher("/WEB-INF/views/enrollments/list.jsp").forward(req, resp);
        } else if (action.equals("/new")) {
            req.setAttribute("users", userRepository.getAll());
            req.setAttribute("courses", courseRepository.getAll());
            req.getRequestDispatcher("/WEB-INF/views/enrollments/form.jsp").forward(req, resp);
        } else if (action.equals("/edit")) {
            long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("enrollment", enrollmentRepository.getById(id));
            req.setAttribute("users", userRepository.getAll());
            req.setAttribute("courses", courseRepository.getAll());
            req.getRequestDispatcher("/WEB-INF/views/enrollments/form.jsp").forward(req, resp);
        } else if (action.equals("/delete")) {
            long id = Long.parseLong(req.getParameter("id"));
            enrollmentRepository.delete(id);
            resp.sendRedirect(req.getContextPath() + "/enrollments/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action.equals("/save")) {
            UserCourse enrollment = new UserCourse();
            enrollment.setUserId(Long.parseLong(req.getParameter("userId")));
            enrollment.setCourseId(Long.parseLong(req.getParameter("courseId")));
            enrollment.setSubscriptionDate(LocalDate.now());
            enrollment.setEnrollmentStatus(EnrollmentStatus.valueOf(req.getParameter("status")));

            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                enrollmentRepository.save(enrollment);
            } else {
                enrollment.setUserCourseId(Long.parseLong(idParam));
                enrollmentRepository.update(enrollment);
            }

            resp.sendRedirect(req.getContextPath() + "/enrollments/list");
        }
    }
}
