package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.AgreementEntity;
import ru.vsu.amm.java.Entities.RentalObjectEntity;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.AgreementRepository;
import ru.vsu.amm.java.Repository.RentalObjectRepository;
import ru.vsu.amm.java.Repository.UserRepository;
import ru.vsu.amm.java.Services.AgreementService;
import ru.vsu.amm.java.Services.RentalObjectService;

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
import java.util.stream.Collectors;

@WebServlet("/myrent")
public class MyRentObjectsServlets extends HttpServlet {

    private AgreementService agreementService;
    private RentalObjectService rentalObjectService;

    @Override
    public void init() {
        AgreementRepository agreementRepository = new AgreementRepository();
        UserRepository userRepository = new UserRepository();
        RentalObjectRepository rentalObjectRepository = new RentalObjectRepository();

        this.agreementService = new AgreementService(agreementRepository, userRepository, rentalObjectRepository);
        this.rentalObjectService = new RentalObjectService(rentalObjectRepository);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("user");

        try {
            List<AgreementEntity> userAgreements = agreementService.getUserAgreements(username);

            List<Long> objectIds = userAgreements.stream()
                    .map(AgreementEntity::getObjectID)
                    .collect(Collectors.toList());

            List<RentalObjectEntity> rentalObjects = rentalObjectService.getObjectsForAgreements(objectIds);

            request.setAttribute("agreements", userAgreements);
            request.setAttribute("rentalObjects", rentalObjects);
            request.getRequestDispatcher("/myrent.jsp").forward(request, response);

        } catch (NotFoundException e) {
            session.setAttribute("errorMessage", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rent");
        } catch (DatabaseException e) {
            throw new ServletException("Database error", e);
        }
    }
}