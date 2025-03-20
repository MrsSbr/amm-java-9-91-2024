package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.model.Film;
import ru.vsu.amm.java.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/films")
public class FilmServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(FilmServlet.class);
    private FilmRepository filmRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
        logger.info("FilmServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("Received GET request to /films");

        List<Film> films = filmRepository.findAll();
        req.setAttribute("films", films);
        req.getRequestDispatcher("/films.jsp").forward(req, resp);
    }
}
