package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

public class Redirection {

    public static String redirectToViewSessions(User user) {

        return  switch (user.getRole()) {
            case Role.USER -> "viewSessionsUser.jsp";
            case Role.EMPLOYEE -> "viewSessionsEmployee.jsp";
            case Role.ADMIN -> "viewSessionsAdmin.jsp";
        };

    }

    public static String redirectToActionsList(User user) {

        return  switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "employeeActions.jsp";
            case Role.ADMIN -> "adminActions.jsp";
        };

    }

    public static String redirectToAddSession(User user) {

        return  switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "addSessionEmployee.jsp";
            case Role.ADMIN -> "addSessionAdmin.jsp";
        };

    }
}
