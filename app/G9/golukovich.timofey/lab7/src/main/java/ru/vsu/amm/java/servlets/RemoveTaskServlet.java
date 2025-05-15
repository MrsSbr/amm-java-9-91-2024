package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.exceptions.NotAllowedActionException;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.TasksService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;
import ru.vsu.amm.java.services.impl.TasksServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/remove_task")
public class RemoveTaskServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        var manager = (EmployeeDto) session.getAttribute("employee");

        try {
            var taskId = Integer.parseInt(req.getParameter("task_id"));
            var taskEmployeeLogin = req.getParameter("task_employee_login");

            EmployeesService employeesService = new EmployeesServiceImpl();
            var taskEmployee = employeesService.getEmployeeByLogin(taskEmployeeLogin);

            TasksService manageTasksService = new TasksServiceImpl();
            manageTasksService.removeTask(manager.getId(), taskId, taskEmployee.getId());
            session.setAttribute("successMessage", "Task " + taskId + " was removed");
            session.setAttribute("current_employee_id", taskEmployee.getId());
            resp.sendRedirect(req.getContextPath() + "/api/manage_tasks");
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
