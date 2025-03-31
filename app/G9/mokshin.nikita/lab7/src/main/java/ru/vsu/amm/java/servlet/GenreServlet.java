package ru.vsu.amm.java.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/genres")
public class GenreServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(GenreServlet.class);
    private GenreRepository genreRepository;

    @Override
    public void init() {
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
        logger.info("GenreServlet initialized");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /genres");

        List<Genre> genres = genreRepository.findAll();
        req.setAttribute("genres", genres);
        req.getRequestDispatcher("/genres.jsp").forward(req, resp);
    }
}
