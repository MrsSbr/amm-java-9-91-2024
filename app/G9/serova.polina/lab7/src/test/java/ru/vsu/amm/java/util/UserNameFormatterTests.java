package ru.vsu.amm.java.util;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNameFormatterTests {

    @Test
    public void formatName_shouldReturnFormattedNameWithPatronymic() {
        UserEntity user = new UserEntity();
        user.setSurname("Иванов");
        user.setName("Иван");
        user.setPatronymic("Иванович");

        String result = UserNameFormatter.formatName(user);

        assertEquals("Иванов Иван Иванович", result);
    }

    @Test
    public void formatName_shouldReturnFormattedNameWithoutPatronymic() {
        UserEntity user = new UserEntity();
        user.setSurname("Петров");
        user.setName("Петр");
        user.setPatronymic(null);

        String result = UserNameFormatter.formatName(user);

        assertEquals("Петров Петр", result);
    }

    @Test
    public void formatName_shouldReturnUnknownForNullUser() {
        String result = UserNameFormatter.formatName(null);
        assertEquals("Неизвестный пользователь", result);
    }
}
