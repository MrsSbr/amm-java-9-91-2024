package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.exceptions.TaskStatusDoesNotExistException;
import ru.vsu.amm.java.services.TasksService;
import ru.vsu.amm.java.services.impl.TasksServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/edit_task")
public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var session = req.getSession(false);
            var employee = (EmployeeDto) session.getAttribute("employee");

            var taskId = Integer.parseInt(req.getParameter("task_id"));
            var updatedManagerLogin = (String) req.getParameter("task_manager_login");
            var currentEmployeeLogin = (String) req.getParameter("task_employee_login");
            var updatedHotelRoomId = Integer.parseInt(req.getParameter("task_hotel_room_id"));
            var updatedStatus = req.getParameter("task_status");
            var updatedDescription = req.getParameter("task_description");

            TasksService manageTasksService = new TasksServiceImpl();

            var taskDto = manageTasksService.updateTask(employee.getId(), taskId, updatedManagerLogin, currentEmployeeLogin, updatedHotelRoomId, updatedStatus, updatedDescription);
            session.setAttribute("successMessage", "Task " + taskDto.getId() + " was updated");
            session.setAttribute("current_employee_id", currentEmployeeLogin);
            resp.sendRedirect(req.getContextPath() + "/api/manage_tasks");
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Incorrect value");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (EmployeeNotFoundException | TaskStatusDoesNotExistException e) {
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
