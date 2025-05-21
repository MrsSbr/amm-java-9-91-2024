package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.services.TasksService;
import ru.vsu.amm.java.services.impl.TasksServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/create_task")
public class CreateTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var manager = (EmployeeDto) session.getAttribute("employee");

        var managerId = manager.getId();
        var currentEmployeeId = req.getParameter("create_task_employeeId");
        var hotelRoomId = req.getParameter("create_task_hotelRoomId");
        var description = req.getParameter("create_task_description");

        try {
            TasksService manageTasksService = new TasksServiceImpl();
            var taskDto = manageTasksService.createTask(managerId, currentEmployeeId, hotelRoomId, description);
            session.setAttribute("successMessage", "New task to " + taskDto.getEmployeeLogin() + " was created");
            session.setAttribute("current_employee_id", Integer.parseInt(currentEmployeeId));
            resp.sendRedirect(req.getContextPath() + "/api/manage_tasks");
        } catch (EmployeeNotFoundException e) {
            session.setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/api/manage_tasks");
        } catch (NotAllowedActionException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        } catch (DatabaseException e) {
            session.setAttribute("errorMessage", "Internal server error! Try again later");
            resp.sendRedirect(req.getContextPath() + "/api/manage_tasks");
        }
    }
}
