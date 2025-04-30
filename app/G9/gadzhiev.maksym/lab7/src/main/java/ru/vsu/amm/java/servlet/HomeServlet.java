package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entity.RealEstate;
import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.service.RealEstateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RealEstateService realEstateService = new RealEstateService();
        AuthService authService = new AuthService();
        boolean isAuth = authService.isAuth(req);
        req.setAttribute("isAuth", isAuth);
        List<RealEstate> realEstateList;
        try {
            realEstateList = realEstateService.getAllRealEstate();
            req.setAttribute("realEstates", realEstateList);
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
