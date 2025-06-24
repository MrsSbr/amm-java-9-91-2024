package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/columns/*")
public class ColumnServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger((ColumnServlet.class));

    private final ColumnService columnService = new ColumnService(new ColumnRepository());
    private final BoardService boardService = new BoardService(new BoardRepository(), new ColumnRepository());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing POST request to /columns{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/create")) {
            handleColumnCreate(req, resp, currentUser);
        } else {
            log.warn("Invalid POST request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing PUT request to /columns{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            log.warn("Invalid PUT request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            log.warn("Invalid PUT request path format: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleColumnUpdate(req, resp, currentUser, parts[1]);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing DELETE request to /columns{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            log.warn("Invalid DELETE request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            log.warn("Invalid DELETE request path format: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleColumnDelete(req, resp, currentUser, parts[1]);
    }

    private User getCurrentUserOrRedirect(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            log.warn("Unauthorized access attempt to /columns{}", req.getPathInfo());
            resp.sendRedirect("/auth/login");
        }
        return currentUser;
    }

    private void handleColumnCreate(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String boardIdStr = req.getParameter("boardId");
        String title = req.getParameter("title");

        if (boardIdStr == null || title == null || title.trim().isEmpty()) {
            log.warn("Attempt to create column with missing parameters by user: {}", user.getUserID());
            req.setAttribute("error", "ID доски и заголовок колонки обязательны");
            req.getRequestDispatcher("/WEB-INF/views/columns/create.jsp").forward(req, resp);
            return;
        }

        try {
            UUID boardId = UUID.fromString(boardIdStr);
            Board board = boardService.getBoardByID(boardId);

            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Create column denied - Board: {}, User: {}", boardId, user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            log.debug("Creating new column for board: {}", boardId);
            columnService.createColumn(boardId, title.trim());
            log.info("Successfully created column for board: {}", boardId);
            resp.sendRedirect("/boards/" + boardId);
        } catch (RuntimeException e) {
            log.error("Error creating column: {}", e.getMessage(), e);
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/columns/create.jsp").forward(req, resp);
        }
    }

    private void handleColumnUpdate(HttpServletRequest req, HttpServletResponse resp,
                                    User user, String columnIdStr)
            throws ServletException, IOException {
        try {
            UUID columnId = UUID.fromString(columnIdStr);
            log.debug("Attempting to update column: {}", columnId);

            Column column = columnService.getColumnByID(columnId);
            if (column == null) {
                log.warn("Column not found: {}", columnId);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Board board = boardService.getBoardByID(column.getBoardID());
            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Update column denied - Board: {}, User: {}", column.getBoardID(), user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            String newTitle = req.getParameter("title");
            if (newTitle != null && !newTitle.trim().isEmpty()) {
                log.debug("Updating title for column: {}", columnId);
                columnService.updateColumnTitle(columnId, newTitle.trim());
                log.info("Successfully updated column: {}", columnId);
            }

            resp.sendRedirect("/boards/" + board.getBoardID());
        } catch (RuntimeException e) {
            log.error("Error updating column {}: {}", columnIdStr, e.getMessage(), e);
            Column column = columnService.getColumnByID(UUID.fromString(columnIdStr));
            req.setAttribute("error", e.getMessage());
            req.setAttribute("column", column);
            req.getRequestDispatcher("/WEB-INF/views/columns/edit.jsp").forward(req, resp);
        }
    }

    private void handleColumnDelete(HttpServletRequest req, HttpServletResponse resp,
                                    User user, String columnIdStr) throws IOException {
        try {
            UUID columnId = UUID.fromString(columnIdStr);
            log.debug("Attempting to delete column: {}", columnId);

            Column column = columnService.getColumnByID(columnId);
            if (column == null) {
                log.warn("Column not found: {}", columnId);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Board board = boardService.getBoardByID(column.getBoardID());
            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Delete column denied - Board: {}, User: {}", column.getBoardID(), user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            columnService.deleteColumn(columnId);
            log.info("Successfully deleted column: {}", columnId);
            resp.sendRedirect("/boards/" + board.getBoardID());
        } catch (RuntimeException e) {
            log.error("Error deleting column {}: {}", columnIdStr, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}