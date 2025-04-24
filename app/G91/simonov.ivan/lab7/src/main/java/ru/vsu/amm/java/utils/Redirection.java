package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

import java.util.List;

public class Redirection {

    public static String redirectToViewSessions(User user) {

        return switch (user.getRole()) {
            case Role.USER -> "viewSessionsUser.jsp";
            case Role.EMPLOYEE -> "viewSessionsEmployee.jsp";
            case Role.ADMIN -> "viewSessionsAdmin.jsp";
        };

    }

    public static String redirectToActionsList(User user) {

        return switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "employeeActions.jsp";
            case Role.ADMIN -> "adminActions.jsp";
        };

    }

    public static String redirectToAddSession(User user) {

        return switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "addSessionEmployee.jsp";
            case Role.ADMIN -> "addSessionAdmin.jsp";
        };

    }

    public static String redirectToViewUsers(User user) {

        return switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "viewUsersEmployee.jsp";
            case Role.ADMIN -> "viewUsersAdmin.jsp";
        };

    }

    public static List<String> getAvailablePaths(User user) {

        return switch (user.getRole()) {
            case Role.USER -> List.of(
                    "/viewSessions"
            );
            case Role.EMPLOYEE -> List.of(
                    "/viewSessions",
                    "/employeeActions.jsp",
                    "/viewUsers",
                    "/addSessionEmployee.jsp"
            );
            case Role.ADMIN -> List.of(
                    "/viewSessions",
                    "/adminActions.jsp",
                    "/viewUsers",
                    "/addSessionAdmin.jsp"
            );
        };

    }
}
