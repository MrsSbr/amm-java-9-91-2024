package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;
import ru.vsu.amm.java.requests.BookUpdate.DownloadBookUpdateRequest;
import ru.vsu.amm.java.services.CreateService;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@WebServlet("/create")
public class CreateBookServlet extends HttpServlet {
    private final CreateService createService;

    public CreateBookServlet(CreateService createService) {
        this.createService = createService;
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

            DownloadBookUpdateRequest downloadUpdateRequest = new DownloadBookUpdateRequest(
                    Integer.parseInt(request.getParameter("userId")),
                    downloadRequest,
                    LocalDateTime.parse(request.getParameter("updateTime")),
                    UpdateType.valueOf(request.getParameter("updateType"))
            );

            createService.createBook(downloadUpdateRequest);
            response.sendRedirect("books.jsp");
        } catch (IllegalArgumentException
                 | NoSuchElementException
                 | InstanceAlreadyExistsException e) {
            response.sendRedirect(String.format("%s", e.getMessage())); // TODO: связать с jsp
        }
    }
}
