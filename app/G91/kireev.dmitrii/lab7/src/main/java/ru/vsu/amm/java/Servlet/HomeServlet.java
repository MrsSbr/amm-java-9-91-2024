package ru.vsu.amm.java.Servlet;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Entity.UserEntity;
import ru.vsu.amm.java.Repository.Impl.UserRepo;
import ru.vsu.amm.java.Service.Impl.DefaultTicketServiceImpl;
import ru.vsu.amm.java.Service.Interface.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
@Slf4j
public class HomeServlet extends HttpServlet {

    private final UserRepo userRepo = new UserRepo();
    private final TicketService ticketService = new DefaultTicketServiceImpl();

    private static final String HOME_VIEW = "/home.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect(request.getContextPath() + "/signin");
            return;
        }

        String email = (String) session.getAttribute("email");
        UserEntity user = null;
        try {
            user = userRepo.findByEmail(email).get();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<TicketEntity> tickets = ticketService.getTicketsByUserId(user.getUserId());
        System.out.println(tickets);
        request.setAttribute("tickets", tickets);

        request.getRequestDispatcher(HOME_VIEW).forward(request, response);
    }
}