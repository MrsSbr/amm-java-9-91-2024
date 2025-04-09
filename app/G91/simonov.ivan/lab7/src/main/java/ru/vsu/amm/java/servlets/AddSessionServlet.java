package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AddException;
import ru.vsu.amm.java.requests.AddSessionRequest;
import ru.vsu.amm.java.requests.AddVehicleRequest;
import ru.vsu.amm.java.service.AddService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static ru.vsu.amm.java.utils.Redirection.redirectToAddSession;

@WebServlet("/addSession")
public class AddSessionServlet extends HttpServlet {

    private final AddService addService;

    public AddSessionServlet() {

        addService = new AddService();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String redirect = redirectToAddSession(user);

        try {

            AddSessionRequest addSessionRequest = new AddSessionRequest(
                    Integer.parseInt(request.getParameter("userId")),
                    new BigDecimal(request.getParameter("parkingPrice")),
                    new AddVehicleRequest(
                            request.getParameter("registrationNumber"),
                            request.getParameter("model"),
                            request.getParameter("brand"),
                            request.getParameter("colour")
                    )
            );

            addService.addSession(addSessionRequest);

            response.sendRedirect(String.format("%s?message=Session successfully added!", redirect));

        } catch (AddException e) {

            response.sendRedirect(String.format("%s?error=%s", redirect, e.getMessage()));

        }
    }
}
