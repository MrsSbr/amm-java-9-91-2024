package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.io.IOException;

@WebServlet("/genres/delete")
public class GenreDeleteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GenreDeleteServlet.class);
    private GenreRepository genreRepository;

    @Override
    public void init() {
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("GenreDeleteServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Received GET request to /genres/delete with parameters: {}", req.getParameterMap());

        try {
            int genreId = Integer.parseInt(req.getParameter("id"));
            Genre genre = genreRepository.findById(genreId).orElseThrow(()-> new EntityNotFoundException("Genre not found with id = " + genreId));
            genreRepository.delete(genre);
            logger.info("Genre with id = {} deleted successfully", genreId);
        } catch (NumberFormatException e) {
            logger.info("Invalid genre id: {}", req.getParameter("id"), e);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            logger.info("Genre not found");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        resp.sendRedirect("/genres");
    }
}
