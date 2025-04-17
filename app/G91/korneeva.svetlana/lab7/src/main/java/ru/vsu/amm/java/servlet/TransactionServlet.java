package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.Transaction;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.entity.response.TransactionResponse;
import ru.vsu.amm.java.enums.Category;
import ru.vsu.amm.java.exception.TransactionException;
import ru.vsu.amm.java.service.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionService service = new TransactionService();
        HttpSession session = req.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");
        TransactionResponse response = null;

        String fromParam = req.getParameter("from");
        String toParam = req.getParameter("to");

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;

        if (fromParam != null && !fromParam.isEmpty()) {
            fromDate = LocalDate.parse(fromParam).atStartOfDay();
        }

        if (toParam != null && !toParam.isEmpty()) {
            toDate = LocalDate.parse(toParam).atTime(23, 59, 59);
        }

        try {
            if (fromDate != null && toDate != null) {
                response = service.findByTimeRange(user, fromDate, toDate);
            } else {
                response = service.findAll(user);
            }
        } catch (TransactionException e) {
            req.setAttribute("errorMessage", e.getMessage());
        }
        req.setAttribute("response", response);
        req.setAttribute("categories", Category.values());
        getServletContext().getRequestDispatcher("/transactions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionService service = new TransactionService();
        HttpSession session = req.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");

        String amountParam = req.getParameter("amount");
        String typeParam = req.getParameter("type");
        String dateParam = req.getParameter("date");
        String categoryParam = req.getParameter("category");

        Boolean type = Boolean.parseBoolean(typeParam);
        BigDecimal amount = new BigDecimal(amountParam);
        LocalDateTime date = LocalDateTime.parse(dateParam);
        Category category = Category.valueOf(categoryParam);

        if (amount.scale() > 2) {
            req.setAttribute("errorSave", "Сумма должна содержать не более двух знаков после запятой.");
        } else {
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .category(category)
                    .date(date)
                    .type(type)
                    .build();

            try {
                service.save(user, transaction);
            } catch (TransactionException e) {
                req.setAttribute("errorSave", e.getMessage());
            }
        }

        TransactionResponse response = null;
        try {
            response = service.findAll(user);
        } catch (TransactionException e) {
            req.setAttribute("errorMessage", e.getMessage());
        }

        req.setAttribute("response", response);
        req.setAttribute("categories", Category.values());
        getServletContext().getRequestDispatcher("/transactions.jsp").forward(req, resp);
    }
}
