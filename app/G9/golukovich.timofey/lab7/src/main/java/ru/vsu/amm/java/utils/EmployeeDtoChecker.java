package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.enums.EmployeePost;

public class EmployeeDtoChecker {
    public static boolean isReady(EmployeeDto employee) {
        return  employee.getPost() == EmployeePost.MASTER_ADMINISTRATOR ||
                (employee.getHotelId() != null || employee.getPost() == EmployeePost.ADMINISTRATOR)
                        && employee.getName() != null
                        && employee.getPhoneNumber() != null
                        && employee.getPassportNumber() != null
                        && employee.getPassportSeries() != null
                        && employee.getPost() != null
                        && employee.getBirthday() != null;
    }
}
