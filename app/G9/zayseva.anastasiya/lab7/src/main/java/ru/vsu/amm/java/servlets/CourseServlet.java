package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Course;
import ru.vsu.amm.java.repository.CourseRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/courses/*")
public class CourseServlet extends HttpServlet {
    private final CourseRepository courseRepository = new CourseRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action == null || action.equals("/list")) {
            req.setAttribute("courses", courseRepository.getAll());
            req.getRequestDispatcher("/WEB-INF/views/courses/list.jsp").forward(req, resp);
        } else if (action.equals("/new")) {
            req.getRequestDispatcher("/WEB-INF/views/courses/form.jsp").forward(req, resp);
        } else if (action.equals("/edit")) {
            long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("course", courseRepository.getById(id));
            req.getRequestDispatcher("/WEB-INF/views/courses/form.jsp").forward(req, resp);
        } else if (action.equals("/delete")) {
            long id = Long.parseLong(req.getParameter("id"));
            courseRepository.delete(id);
            resp.sendRedirect(req.getContextPath() + "/courses/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getPathInfo();

        if (action.equals("/save")) {
            Course course = new Course();
            course.setTitle(req.getParameter("title"));
            course.setDescription(req.getParameter("description"));

            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                courseRepository.save(course);
            } else {
                course.setCourseId(Long.parseLong(idParam));
                courseRepository.update(course);
            }

            resp.sendRedirect(req.getContextPath() + "/courses/list");
        }
    }
}