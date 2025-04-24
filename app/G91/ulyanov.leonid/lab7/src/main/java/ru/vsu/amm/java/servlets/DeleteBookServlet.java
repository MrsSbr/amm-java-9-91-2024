package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.requests.BookUpdate.DeleteBookUpdateRequest;
import ru.vsu.amm.java.services.DeleteService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
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
        try {
            DeleteBookUpdateRequest deleteUpdateRequest = new DeleteBookUpdateRequest(
                    Integer.parseInt(request.getParameter("userId")),
                    Integer.parseInt(request.getParameter("bookId")),
                    LocalDateTime.parse(request.getParameter("updateTime")),
                    UpdateType.valueOf(request.getParameter("updateType"))
            );

            deleteService.deleteBook(deleteUpdateRequest);
            response.sendRedirect("books.jsp");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            response.sendRedirect(String.format("%s", e.getMessage())); // TODO: связать с jsp
        }
    }

}
