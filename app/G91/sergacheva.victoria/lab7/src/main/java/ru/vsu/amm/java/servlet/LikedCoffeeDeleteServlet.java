package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.EntityNotFoundException;
import ru.vsu.amm.java.exception.ForbiddenException;
import ru.vsu.amm.java.repository.CoffeeRepository;
import ru.vsu.amm.java.repository.LikedCoffeeRepository;
import ru.vsu.amm.java.service.CoffeeService;

import java.io.IOException;
@WebServlet("/coffee-liked/delete")
public class LikedCoffeeDeleteServlet extends HttpServlet {
    private CoffeeService coffeeService;

    @Override
    public void init() {
        coffeeService = (CoffeeService) getServletContext().getAttribute("coffeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        try {
            long coffeeId = Long.parseLong(req.getParameter("id"));

            coffeeService.deleteLikedCoffee(coffeeId, user);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } catch (EntityNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (ForbiddenException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        resp.sendRedirect("/coffee-liked");
    }
}
