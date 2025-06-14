package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.service.AuthorService;
import ru.vsu.amm.java.service.impl.AuthorServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
    private final AuthorService authorService = new AuthorServiceImpl();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.setAttribute("author", new Author());
            req.setAttribute("today", LocalDate.now().format(dateFormatter));
            req.getRequestDispatcher("/WEB-INF/author-form.jsp").forward(req, resp);
        }
        else if ("edit".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            Author author = authorService.getAuthorById(id);
            req.setAttribute("author", author);
            req.getRequestDispatcher("/WEB-INF/author-form.jsp").forward(req, resp);
        }
        else if ("delete".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            authorService.deleteAuthor(id);
            resp.sendRedirect(req.getContextPath() + "/authors");
        }
        else {
            req.setAttribute("authors", authorService.getAllAuthors());
            req.getRequestDispatcher("/WEB-INF/author-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String action = req.getParameter("action");
        Long id = null;

        if ("update".equals(action)) {
            id = req.getParameter("id") != null && !req.getParameter("id").isEmpty() ?
                    Long.parseLong(req.getParameter("id")) : null;
        }

        try {
            String surname = req.getParameter("surname");
            String name = req.getParameter("name");
            String patronymic = req.getParameter("patronymic");
            String dateStr = req.getParameter("registrationDate");

            if (surname == null || surname.trim().isEmpty() ||
                    name == null || name.trim().isEmpty() ||
                    dateStr == null || dateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Фамилия, имя и дата обязательны для заполнения");
            }

            LocalDate registrationDate = LocalDate.parse(dateStr, dateFormatter);

            Author author = new Author();
            if (id != null) {
                author.setId(id);
            }
            author.setSurname(surname.trim());
            author.setName(name.trim());
            author.setPatronymic(patronymic != null ? patronymic.trim() : null);
            author.setRegistrationDate(registrationDate);

            if ("insert".equals(action)) {
                authorService.createAuthor(author);
            } else if ("update".equals(action)) {
                authorService.updateAuthor(author);
            }

            resp.sendRedirect(req.getContextPath() + "/authors");
        } catch (DateTimeParseException e) {
            req.setAttribute("error", "Неверный формат даты. Используйте формат ГГГГ-ММ-ДД");
            forwardToForm(req, resp, action, id);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            forwardToForm(req, resp, action, id);
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при сохранении автора: " + e.getMessage());
            forwardToForm(req, resp, action, id);
        }
    }

    private void forwardToForm(HttpServletRequest req, HttpServletResponse resp,
                               String action, Long id) throws ServletException, IOException {
        if ("insert".equals(action)) {
            Author author = new Author();
            author.setSurname(req.getParameter("surname"));
            author.setName(req.getParameter("name"));
            author.setPatronymic(req.getParameter("patronymic"));
            try {
                author.setRegistrationDate(LocalDate.parse(req.getParameter("registrationDate"), dateFormatter));
            } catch (DateTimeParseException e) {
                // Оставляем дату null или устанавливаем текущую дату
                author.setRegistrationDate(null);
            }
            req.setAttribute("author", author);
            req.setAttribute("today", req.getParameter("registrationDate"));
            req.getRequestDispatcher("/WEB-INF/author-form.jsp").forward(req, resp);
        } else {
            Author author = authorService.getAuthorById(id);
            author.setSurname(req.getParameter("surname"));
            author.setName(req.getParameter("name"));
            author.setPatronymic(req.getParameter("patronymic"));
            try {
                author.setRegistrationDate(LocalDate.parse(req.getParameter("registrationDate"), dateFormatter));
            } catch (DateTimeParseException e) {

            }
            req.setAttribute("author", author);
            req.getRequestDispatcher("/WEB-INF/author-form.jsp").forward(req, resp);
        }
    }
}