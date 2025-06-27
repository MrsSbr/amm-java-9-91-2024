package ru.vsu.amm.java.Utils;

import ru.vsu.amm.java.Dtos.UserDto;
import ru.vsu.amm.java.Exceptions.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletHelper {
    private static final String USER_ATTRIBUTE_NAME = "user";
    private static final String SUCCESS_ATTRIBUTE_NAME = "successMessage";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    public static UserDto getUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(USER_ATTRIBUTE_NAME) != null) {
            return (UserDto) session.getAttribute(USER_ATTRIBUTE_NAME);
        }

        throw new AuthorizationException("Session does not contain user info");
    }

    public static void setMessage(HttpServletRequest req, String successMessage) {
        req.setAttribute(SUCCESS_ATTRIBUTE_NAME, successMessage);
    }

    public static void setError(HttpServletRequest req, String errorMessage) {
        req.setAttribute(ERROR_ATTRIBUTE_NAME, errorMessage);
    }
}
