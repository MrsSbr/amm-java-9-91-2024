package ru.vsu.amm.java.filters;

import ru.vsu.amm.java.entities.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/listProperties")
public class EditableFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        String editableParam = httpRequest.getParameter("editable");
        if ("true".equals(editableParam) && user == null) {
            httpResponse.sendRedirect("authorization?redirectTo=listProperties");
            return;
        }

        httpRequest.setAttribute("editable", user != null);

        chain.doFilter(request, response);
    }

}

