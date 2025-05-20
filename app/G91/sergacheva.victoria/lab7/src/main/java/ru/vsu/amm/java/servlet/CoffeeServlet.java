package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.repository.CoffeeRepository;
import ru.vsu.amm.java.repository.LikedCoffeeRepository;
import ru.vsu.amm.java.service.CoffeeService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet("/coffees")
public class CoffeeServlet extends HttpServlet {
    private CoffeeService coffeeService;

    @Override
    public void init() {
        coffeeService = (CoffeeService) getServletContext().getAttribute("coffeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        List<Coffee> coffees = coffeeService.findAll();

        Set<Coffee> likedCoffees = coffeeService.findSetLikedCoffees(user);
        req.setAttribute("coffees", coffees);
        req.setAttribute("likedCoffees", likedCoffees);
        req.setAttribute("me_id", user.getId());
        req.getRequestDispatcher("/coffees.jsp").forward(req, resp);
    }
}
