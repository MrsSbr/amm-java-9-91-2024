package ru.vsu.amm.java.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet(name = "NoteServlet", urlPatterns = "/note")
public class NoteServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(NoteServlet.class);
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

            String noteIdParam = req.getParameter("id");
            if (noteIdParam == null || noteIdParam.isEmpty()) {
                resp.sendRedirect("/notes");
                return;
            }

            long noteId = Long.parseLong(noteIdParam);
            Note note = noteService.getNoteById(noteId, username);

            if (note == null) {
                req.setAttribute("error", "Note not found or access denied");
                req.getRequestDispatcher("/notes.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("note", note);
            req.getRequestDispatcher("/note.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            logger.error("Invalid note ID parameter", e);
            resp.sendRedirect("/notes");
        } catch (DatabaseException | SQLException e) {
            logger.error("Error accessing note", e);
            req.setAttribute("error", "Error loading note");
            req.getRequestDispatcher("/notes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            String action = req.getParameter("action");
            String noteIdParam = req.getParameter("id");
            String content = req.getParameter("content");

            if (noteIdParam == null || noteIdParam.isEmpty()) {
                resp.sendRedirect("/notes");
                return;
            }

            long noteId = Long.parseLong(noteIdParam);
            Note note = noteService.getNoteById(noteId, username);

            if ("delete".equals(action)) {
                noteService.deleteNote(noteId, username);
                resp.sendRedirect("/notes");
            } else if ("save".equals(action)) {
                if (content == null || content.trim().isEmpty()) {
                    req.setAttribute("error", "Note content cannot be empty");
                    req.setAttribute("note", note);
                    req.getRequestDispatcher("/note.jsp").forward(req, resp);
                    return;
                }
                noteService.updateNoteContent(noteId, content, username);
                resp.sendRedirect("/note?id=" + noteId);
            }

        } catch (NumberFormatException e) {
            logger.error("Invalid note ID parameter", e);
            resp.sendRedirect("/notes");
        } catch (DatabaseException | SQLException e) {
            logger.error("Error processing note", e);
            req.setAttribute("error", "Error processing your request");
            req.getRequestDispatcher("/note.jsp").forward(req, resp);
        }
    }
}