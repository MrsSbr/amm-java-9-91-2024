package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;
import ru.vsu.amm.java.enums.TaskStatus;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.services.EmployeesService;
import ru.vsu.amm.java.services.HotelRoomsService;
import ru.vsu.amm.java.services.TasksService;
import ru.vsu.amm.java.services.impl.EmployeesServiceImpl;
import ru.vsu.amm.java.services.impl.HotelRoomsServiceImpl;
import ru.vsu.amm.java.services.impl.TasksServiceImpl;
import ru.vsu.amm.java.utils.ServletMessageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/api/manage_tasks")
public class ManageTasksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var session = req.getSession(false);
            var employee = (EmployeeDto) session.getAttribute("employee");

            EmployeesService employeesService = new EmployeesServiceImpl();

            int currentEmployeeId;
            if (req.getParameter("current_employee_id") != null) {
                currentEmployeeId = Integer.parseInt(req.getParameter("current_employee_id"));
            } else {
                currentEmployeeId = (Integer) session.getAttribute("current_employee_id");
                session.removeAttribute("current_employee_id");
            }
            var currentEmployee = employeesService.getEmployeeById(currentEmployeeId);

            TasksService tasksService = new TasksServiceImpl();
            var employeeTasks = tasksService.getAllTasksByEmployeeId(currentEmployeeId);

            var taskStatuses = Arrays.stream(TaskStatus.values()).toList();

            var managers = employeesService.getAllFilteredEmployees(null, null, null, null, null, null,
                    null, null, EmployeePost.MANAGER.name(), null);
            var employees = employeesService.getAllFilteredEmployees(null, employee.getHotelId(), null, null, null,
                    null, null, null, null, null);
            employees = employees.stream().filter(e -> e.getPost() == EmployeePost.STAFF).toList();

            HotelRoomsService hotelRoomsService = new HotelRoomsServiceImpl();
            var hotelRooms = hotelRoomsService.getHotelRoomsByHotelId(employee.getHotelId());

            req.setAttribute("current_employee", currentEmployee);
            req.setAttribute("employees", employees);
            req.setAttribute("tasks", employeeTasks);
            req.setAttribute("task_statuses", taskStatuses);
            req.setAttribute("managers", managers);
            req.setAttribute("staff", employees);
            req.setAttribute("hotel_rooms", hotelRooms);

            ServletMessageHelper.CopyErrorMessage(req, session);
            ServletMessageHelper.CopySuccessMessage(req, session);

            getServletContext().getRequestDispatcher("/manage_tasks.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Incorrect employee id. Please, try again");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (EmployeeNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/employees_manager_dashboard").forward(req, resp);
        }
    }
}
