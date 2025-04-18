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

import java.io.IOException;

@WebServlet("/coffees/delete")
public class CoffeeDeleteServlet extends HttpServlet {
    private CoffeeRepository coffeeRepository;

    @Override
    public void init() {
        coffeeRepository = (CoffeeRepository) getServletContext().getAttribute("coffeeRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            int coffeeId = Integer.parseInt(req.getParameter("id"));

            Coffee coffee = coffeeRepository.findById(coffeeId).orElseThrow(()-> new EntityNotFoundException("Coffee not found with id = " + coffeeId));
            if (!user.getId().equals(coffee.getAuthor().getId())) {
                throw new ForbiddenException("This user cannot edit coffee with id =" + coffeeId);
            }

            coffeeRepository.delete(coffee);
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
        resp.sendRedirect("/coffees");
    }
}
