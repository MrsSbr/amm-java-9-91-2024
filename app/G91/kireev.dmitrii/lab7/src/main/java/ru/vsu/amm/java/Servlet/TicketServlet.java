package ru.vsu.amm.java.Servlet;

import lombok.RequiredArgsConstructor;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Enum.TicketStatus;
import ru.vsu.amm.java.Service.Interface.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@WebServlet(name = "TicketServlet", urlPatterns = "/ticket")
@RequiredArgsConstructor
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService;

    private static final String ERROR_MESSAGE = "error";
    private static final String SUCCESS_PAGE = "/ticketSuccess.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_PURCHASE_MESSAGE = "Ошибка при покупке билета: ";
    private static final String ERROR_INVALID_DATA = "Неверные данные";
    private static final String ERROR_DB_CANCEL_TICKET = "Ошибка БД при отмене билета";
    private static final String ERROR_BAD_REQUEST_CANCEL_TICKET = "Неверный запрос для отмены билета";

    private static final String FILM_ID_PARAM = "filmId";
    private static final String USER_ID_PARAM = "userId";
    private static final String HALL_NUMBER_PARAM = "hallNumber";
    private static final String PLACE_NUMBER_PARAM = "placeNumber";
    private static final String START_TIME_PARAM = "startTime";
    private static final String END_TIME_PARAM = "endTime";
    private static final String TICKET_ID_PARAM = "ticketId";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long filmId = Long.parseLong(req.getParameter(FILM_ID_PARAM));
            long userId = Long.parseLong(req.getParameter(USER_ID_PARAM));
            int hallNumber = Integer.parseInt(req.getParameter(HALL_NUMBER_PARAM));
            int placeNumber = Integer.parseInt(req.getParameter(PLACE_NUMBER_PARAM));
            OffsetDateTime startTime = LocalDateTime.parse(req.getParameter(START_TIME_PARAM)).atOffset(ZoneOffset.UTC);
            OffsetDateTime endTime = LocalDateTime.parse(req.getParameter(END_TIME_PARAM)).atOffset(ZoneOffset.UTC);

            TicketEntity ticket = new TicketEntity();
            ticket.setFilmId(filmId);
            ticket.setUserId(userId);
            ticket.setHallNumber(hallNumber);
            ticket.setPlaceNumber(placeNumber);
            ticket.setStartTime(startTime);
            ticket.setEndTime(endTime);
            ticket.setStatus(TicketStatus.PAID);

            ticketService.addTicket(ticket);
            resp.sendRedirect(SUCCESS_PAGE);

        } catch (DbException e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_PURCHASE_MESSAGE + e.getMessage());
            getServletContext().getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_INVALID_DATA);
            getServletContext().getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long ticketId = Long.parseLong(req.getParameter(TICKET_ID_PARAM));
            ticketService.deleteTicket(ticketId);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (DbException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ERROR_DB_CANCEL_TICKET);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ERROR_BAD_REQUEST_CANCEL_TICKET);
        }
    }
}
