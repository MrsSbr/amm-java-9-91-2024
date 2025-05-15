package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/protected/doctor/patients")
public class PatientsServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        UserEntity currentUser = (UserEntity) session.getAttribute("user");
        if (currentUser.getRole() != Role.DOCTOR) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<UserEntity> patients = userRepository.findAll()
                .stream().filter(it -> it.getId() != currentUser.getId())
                .collect(Collectors.toList());
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/protected/doctor/patients.jsp").forward(request, response);
    }
}
