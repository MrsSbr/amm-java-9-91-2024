package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Role;

import java.util.Arrays;
import java.util.List;

public class RolesMapper {
    public static String[] mapRolesToArrayOfStrings(List<Role> roles) {
        return roles.stream().map(Enum::name).toArray(String[]::new);
    }

    public static List<Role> mapArrayOfStringsToRoles(String[] roles) {
        return Arrays.stream(roles).map(Role::valueOf).toList();
    }
}
