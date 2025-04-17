package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;
import ru.vsu.amm.java.requests.BookUpdate.ChangeBookUpdateRequest;
import ru.vsu.amm.java.services.UpdateService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@WebServlet("/update")
public class UpdateBookServlet extends HttpServlet {
    private final UpdateService updateService;

    public UpdateBookServlet(UpdateService updateService) {
        this.updateService = updateService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            DownloadBookRequest downloadRequest = new DownloadBookRequest(
                    request.getParameter("title"),
                    request.getParameter("author"),
                    request.getParameter("publisher"),
                    Integer.parseInt(request.getParameter("publishedYear")),
                    Integer.parseInt(request.getParameter("numberOfPages")),
                    BookType.valueOf(request.getParameter("bookType"))
            );

            ChangeBookUpdateRequest downloadUpdateRequest = new ChangeBookUpdateRequest(
                    Integer.parseInt(request.getParameter("userId")),
                    Integer.parseInt(request.getParameter("bookId")),
                    downloadRequest,
                    LocalDateTime.parse(request.getParameter("updateTime")),
                    UpdateType.valueOf(request.getParameter("updateType"))
            );

            updateService.updateBook(downloadUpdateRequest);
            response.sendRedirect("books.jsp");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            response.sendRedirect(String.format("%s", e.getMessage())); // TODO: связать с jsp
        }
    }
}
