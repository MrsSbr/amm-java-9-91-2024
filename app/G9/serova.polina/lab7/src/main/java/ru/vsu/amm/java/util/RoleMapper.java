package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.exceptions.RoleException;

public class RoleMapper {

    public static String mapRoleToString(Role role) {
        return switch (role) {
            case DOCTOR -> "DOCTOR";
            case PATIENT -> "PATIENT";
            case null -> throw new RoleException("Unknown user role");
        };
    }

    public static Role mapStringToRole(String role) {
        return switch (role) {
            case "DOCTOR" -> Role.DOCTOR;
            case "PATIENT" -> Role.PATIENT;
            case null, default -> throw new RoleException("Unknown user role");
        };
    }
}
