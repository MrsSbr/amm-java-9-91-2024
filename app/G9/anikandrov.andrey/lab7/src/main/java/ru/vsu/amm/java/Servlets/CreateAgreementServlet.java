package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.AgreementEntity;
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
import java.time.LocalDate;

@WebServlet("/createAgreement")
public class CreateAgreementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

            if (startDate.isAfter(endDate)) {
                session.setAttribute("errorMessage", "Дата начала не может быть позже даты окончания!");
                response.sendRedirect(request.getContextPath() + "/rent");
                return;
            }

            if (startDate.isBefore(LocalDate.now())) {
                session.setAttribute("errorMessage", "Ошибка: Дата начала не может быть в прошлом!");
                response.sendRedirect(request.getContextPath() + "/rent");
                return;
            }

            UserRepository userRepository = new UserRepository();
            RentalObjectRepository rentalObjectRepository = new RentalObjectRepository();
            AgreementRepository agreementRepository = new AgreementRepository();

            Long userId = userRepository.findByUserName(username)
                    .orElseThrow(() -> new ServletException("User not found"))
                    .getUserID();

            int price = rentalObjectRepository.findById(objectId)
                    .orElseThrow(() -> new ServletException("Object not found"))
                    .getPrice();

            int days = (int) (endDate.toEpochDay() - startDate.toEpochDay());
            int totalPrice = price * days;

            AgreementEntity agreement = new AgreementEntity();
            agreement.setUserID(userId);
            agreement.setObjectID(objectId);
            agreement.setTimeStart(startDate);
            agreement.setTimeEnd(endDate);
            agreement.setSumPrice(totalPrice);

            agreementRepository.save(agreement);

            response.sendRedirect(request.getContextPath() + "/myrent");

        } catch (Exception e) {
            session.setAttribute("errorMessage", "Ошибка при создании договора: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rent");
        }

    }
}