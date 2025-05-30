package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;

import java.io.IOException;
import java.time.LocalDate;

import ru.vsu.amm.java.enums.Gender;

import static ru.vsu.amm.java.utils.ServletConstants.PSY_REGISTER_PAGE;
import static ru.vsu.amm.java.utils.ServletConstants.URL_PSY_REGISTER;
import static ru.vsu.amm.java.utils.ServletConstants.URL_PSY_SESSIONS;

@WebServlet(name = "PsychologistRegistrationServlet", urlPatterns = URL_PSY_REGISTER)
public class PsychologistRegistrationServlet extends HttpServlet {

    private final PsychologistService psychologistService;

    public PsychologistRegistrationServlet() {
        this.psychologistService = new PsychologistServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher(PSY_REGISTER_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        LocalDate birth = LocalDate.parse(req.getParameter("birthdate"));
        Gender gender = Gender.valueOf(req.getParameter("gender"));
        Short experience = Short.valueOf(req.getParameter("experience"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            psychologistService.register(new PsychologistRegisterRequest(
                    name, surname, birth, gender, experience, login, password
            ));

            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect(URL_PSY_SESSIONS);

        } catch (WrongUserCredentialsException | DataAccessException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext()
                    .getRequestDispatcher(PSY_REGISTER_PAGE)
                    .forward(req, resp);
        }
    }
}
