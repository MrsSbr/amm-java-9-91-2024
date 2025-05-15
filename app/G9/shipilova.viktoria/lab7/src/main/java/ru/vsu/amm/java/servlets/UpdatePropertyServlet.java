package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.services.FoundPropertyService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/updateProperty")
public class UpdatePropertyServlet extends HttpServlet {
    private FoundPropertyService foundPropertyService = new FoundPropertyService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long propertyId = Long.parseLong(request.getParameter("propertyId"));

        boolean success = foundPropertyService.updatePropertyStatusToReturned(propertyId);

        String message = success
                ? URLEncoder.encode("Имущество отмечено как возвращенное", StandardCharsets.UTF_8.toString())
                : URLEncoder.encode("Имущество не найдено", StandardCharsets.UTF_8.toString());

        response.sendRedirect("listProperties?message=" + message);
    }
}
