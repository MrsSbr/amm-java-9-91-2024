package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;
import ru.vsu.amm.java.entity.EarnedAchievementView;
import ru.vsu.amm.java.services.AchievementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AchievementServlet", urlPatterns = "/home")
public class AchievementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String login = (String) session.getAttribute("login");
        AchievementService service = new AchievementService(DatabaseAccess.getDataSource());

        try {
            List<EarnedAchievement> earnedAchievements = service.getAllAchievements(login);
            List<EarnedAchievementView> result = new ArrayList<>();

            for (EarnedAchievement ea : earnedAchievements) {
                Achievement a = service.getAchievementById(ea.getAchievementId());
                result.add(new EarnedAchievementView(ea, a));
            }

            req.setAttribute("achievements", result);
            req.getRequestDispatcher("/home.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при загрузке достижений");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}