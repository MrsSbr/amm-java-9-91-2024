package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.AgreementEntity;
import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Repository.AgreementRepository;
import ru.vsu.amm.java.Repository.RentalObjectRepository;
import ru.vsu.amm.java.Repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/myrent")
public class MyRentObjectsServlets extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("user");
        try {
            UserRepository userRepository = new UserRepository();
            AgreementRepository agreementRepository = new AgreementRepository();
            RentalObjectRepository rentalObjectRepository = new RentalObjectRepository();

            Long userId = userRepository.findByUserName(username)
                    .orElseThrow(() -> new ServletException("User not found"))
                    .getUserID();

            List<AgreementEntity> agreements = agreementRepository.findAll();
            List<AgreementEntity> userAgreements = new ArrayList<>();
            List<RentalObjectEntity> rentalObjects = new ArrayList<>();

            for (AgreementEntity agreement : agreements) {
                if (agreement.getUserID().equals(userId)) {
                    userAgreements.add(agreement);
                    rentalObjectRepository.findById(agreement.getObjectID())
                            .ifPresent(rentalObjects::add);
                }
            }

            request.setAttribute("agreements", userAgreements);
            request.setAttribute("rentalObjects", rentalObjects);
            request.getRequestDispatcher("/myrent.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}