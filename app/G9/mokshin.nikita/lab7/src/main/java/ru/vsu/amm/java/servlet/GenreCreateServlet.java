package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.InvalidParameterException;
import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.io.IOException;

@WebServlet("/genres/create")
public class GenreCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GenreCreateServlet.class);
    private GenreRepository genreRepository;

    @Override
    public void init() {
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("GenreCreateServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /genres/create");

        req.setAttribute("action", "/genres/create");
        req.setAttribute("h1", "Create genre");
        req.setAttribute("button", "Create");
        req.getRequestDispatcher("/GenreEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request to /genres/create");

        try {
            String title = req.getParameter("title");
            if (title == null || title.isBlank()) {
                throw new InvalidParameterException("title is null or empty");
            }

            Genre genre = new Genre(title);
            genreRepository.save(genre);

            logger.info("Genre '{}' created successfully", title);
        } catch (InvalidParameterException | SqlException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/GenreEdit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/genres");
    }
}
