package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.InvalidParameterException;
import ru.vsu.amm.java.model.Genre;
import ru.vsu.amm.java.repository.GenreRepository;

import java.io.IOException;

@WebServlet("/genres/edit")
public class GenreEditServlet extends HttpServlet {
    private GenreRepository genreRepository;

    @Override
    public void init() {
        genreRepository = (GenreRepository) getServletContext().getAttribute("genreRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("h1", "Edit Genre");
        req.setAttribute("button", "Edit");
        long genreId;
        Genre genre;
        try {
            genreId = Long.parseLong(req.getParameter("id"));
            req.setAttribute("action", "/genres/edit?id=" + genreId);
            genre = genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre not found with id =" + genreId));
            req.setAttribute("title", genre.getTitle());
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.getRequestDispatcher("/GenreEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
