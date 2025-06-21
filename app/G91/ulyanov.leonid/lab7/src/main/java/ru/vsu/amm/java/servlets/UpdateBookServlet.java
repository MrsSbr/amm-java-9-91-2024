package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;
import ru.vsu.amm.java.requests.BookUpdate.ChangeBookUpdateRequest;
import ru.vsu.amm.java.services.UpdateService;
import ru.vsu.amm.java.repos.BookRepository;

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

@WebServlet("/update")
public class UpdateBookServlet extends HttpServlet {
    private final UpdateService updateService;
    private final BookRepository bookRepository;

    public UpdateBookServlet() {
        this.updateService = new UpdateService();
        this.bookRepository = new BookRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookTypes", BookType.values());
        String bookIdParam = request.getParameter("bookId");
        int bookId = Integer.parseInt(bookIdParam);
        bookRepository.getById(bookId).ifPresent(book -> request.setAttribute("book", book));
        request.getRequestDispatcher("/changeBook.jsp").forward(request, response);
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

            ChangeBookUpdateRequest downloadUpdateRequest = new ChangeBookUpdateRequest(
                    Integer.parseInt(request.getParameter("userId")),
                    Integer.parseInt(request.getParameter("bookId")),
                    downloadRequest,
                    LocalDateTime.now(),
                    UpdateType.CHANGE
            );

            updateService.updateBook(downloadUpdateRequest);
            String redirectUrl = request.getContextPath()
                                 + redirectPath + "?message="
                                 + URLEncoder.encode("Книга успешно изменена!", StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            String redirectUrl = request.getContextPath()
                                 + redirectPath
                                 + String.format("?error=%s", e.getMessage());
            response.sendRedirect(redirectUrl);
        }
    }
}
