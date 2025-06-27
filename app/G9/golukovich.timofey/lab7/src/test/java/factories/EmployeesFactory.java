package factories;

import ru.vsu.amm.java.dtos.EmployeeDto;
import ru.vsu.amm.java.entities.EmployeeEntity;
import ru.vsu.amm.java.enums.EmployeePost;

import java.time.LocalDate;

public class EmployeesFactory {
    public static final int ID = 1;
    public static final Integer HOTEL_ID = null;
    public static final String LOGIN = "employee";
    public static final String PASSWORD = "employee";
    public static final String NAME = "employee";
    public static final String PHONE_NUMBER = "+79009009090";
    public static final String EMAIL = "employee@employee.employee";
    public static final String PASSPORT_NUMBER = "1111";
    public static final String PASSPORT_SERIES = "111111";
    public static final EmployeePost EMPLOYEE_POST = EmployeePost.MASTER_ADMINISTRATOR;
    public static final LocalDate BIRTHDAY = LocalDate.now().minusYears(30);

    public static EmployeeEntity buildDefaultEmployeeEntity() {
        return new EmployeeEntity(
                ID, HOTEL_ID,
                LOGIN, PASSWORD,
                NAME, PHONE_NUMBER, EMAIL,
                PASSPORT_NUMBER, PASSPORT_SERIES,
                EMPLOYEE_POST, BIRTHDAY
        );
    }

    public static EmployeeDto buildDefaultEmployeeDto() {
        return new EmployeeDto(
                ID, HOTEL_ID,
                LOGIN, NAME, PHONE_NUMBER, EMAIL,
                PASSPORT_NUMBER, PASSPORT_SERIES,
                EMPLOYEE_POST, BIRTHDAY
        );
    }
}
