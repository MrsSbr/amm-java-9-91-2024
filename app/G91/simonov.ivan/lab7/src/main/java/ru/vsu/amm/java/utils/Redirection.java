package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

import java.util.ArrayList;
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

        List<String> commonPaths = List.of("/viewSessions");
        List<String> staffPaths = List.of(
                "/viewUsers",
                "/addSession",
                "/deleteSession",
                "/updateSession");

        return switch (user.getRole()) {

            case Role.USER -> commonPaths;

            case Role.EMPLOYEE -> {

                List<String> paths = new ArrayList<>(commonPaths);
                paths.addAll(staffPaths);
                paths.addAll(List.of(
                        "/employeeActions.jsp",
                        "/addSessionEmployee.jsp"
                ));
                yield paths;

            }

            case Role.ADMIN -> {

                List<String> paths = new ArrayList<>(commonPaths);
                paths.addAll(staffPaths);
                paths.addAll(List.of(
                        "/adminActions.jsp",
                        "/addSessionAdmin.jsp"
                ));
                yield paths;

            }

        };

    }
}
