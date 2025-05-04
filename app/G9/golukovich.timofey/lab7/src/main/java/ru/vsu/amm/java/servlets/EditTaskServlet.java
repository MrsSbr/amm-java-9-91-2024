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

@WebServlet("/api/edit_task")
public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var session = req.getSession(false);
            var employee = (EmployeeDto) session.getAttribute("employee");

            var taskId = Integer.parseInt(req.getParameter("task_id"));
            var updatedManagerId = Integer.parseInt(req.getParameter("manager_id"));
            var currentEmployeeId = Integer.parseInt(req.getParameter("current_employee_id"));
            var updatedHotelRoomId = Integer.parseInt(req.getParameter("hotel_room_id"));
            var updatedDescription = req.getParameter("task_description");

            TasksService manageTasksService = new TasksServiceImpl();

            var taskDto = manageTasksService.updateTask(employee.getId(), taskId, updatedManagerId, currentEmployeeId, updatedHotelRoomId, updatedDescription);
            req.setAttribute("successMessage", "Task " + taskDto.getId() + " was updated");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Incorrect value");
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
