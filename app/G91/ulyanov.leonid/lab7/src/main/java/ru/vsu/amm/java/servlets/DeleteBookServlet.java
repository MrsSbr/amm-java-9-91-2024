package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.services.DeleteService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@WebServlet("/delete")
public class DeleteBookServlet extends HttpServlet {
    private final DeleteService deleteService;

    public DeleteBookServlet() {
        this.deleteService = new DeleteService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        String redirectPath = "/read";
        try {
            Integer bookId = Integer.parseInt(request.getParameter("bookId"));
            Integer userId = Integer.parseInt(request.getParameter("userId"));
            deleteService.deleteBook(bookId, userId);
            String redirectUrl = request.getContextPath()
                                 + redirectPath + "?message="
                                 + URLEncoder.encode("Книга успешно удалена!", StandardCharsets.UTF_8);
            response.sendRedirect(redirectUrl);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            String redirectUrl = request.getContextPath()
                                 + redirectPath
                                 + String.format("?error=%s", e.getMessage());
            response.sendRedirect(redirectUrl);
        }
    }

}
