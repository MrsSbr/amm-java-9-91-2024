package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

public class Redirection {

    public static String redirectBasedOnRole(User user) {

        return switch (user.getRole()) {
            case Role.USER -> "userSessions.jsp";
            case Role.EMPLOYEE -> "employeeActions.jsp";
            case Role.ADMIN -> "adminActions.jsp";
        };

    }
}
