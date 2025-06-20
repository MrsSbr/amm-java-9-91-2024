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
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.TaskService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/boards/*")
public class BoardServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger((BoardServlet.class));

    private final BoardService boardService = new BoardService(new BoardRepository());
    private final ColumnService columnService = new ColumnService(new ColumnRepository());
    private final TaskService taskService = new TaskService(new TaskRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Processing GET request to /boards{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            handleBoardList(req, resp, currentUser);
        } else if (pathInfo.equals("/create")) {
            handleBoardCreate(req, resp);
        } else if (pathInfo.endsWith("/edit")) {
            handleBoardEdit(req, resp, currentUser);
        } else {
            handleBoardView(req, resp, currentUser);
        }
    }

    private User getCurrentUserOrRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            log.warn("Unauthorized access attempt to /boards{}", req.getPathInfo());
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
        return currentUser;
    }

    private void handleBoardList(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        log.debug("Fetching boards list for user: {}", user.getUserID());
        List<Board> boards = boardService.getAllBoards().stream()
                .filter(board -> board.getUserID().equals(user.getUserID()))
                .toList();
        log.info("Fetched {} boards for user: {}", boards.size(), user.getUserID());
        req.setAttribute("boards", boards);
        req.getRequestDispatcher("/WEB-INF/views/boards/list.jsp").forward(req, resp);
    }

    private void handleBoardCreate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/boards/create.jsp").forward(req, resp);
    }

    private void handleBoardEdit(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String[] parts = req.getPathInfo().split("/");
        if (parts.length != 3) {
            log.warn("Invalid path for board edit: {}", req.getPathInfo());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UUID boardId = UUID.fromString(parts[1]);
        log.debug("Attempting to edit board: {}", boardId);
        Board board = getBoardIfAccessible(boardId, user);
        if (board == null) {
            log.warn("Board not found or access denied: {}", boardId);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        req.setAttribute("board", board);
        req.setAttribute("referer", req.getParameter("referer"));
        req.getRequestDispatcher("/WEB-INF/views/boards/edit.jsp").forward(req, resp);
    }

    private void handleBoardView(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String[] parts = req.getPathInfo().split("/");
        if (parts.length != 2) {
            log.warn("Invalid path for board view: {}", req.getPathInfo());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UUID boardId = UUID.fromString(parts[1]);
        Board board = getBoardIfAccessible(boardId, user);
        if (board == null) {
            log.warn("Board not found or access denied: {}", boardId);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        log.debug("Fetching details for board: {}", boardId);
        req.setAttribute("board", board);

        List<Column> columns = columnService.getColumnsByBoard(boardId);
        Map<UUID, List<Task>> tasksByColumn = columns.stream()
                .collect(Collectors.toMap(
                        Column::getColumnID,
                        column -> taskService.getTasksByColumn(column.getColumnID())
                ));

        log.info("Fetched {} columns for board: {}", columns.size(), boardId);
        req.setAttribute("columns", columns);
        req.setAttribute("tasksByColumn", tasksByColumn);
        req.getRequestDispatcher("/WEB-INF/views/boards/view.jsp").forward(req, resp);
    }

    private Board getBoardIfAccessible(UUID boardId, User user) {
        Board board = boardService.getBoardByID(boardId);
        if (board == null || !board.getUserID().equals(user.getUserID())) {
            log.warn("Board not found or access denied for board: {}, user: {}", boardId, user.getUserID());
            return null;
        }
        return board;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing POST request to /boards{}", req.getPathInfo());
        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/create")) {
            handleBoardCreate(req, resp, currentUser);
        } else {
            log.warn("Invalid POST request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing PUT request to /boards{}", req.getPathInfo());
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

        handleBoardUpdate(req, resp, currentUser, parts[1]);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("Processing DELETE request to /boards{}", req.getPathInfo());
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

        handleBoardDelete(req, resp, currentUser, parts[1]);
    }

    private void handleBoardCreate(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        if (title == null || title.trim().isEmpty()) {
            log.warn("Attempt to create board with empty title by user: {}", user.getUserID());
            req.setAttribute("error", "Заголовок доски обязателен");
            req.getRequestDispatcher("/WEB-INF/views/boards/create.jsp").forward(req, resp);
            return;
        }

        try {
            log.debug("Creating new board for user: {}", user.getUserID());
            boardService.createBoard(user.getUserID(), title.trim(), description);
            log.info("Successfully created board for user: {}", user.getUserID());
            resp.sendRedirect(req.getContextPath() + "/boards");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/boards/create.jsp").forward(req, resp);
        }
    }

    private void handleBoardUpdate(HttpServletRequest req, HttpServletResponse resp,
                                   User user, String boardIdStr)
            throws ServletException, IOException {
        UUID boardId = UUID.fromString(boardIdStr);
        log.debug("Attempting to update board: {}", boardId);
        Board board = boardService.getBoardByID(boardId);

        if (board == null || !board.getUserID().equals(user.getUserID())) {
            log.warn("Board not found or access for update denied" +
                    "for board: {}, user: {}", boardId, user.getUserID());
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
            log.info("Successfully updated board: {}", boardId);
            resp.sendRedirect(req.getContextPath() + "/boards/" + boardId);
        } catch (Exception e) {
            log.error("Error updating board {}: {}", boardId, e.getMessage(), e);
            req.setAttribute("error", e.getMessage());
            req.setAttribute("board", board);
            req.getRequestDispatcher("/WEB-INF/views/boards/edit.jsp").forward(req, resp);
        }
    }

    private void handleBoardDelete(HttpServletRequest req, HttpServletResponse resp,
                                   User user, String boardIdStr)
            throws IOException {
        UUID boardId = UUID.fromString(boardIdStr);
        log.debug("Attempting to delete board: {}", boardId);
        Board board = boardService.getBoardByID(boardId);

        if (board == null || !board.getUserID().equals(user.getUserID())) {
            log.warn("Board not found or access for delete denied" +
                    "for board: {}, user: {}", boardId, user.getUserID());
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            boardService.deleteBoard(boardId);
            log.info("Successfully deleted board: {}", boardId);
            resp.sendRedirect(req.getContextPath() + "/boards");
        } catch (Exception e) {
            log.error("Error deleting board {}: {}", boardId, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}