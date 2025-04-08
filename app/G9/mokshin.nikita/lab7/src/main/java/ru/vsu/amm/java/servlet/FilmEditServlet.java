package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.exception.InvalidParameterException;
import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.Film;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.model.User;
import ru.vsu.amm.java.repository.FilmRepository;
import ru.vsu.amm.java.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/films/edit")
public class FilmEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FilmEditServlet.class);
    private FilmRepository filmRepository;
    private GenreRepository genreRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("FilmEditServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /films/edit with parameters: {}", req.getParameterMap());

        User user = (User) req.getSession(false).getAttribute("user");
        req.setAttribute("action", "/films/edit");
        req.setAttribute("h1", "Edit film");
        req.setAttribute("button", "Edit");
        List<Genre> genres = genreRepository.findAll();
        req.setAttribute("genres", genres);

        long filmId;
        Film film;
        try {
            filmId = Long.parseLong(req.getParameter("id"));
            req.setAttribute("action", "/films/edit?id=" + filmId);
            film = filmRepository.findById(filmId).orElseThrow(() -> new EntityNotFoundException("Film not found with id =" + filmId));
            if (!user.getId().equals(film.getAuthorOptional().get().getId())) {
                throw new ForbiddenException("This user cannot edit film with id =" + filmId);
            }
            req.setAttribute("film", film);

            logger.info("Film {} loaded successfully for editing", filmId);
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
        req.getRequestDispatcher("/FilmEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request to /films/edit with parameters: {}", req.getParameterMap());

        User user = (User) req.getSession(false).getAttribute("user");
        long filmId;
        Film film;
        try {
            filmId = Long.parseLong(req.getParameter("id"));
            film = filmRepository.findById(filmId).orElseThrow(() -> new EntityNotFoundException("Film not found with id =" + filmId));

            if(!user.getId().equals(film.getAuthorOptional().get().getId())) {
                throw new ForbiddenException("This user cannot edit film with id =" + filmId);
            }

            String title = req.getParameter("title");
            if (title == null || title.isBlank()) {
                throw new InvalidParameterException("title is null or empty");
            }

            String slogan = req.getParameter("slogan");
            if (slogan == null || slogan.isBlank()) {
                throw new InvalidParameterException("slogan is null or empty");
            }

            String description = req.getParameter("description");
            if (description == null || description.isBlank()) {
                throw new InvalidParameterException("description is null or empty");
            }

            String releaseDateString = req.getParameter("releaseDate");
            if (releaseDateString == null || releaseDateString.isBlank()) {
                throw new InvalidParameterException("releaseDate is null or empty");
            }

            LocalDate releaseDate;
            try {
                releaseDate = LocalDate.parse(releaseDateString);
            } catch (Exception e) {
                throw new InvalidParameterException("releaseDate is not a valid date");
            }

            long genre_id;
            try {
                genre_id = Long.parseLong(req.getParameter("genre_id"));
            } catch (NumberFormatException e) {
                throw new InvalidParameterException("genre_id is null or empty");
            }

            Genre genre = genreRepository.findById(genre_id).orElseThrow(() -> new EntityNotFoundException("Genre not found"));

            film.setTitle(title);
            film.setSlogan(slogan);
            film.setDescription(description);
            film.setReleaseDate(releaseDate);
            film.setGenreOptional(Optional.of(genre));
            filmRepository.save(film);

            logger.info("Film '{}' edited successfully", film);
        } catch (InvalidParameterException | SqlException | EntityNotFoundException e) {
            logger.info("Error editing film");

            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/FilmEdit.jsp").forward(req, resp);
            return;
        } catch (ForbiddenException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        resp.sendRedirect("/films");
    }
}
