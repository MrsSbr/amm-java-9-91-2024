package ru.vsu.amm.java.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet(name = "NewNoteServlet", urlPatterns = "/notes/new")
public class NewNoteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NewNoteServlet.class);
    private NoteService noteService;

    @Override
    public void init() {
        this.noteService = new NoteService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            logger.info("Displaying new note form for user: {}", username);
            req.getRequestDispatcher("/new-note.jsp").forward(req, resp);

        } catch (Exception e) {
            logger.error("Error displaying new note form", e);
            req.setAttribute("error", "Error loading form");
            req.getRequestDispatcher("/notes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            String content = req.getParameter("content");

            if (content == null || content.trim().isEmpty()) {
                logger.warn("Empty content submitted for new note by user: {}", username);
                req.setAttribute("error", "Note content cannot be empty");
                doGet(req, resp);
                return;
            }

            long noteId = noteService.createNote(username, content);
            logger.info("Successfully created new note with ID: {} for user: {}", noteId, username);

            resp.sendRedirect("/note?id=" + noteId);

        } catch (DatabaseException | SQLException e) {
            logger.error("Error creating new note", e);
            req.setAttribute("error", "Error creating note");
            doGet(req, resp);
        }
    }
}