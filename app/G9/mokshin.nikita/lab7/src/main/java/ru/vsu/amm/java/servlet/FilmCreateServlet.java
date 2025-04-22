package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.exception.EntityNotFoundException;
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

@WebServlet("/films/create")
public class FilmCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FilmCreateServlet.class);
    private FilmRepository filmRepository;
    private GenreRepository genreRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("FilmCreateServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /films/create");

        req.setAttribute("action", "/films/create");
        req.setAttribute("h1", "Create film");
        req.setAttribute("button", "Create");
        List<Genre> genres = genreRepository.findAll();
        req.setAttribute("genres", genres);
        req.getRequestDispatcher("/FilmEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request to /films/create");

        try {
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
            User author = (User) req.getSession(false).getAttribute("user");

            Film film = new Film(title, slogan, description, releaseDate, genre, author);
            filmRepository.save(film);

            logger.info("Film '{}' created successfully", film);
        } catch (InvalidParameterException | SqlException | EntityNotFoundException e) {
            logger.info("Error creating film: {}", e.getMessage());

            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/FilmEdit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/films");
    }
}
