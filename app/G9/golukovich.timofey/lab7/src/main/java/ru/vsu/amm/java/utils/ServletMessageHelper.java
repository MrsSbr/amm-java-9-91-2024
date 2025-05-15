package ru.vsu.amm.java.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ServletMessageHelper {
    public static void CopyErrorMessage(HttpServletRequest req, HttpSession session) {
        final String ERROR_MESSAGE = "errorMessage";
        if (session.getAttribute(ERROR_MESSAGE) != null && req.getAttribute(ERROR_MESSAGE) == null) {
            req.setAttribute(ERROR_MESSAGE, session.getAttribute(ERROR_MESSAGE));
            session.removeAttribute(ERROR_MESSAGE);
        }
    }

    public static void CopySuccessMessage(HttpServletRequest req, HttpSession session) {
        final String SUCCESS_MESSAGE = "successMessage";
        if (session.getAttribute(SUCCESS_MESSAGE) != null && req.getAttribute(SUCCESS_MESSAGE) == null) {
            req.setAttribute(SUCCESS_MESSAGE, session.getAttribute(SUCCESS_MESSAGE));
            session.removeAttribute(SUCCESS_MESSAGE);
        }
    }
}
