package ru.vsu.amm.java.Servlet;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Entity.FilmEntity;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Entity.UserEntity;
import ru.vsu.amm.java.Model.Enum.TicketStatus;
import ru.vsu.amm.java.Repository.Impl.UserRepo;
import ru.vsu.amm.java.Service.Impl.DefaultFilmServiceImpl;
import ru.vsu.amm.java.Service.Impl.DefaultTicketServiceImpl;
import ru.vsu.amm.java.Service.Interface.FilmService;
import ru.vsu.amm.java.Service.Interface.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "TicketServlet", urlPatterns = "/ticket")
@Slf4j
public class TicketServlet extends HttpServlet {

    private final TicketService ticketService;
    private final FilmService filmService;
    private final UserRepo userRepo;

    private static final String ERROR_MESSAGE = "error";
    private static final String PAGE = "/ticket.jsp";
    private static final String SUCCESS_PAGE = "/ticketSuccess.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_PURCHASE_MESSAGE = "Ошибка при покупке билета: ";
    private static final String ERROR_INVALID_DATA = "Неверные данные";

    public TicketServlet() {
        this.ticketService = new DefaultTicketServiceImpl();
        this.filmService = new DefaultFilmServiceImpl();
        this.userRepo = new UserRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем список фильмов для выбора
        List<FilmEntity> films = filmService.getAllFilms();
        req.setAttribute("films", films);

        // Проверяем авторизацию
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }

        req.getRequestDispatcher(PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + "/signin");
            return;
        }

        String email = (String) session.getAttribute("email");

        try {
            // Получаем пользователя по email
            Optional<UserEntity> user = userRepo.findByEmail(email);
            if (user == null) {
                req.setAttribute(ERROR_MESSAGE, "Пользователь не найден");
                req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
                return;
            }

            long filmId = Long.parseLong(req.getParameter("filmId"));
            int hallNumber = Integer.parseInt(req.getParameter("hallNumber"));
            int placeNumber = Integer.parseInt(req.getParameter("placeNumber"));
            OffsetDateTime startTime = LocalDateTime.parse(req.getParameter("startTime")).atOffset(ZoneOffset.UTC);
            OffsetDateTime endTime = LocalDateTime.parse(req.getParameter("endTime")).atOffset(ZoneOffset.UTC);
            String film = filmService.getFilmById(filmId).get().getName();
            ticketService.addTicket(filmId,hallNumber,placeNumber,startTime,endTime,user.get().getUserId(),film);

            resp.sendRedirect(req.getContextPath() + SUCCESS_PAGE);

        } catch (DbException e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_PURCHASE_MESSAGE + e.getMessage());
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_INVALID_DATA);
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }
    }
}