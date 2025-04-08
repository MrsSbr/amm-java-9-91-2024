package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import ru.vsu.amm.java.enams.ReturnStatus;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/updateProperty")
public class UpdatePropertyServlet extends HttpServlet {
    private FoundPropertyRepository repository = new FoundPropertyRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        long propertyId = Long.parseLong(request.getParameter("propertyId"));

        FoundProperty foundProperty = repository.getById(propertyId);

        if (foundProperty != null) {
            foundProperty.setReturnStatus(ReturnStatus.RETURNED);
            repository.update(foundProperty);
            String message = URLEncoder.encode("Имущество отмечено как возвращенное", StandardCharsets.UTF_8.toString());
            response.sendRedirect("listProperties?message=" + message);
        } else {
            response.sendRedirect("listProperties?error=Property not found");
        }
    }
}
