package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.repos.BookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/read")
public class ReadBooksServlet extends HttpServlet {
    private final BookRepository bookRepository;

    public ReadBooksServlet() {
        this.bookRepository = new BookRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        List<Book> bookList = bookRepository.getAll();
        request.setAttribute("book", bookList);
        request.getRequestDispatcher("books.jsp").forward(request, response);
    }
}
