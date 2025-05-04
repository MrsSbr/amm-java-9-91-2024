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
        var currentEmployeeId = (int) req.getAttribute("current_employee_id");
        var hotelRoomId = (int) req.getAttribute("hotel_room_id");
        var description = req.getParameter("task_description");

        TasksService manageTasksService = new TasksServiceImpl();
        try {
            var taskDto = manageTasksService.createTask(managerId, currentEmployeeId, hotelRoomId, description);
            req.setAttribute("successMessage", "New task to " + taskDto.getEmployeeLogin() + " was created");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (EmployeeNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (NotAllowedActionException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        }
    }
}
