package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.model.Film;
import ru.vsu.amm.java.model.User;
import ru.vsu.amm.java.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/films/delete")
public class FilmDeleteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FilmDeleteServlet.class);
    private FilmRepository filmRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
        logger.info("FilmDeleteServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received GET request to /films/delete with parameters: {}", req.getParameterMap());

        User user = (User) req.getSession().getAttribute("user");
        try {
            int filmId = Integer.parseInt(req.getParameter("id"));

            logger.info("Attempting to delete film with id = {}", filmId);

            Film film = filmRepository.findById(filmId).orElseThrow(()-> new EntityNotFoundException("Film not found with id = " + filmId));
            if (!user.getId().equals(film.getAuthorOptional().get().getId())) {
                throw new ForbiddenException("This user cannot edit film with id =" + filmId);
            }

            filmRepository.delete(film);
            logger.info("Film with id = {} deleted successfully", filmId);
        } catch (NumberFormatException e) {
            logger.info("Invalid film id: {}", req.getParameter("id"), e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            logger.info("Film not found");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (ForbiddenException e) {
            logger.info("Access denied user {}", user);
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        resp.sendRedirect("/films");
    }
}
