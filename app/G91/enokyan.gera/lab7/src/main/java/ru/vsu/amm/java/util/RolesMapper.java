package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Role;

import java.util.Arrays;
import java.util.List;

public class RolesMapper {
    public static String mapRolesToString(List<Role> roles) {
        return String.join(",", roles.stream().map(Enum::name).toList());
    }

    public static List<Role> mapStringToRoles(String roles) {
        return Arrays.stream(roles.split(",")).map(Role::valueOf).toList();
    }
}
