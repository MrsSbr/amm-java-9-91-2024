package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enams.PropertyTypeName;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import ru.vsu.amm.java.enams.ReturnStatus;
import ru.vsu.amm.java.repository.PropertyTypeRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/addProperty")
public class AddPropertyServlet extends HttpServlet {
    private FoundPropertyRepository repository = new FoundPropertyRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            session = request.getSession();
            session.setAttribute("redirectTo", "addProperty.jsp");
            response.sendRedirect("authorization");
            return;
        }

        String propType = request.getParameter("propertyType");
        String placeOfFinding = request.getParameter("placeOfFinding");
        String description = request.getParameter("description");

        FoundProperty property = new FoundProperty();
        PropertyTypeRepository propertyTypeRepository = new PropertyTypeRepository();
        PropertyType propertyType = propertyTypeRepository.getByName(PropertyTypeName.valueOf(propType));
        propertyType.setPropertyTypeName(PropertyTypeName.valueOf(propType));
        property.setPropertyType(propertyType);
        property.setReturnStatus(ReturnStatus.NOT_RETURNED);
        property.setPlaceOfFinding(placeOfFinding);
        property.setDescription(description);
        property.setDateOfFinding(LocalDate.now());
        property.setTimeOfFinding(LocalTime.now());
        property.setUser(user);

        repository.save(property);

        String message = URLEncoder.encode("Вещь успешно добавлена", StandardCharsets.UTF_8.toString());
        response.sendRedirect("listProperties?message=" + message);
    }
}

