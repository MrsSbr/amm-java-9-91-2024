package ru.vsu.amm.java.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.entities.Note;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.NoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "NotesServlet", urlPatterns = "/notes")
public class NotesServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NotesServlet.class);
    private NoteService noteService;

    @Override
    public void init() {
        logger.info("Initializing NotesServlet");
        this.noteService = new NoteService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Processing GET request for /notes");

        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                logger.error("Username not found in session");
                resp.sendRedirect("/login");
                return;
            }

            long userId = noteService.getUserIdByUsername(username);
            String categoryIdParam = req.getParameter("categoryId");
            Long categoryId = null;

            if (categoryIdParam != null && !categoryIdParam.isEmpty() && !categoryIdParam.equals("all")) {
                try {
                    categoryId = Long.parseLong(categoryIdParam);
                } catch (NumberFormatException e) {
                    logger.error("Invalid category ID parameter: {}", categoryIdParam);
                }
            }

            List<Note> notes;
            if (categoryId != null) {
                notes = noteService.getNotesByCategory(userId, categoryId);
            } else {
                notes = noteService.getUserNotes(username);
            }

            List<Category> categories = noteService.getUserCategories(username);

            req.setAttribute("notes", notes);
            req.setAttribute("categories", categories);
            req.setAttribute("selectedCategoryId", categoryId);

            req.getRequestDispatcher("/notes.jsp").forward(req, resp);

        } catch (DatabaseException e) {
            logger.error("Database error: {}", e.getMessage());
            req.setAttribute("error", "Error loading notes");
            req.getRequestDispatcher("/notes.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}