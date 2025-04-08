package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.AddException;
import ru.vsu.amm.java.requests.AddSessionRequest;
import ru.vsu.amm.java.requests.AddVehicleRequest;
import ru.vsu.amm.java.service.AddService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/addSession")
public class AddSessionServlet extends HttpServlet {

    private final AddService addService;

    public AddSessionServlet() {

        addService = new AddService();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

            response.sendRedirect("addSession.jsp?message=Session successfully added!");

        } catch (AddException e) {

            response.sendRedirect(String.format("addSession.jsp?error=%s", e.getMessage()));

        }
    }
}
