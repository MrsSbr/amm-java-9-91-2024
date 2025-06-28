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

    private EmployeeEntity entity;

    public EmployeesFactory() {
        entity = null;
    }

    public EmployeesFactory setDefaultEmployeeEntity() {
        entity = new EmployeeEntity(
                ID, HOTEL_ID,
                LOGIN, PASSWORD,
                NAME, PHONE_NUMBER, EMAIL,
                PASSPORT_NUMBER, PASSPORT_SERIES,
                EMPLOYEE_POST, BIRTHDAY
        );
        return this;
    }

    public EmployeesFactory next() {
        entity.setId(entity.getId() + 1);
        entity.setLogin(replaceLastChar(entity.getLogin()));
        entity.setName(replaceLastChar(entity.getName()));
        entity.setPhoneNumber(replaceLastChar(entity.getPhoneNumber()));
        entity.setEmail(replaceLastChar(entity.getEmail()));
        entity.setPassportNumber(replaceLastChar(entity.getPassportNumber()));
        entity.setPassportSeries(replaceLastChar(entity.getPassportSeries()));

        return this;
    }

    private String replaceLastChar(String str) {
        var c = (char)(str.charAt(str.length() - 1) + 1);
        var s = str.substring(0, str.length() - 1) + c;
        return s;
    }

    public EmployeeEntity buildEntity() {
        return entity;
    }
}
