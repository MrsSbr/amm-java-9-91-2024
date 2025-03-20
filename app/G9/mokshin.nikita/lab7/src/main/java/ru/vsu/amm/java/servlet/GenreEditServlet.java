package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.InvalidParameterException;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.io.IOException;

@WebServlet("/genres/edit")
public class GenreEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GenreEditServlet.class);
    private GenreRepository genreRepository;

    @Override
    public void init() {
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("GenreEditServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /genres/edit with parameters: {}", req.getParameterMap());

        req.setAttribute("h1", "Edit Genre");
        req.setAttribute("button", "Edit");
        long genreId;
        Genre genre;
        try {
            genreId = Long.parseLong(req.getParameter("id"));
            req.setAttribute("action", "/genres/edit?id=" + genreId);
            genre = genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre not found with id =" + genreId));
            req.setAttribute("title", genre.getTitle());

            logger.info("Genre {} loaded successfully for editing", genreId);
        } catch (NumberFormatException e) {
            logger.info("Invalid genre id: {}", req.getParameter("id"), e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            logger.info("Genre not found");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.getRequestDispatcher("/GenreEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received POST request to /genres/edit with parameters: {}", req.getParameterMap());

        long genreId;
        Genre genre;
        try {
            genreId = Long.parseLong(req.getParameter("id"));
            genre = genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre not found with id =" + genreId));

            String title = req.getParameter("title");
            if (title == null || title.isBlank()) {
                throw new InvalidParameterException("title is null or empty");
            }

            genre.setTitle(title);
            genreRepository.save(genre);
            logger.info("Genre '{}' edited successfully", genre);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (InvalidParameterException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/GenreEdit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/genres");
    }
}
