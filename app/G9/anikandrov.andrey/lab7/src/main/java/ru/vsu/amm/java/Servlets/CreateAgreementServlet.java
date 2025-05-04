package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Exception.ValidationException;
import ru.vsu.amm.java.Services.AgreementService;
import ru.vsu.amm.java.Repository.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;


@WebServlet("/createAgreement")
public class CreateAgreementServlet extends HttpServlet {
    private AgreementService agreementService;

    @Override
    public void init() {
        this.agreementService = new AgreementService(
                new AgreementRepository(),
                new UserRepository(),
                new RentalObjectRepository()
        );
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            String username = (String) session.getAttribute("user");
            Long objectId = Long.parseLong(request.getParameter("objectId"));
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

            agreementService.createAgreement(username, objectId, startDate, endDate);
            response.sendRedirect(request.getContextPath() + "/myrent");

        } catch (ValidationException | NotFoundException e) {
            session.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rent");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Ошибка сервера: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rent");
        }
    }
}