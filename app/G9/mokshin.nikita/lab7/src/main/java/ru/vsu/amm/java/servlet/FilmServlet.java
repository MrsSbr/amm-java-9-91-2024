package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.model.Film;
import ru.vsu.amm.java.repository.FilmRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/films")
public class FilmServlet extends HttpServlet {
    private FilmRepository filmRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Film> films = filmRepository.findAll();
        req.setAttribute("films", films);
        req.getRequestDispatcher("/films.jsp").forward(req, resp);
    }
}
