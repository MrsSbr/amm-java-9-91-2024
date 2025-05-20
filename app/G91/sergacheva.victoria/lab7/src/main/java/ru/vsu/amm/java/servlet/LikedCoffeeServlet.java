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
import java.util.List;

@WebServlet("/coffee-liked")
public class LikedCoffeeServlet extends HttpServlet {
    private CoffeeService coffeeService;

    @Override
    public void init() {
        coffeeService = (CoffeeService) getServletContext().getAttribute("coffeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");

        List<Coffee> coffees = coffeeService.findListLikedCoffees(user);
        req.setAttribute("coffees", coffees);
        req.getRequestDispatcher("/likedCoffees.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        try {
            long coffeeId = Long.parseLong(req.getParameter("id"));

            coffeeService.createLikedCoffee(coffeeId, user);
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
