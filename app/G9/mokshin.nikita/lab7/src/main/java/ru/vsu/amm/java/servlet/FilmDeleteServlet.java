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

import java.io.IOException;

@WebServlet("/films/delete")
public class FilmDeleteServlet extends HttpServlet {
    private FilmRepository filmRepository;

    @Override
    public void init() {
        filmRepository = (FilmRepository) getServletContext().getAttribute("filmRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            int filmId = Integer.parseInt(req.getParameter("id"));
            Film film = filmRepository.findById(filmId).orElseThrow(()-> new EntityNotFoundException("Film not found with id = " + filmId));

            if (!user.getId().equals(film.getAuthorOptional().get().getId())) {
                throw new ForbiddenException("This user cannot edit film with id =" + filmId);
            }

            filmRepository.delete(film);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (ForbiddenException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        resp.sendRedirect("/films");
    }
}
