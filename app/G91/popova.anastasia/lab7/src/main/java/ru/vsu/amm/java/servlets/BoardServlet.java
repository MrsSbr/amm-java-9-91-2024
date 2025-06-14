package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.service.BoardService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/boards/*")
public class BoardServlet extends HttpServlet {
    private final BoardService boardService = new BoardService(new BoardRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Показать все доски пользователя
            List<Board> boards = boardService.getAllBoards().stream()
                    .filter(board -> board.getUserID().equals(currentUser.getUserID()))
                    .toList();
            req.setAttribute("boards", boards);
            req.getRequestDispatcher("/WEB-INF/views/boards/list.jsp").forward(req, resp);
        } else {
            // Показать конкретную доску
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID boardId = UUID.fromString(parts[1]);
                Board board = boardService.getBoardByID(boardId);
                if (board != null && board.getUserID().equals(currentUser.getUserID())) {
                    req.setAttribute("board", board);
                    req.getRequestDispatcher("/WEB-INF/views/boards/view.jsp").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/create")) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            if (title == null || title.trim().isEmpty()) {
                req.setAttribute("error", "Заголовок доски обязателен");
                req.getRequestDispatcher("/WEB-INF/views/boards/create.jsp").forward(req, resp);
                return;
            }
            try {
                boardService.createBoard(currentUser.getUserID(), title.trim(), description);
                resp.sendRedirect("/boards");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/boards/create.jsp").forward(req, resp);
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
                UUID boardId = UUID.fromString(parts[1]);
                Board board = boardService.getBoardByID(boardId);
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                String newTitle = req.getParameter("title");
                String newDescription = req.getParameter("description");
                try {
                    if (newTitle != null && !newTitle.trim().isEmpty()) {
                        boardService.updateBoardTitle(boardId, newTitle.trim());
                    }
                    if (newDescription != null) {
                        boardService.updateBoardDescription(boardId, newDescription);
                    }
                    resp.sendRedirect("/boards/" + boardId);
                } catch (Exception e) {
                    req.setAttribute("error", e.getMessage());
                    req.setAttribute("board", board);
                    req.getRequestDispatcher("/WEB-INF/views/boards/edit.jsp").forward(req, resp);
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
                UUID boardId = UUID.fromString(parts[1]);
                Board board = boardService.getBoardByID(boardId);
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                try {
                    boardService.deleteBoard(boardId);
                    resp.sendRedirect("/boards");
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