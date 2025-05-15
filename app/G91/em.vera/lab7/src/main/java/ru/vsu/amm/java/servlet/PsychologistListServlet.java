package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.models.dto.PsychologistDto;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.utils.ServletConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PsychologistListServlet", urlPatterns = ServletConstants.URL_PSY_LIST)
public class PsychologistListServlet extends HttpServlet {

    private final PsychologistService psychologistService;

    public PsychologistListServlet(){
        this.psychologistService = new PsychologistServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<PsychologistDto> psychologists = psychologistService.getAllPsychologist();

        req.setAttribute("psychologists", psychologists);

        getServletContext()
                .getRequestDispatcher(ServletConstants.PSY_LIST_PAGE)
                .forward(req, resp);
    }
}
