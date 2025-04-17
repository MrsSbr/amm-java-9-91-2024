package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.exception.TransactionException;
import ru.vsu.amm.java.service.TransactionService;

import java.io.IOException;

@WebServlet("/transactions/delete")
public class TransactionDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TransactionService service = new TransactionService();
        HttpSession session = req.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");
        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                service.delete(user, id);
            } catch (ForbiddenException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (TransactionException | NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        resp.sendRedirect("/transactions");
    }
}
