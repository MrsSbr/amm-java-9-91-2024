package ru.vsu.amm.java.Servlet;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Model.Entity.FilmEntity;
import ru.vsu.amm.java.Service.Impl.DefaultFilmServiceImpl;
import ru.vsu.amm.java.Service.Interface.FilmService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FilmServlet", urlPatterns = "/films")
public class FilmServlet extends HttpServlet {

    private final FilmService filmService;

    private static final String FILMS_PAGE = "/films.jsp";
    private static final String ERROR_MESSAGE = "error";

    private static final String FILM_NAME_PARAM = "name";
    private static final String FILM_GENRE_PARAM = "genre";
    private static final String FILM_DURATION_PARAM = "duration";
    private static final String FILM_SCREENWRITER_PARAM = "screenWriter";
    private static final String FILM_RATING_PARAM = "rating";
    private static final String ACTION_PARAM = "action";
    private static final String FILM_ID_PARAM = "filmId";
    private static final String ADD_ACTION = "add";
    private static final String DELETE_ACTION = "delete";

    private static final String ERROR_FETCHING_FILMS = "Ошибка при получении списка фильмов";
    private static final String ERROR_PROCESSING_FILM = "Ошибка при обработке фильма";

    public FilmServlet() {
        this.filmService = new DefaultFilmServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<FilmEntity> films = filmService.getAllFilms();
            req.setAttribute("films", films);
        } catch (DbException e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_FETCHING_FILMS);
        }

        getServletContext().getRequestDispatcher(FILMS_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ACTION_PARAM);

        try {
            if (ADD_ACTION.equals(action)) {
                FilmEntity film = new FilmEntity();
                film.setName(req.getParameter(FILM_NAME_PARAM));
                film.setGenre(req.getParameter(FILM_GENRE_PARAM));
                film.setDuration(Integer.parseInt(req.getParameter(FILM_DURATION_PARAM)));
                film.setScreenWriter(req.getParameter(FILM_SCREENWRITER_PARAM));
                film.setRating(Double.parseDouble(req.getParameter(FILM_RATING_PARAM)));

                filmService.addFilm(film);
            } else if (DELETE_ACTION.equals(action)) {
                Long filmId = Long.parseLong(req.getParameter(FILM_ID_PARAM));
                filmService.deleteFilm(filmId);
            }
        } catch (DbException | NumberFormatException e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_PROCESSING_FILM);
        }

        resp.sendRedirect(req.getContextPath() + "/films");
    }
}