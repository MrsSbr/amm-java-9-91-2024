package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;
import ru.vsu.amm.java.requests.BookUpdate.DownloadBookUpdateRequest;
import ru.vsu.amm.java.services.CreateService;

import javax.management.InstanceAlreadyExistsException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@WebServlet("/create")
public class CreateBookServlet extends HttpServlet {
    private final CreateService createService;

    public CreateBookServlet() {
        this.createService = new CreateService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookTypes", BookType.values());
        request.getRequestDispatcher("/addBook.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectPath = "read";
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
                    Integer.parseInt(request.getParameter("Id_user")),
                    downloadRequest,
                    LocalDateTime.now(),
                    UpdateType.DOWNLOAD
            );
            createService.createBook(downloadUpdateRequest);
            String redirectUrl = request.getContextPath()
                                 + redirectPath + "?message="
                                 + URLEncoder.encode("Книга успешно добавлена!", StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (IllegalArgumentException
                 | NoSuchElementException
                 | InstanceAlreadyExistsException e) {
            String redirectUrl = request.getContextPath()
                                 + redirectPath
                                 + String.format("?error=%s", e.getMessage());
            response.sendRedirect(redirectUrl);
        }
    }
}
