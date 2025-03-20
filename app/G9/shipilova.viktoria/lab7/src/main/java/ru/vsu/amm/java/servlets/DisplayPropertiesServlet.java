package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.enams.ReturnStatus;
import ru.vsu.amm.java.entities.FoundProperty;
import ru.vsu.amm.java.entities.PropertyType;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.FoundPropertyRepository;
import ru.vsu.amm.java.repository.PropertyTypeRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/listProperties")
public class DisplayPropertiesServlet extends HttpServlet {

    private FoundPropertyRepository repository = new FoundPropertyRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        boolean editable = "true".equals(request.getParameter("editable"));

        if (user == null && editable) {
            session = request.getSession();
            session.setAttribute("redirectTo", "listProperties?editable=true");
            response.sendRedirect("authorization");
            return;
        }

        List<FoundProperty> allProperties = repository.getAll();
        PropertyTypeRepository propertyTypeRepository = new PropertyTypeRepository();

        for (FoundProperty property : allProperties) {
            PropertyType propertyType = propertyTypeRepository.getById(property.getPropertyType().getId());
            property.setPropertyType(propertyType);
        }

        List<FoundProperty> notReturnedProperties = allProperties.stream()
                .filter(property -> property.getReturnStatus() == ReturnStatus.NOT_RETURNED)
                .collect(Collectors.toList());
        request.setAttribute("properties", notReturnedProperties);

        request.setAttribute("editable", user != null);
        request.getRequestDispatcher("listProperties.jsp").forward(request, response);
    }
}
