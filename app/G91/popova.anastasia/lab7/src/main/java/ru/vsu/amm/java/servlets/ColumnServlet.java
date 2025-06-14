package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final ColumnService columnService = new ColumnService(new ColumnRepository());
    private final BoardService boardService = new BoardService(new BoardRepository());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/create")) {
            String boardIdStr = req.getParameter("boardId");
            String title = req.getParameter("title");
            if (boardIdStr == null || title == null || title.trim().isEmpty()) {
                req.setAttribute("error", "ID доски и заголовок колонки обязательны");
                req.getRequestDispatcher("/WEB-INF/views/columns/create.jsp").forward(req, resp);
                return;
            }
            UUID boardId = UUID.fromString(boardIdStr);
            Board board = boardService.getBoardByID(boardId);
            if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            try {
                columnService.createColumn(boardId, title.trim());
                resp.sendRedirect("/boards/" + boardId);
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/columns/create.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID columnId = UUID.fromString(parts[1]);
                Column column = columnService.getColumnByID(columnId);
                if (column == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                Board board = boardService.getBoardByID(column.getBoardID());
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                String newTitle = req.getParameter("title");
                try {
                    if (newTitle != null && !newTitle.trim().isEmpty()) {
                        columnService.updateColumnTitle(columnId, newTitle.trim());
                    }
                    resp.sendRedirect("/boards/" + board.getBoardID());
                } catch (Exception e) {
                    req.setAttribute("error", e.getMessage());
                    req.setAttribute("column", column);
                    req.getRequestDispatcher("/WEB-INF/views/columns/edit.jsp").forward(req, resp);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID columnId = UUID.fromString(parts[1]);
                Column column = columnService.getColumnByID(columnId);
                if (column == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                Board board = boardService.getBoardByID(column.getBoardID());
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                try {
                    columnService.deleteColumn(columnId);
                    resp.sendRedirect("/boards/" + board.getBoardID());
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}