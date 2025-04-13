package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.AgreementEntity;
import ru.vsu.amm.java.Repository.AgreementRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cancel")
public class CancelAgreementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long agreementId = Long.parseLong(request.getParameter("agreementId"));
            AgreementRepository repository = new AgreementRepository();

            AgreementEntity agreement = repository.findById(agreementId)
                    .orElseThrow(() -> new ServletException("Agreement not found"));

            repository.delete(agreement);

            response.sendRedirect(request.getContextPath() + "/myrent");

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error canceling agreement", e);
        }
    }
}