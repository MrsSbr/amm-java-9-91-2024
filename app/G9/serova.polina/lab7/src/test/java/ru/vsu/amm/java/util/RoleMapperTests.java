package ru.vsu.amm.java.util;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.exceptions.RoleException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleMapperTests {

    @Test
    void mapRoleToString_shouldReturnDoctor() {
        assertEquals("DOCTOR", RoleMapper.mapRoleToString(Role.DOCTOR));
    }

    @Test
    void mapRoleToString_shouldReturnPatient() {
        assertEquals("PATIENT", RoleMapper.mapRoleToString(Role.PATIENT));
    }

    @Test
    void mapRoleToString_shouldThrowExceptionOnNull() {
        RoleException exception = assertThrows(RoleException.class, () ->
                RoleMapper.mapRoleToString(null));
        assertEquals("Unknown user role", exception.getMessage());
    }

    @Test
    void mapStringToRole_shouldReturnDoctor() {
        assertEquals(Role.DOCTOR, RoleMapper.mapStringToRole("DOCTOR"));
    }

    @Test
    void mapStringToRole_shouldReturnPatient() {
        assertEquals(Role.PATIENT, RoleMapper.mapStringToRole("PATIENT"));
    }

    @Test
    void mapStringToRole_shouldThrowExceptionOnNull() {
        RoleException exception = assertThrows(RoleException.class, () ->
                RoleMapper.mapStringToRole(null));
        assertEquals("Unknown user role", exception.getMessage());
    }

    @Test
    void mapStringToRole_shouldThrowExceptionOnUnknownString() {
        RoleException exception = assertThrows(RoleException.class, () ->
                RoleMapper.mapStringToRole("ADMIN"));
        assertEquals("Unknown user role", exception.getMessage());
    }
}
