package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.UserEntity;

public class UserNameFormatter {
    public static String formatName(UserEntity user) {
        if (user == null) {
            return "Неизвестный пользователь";
        } else {
            return String.format("%s %s%s",
                    user.getSurname(),
                    user.getName(),
                    user.getPatronymic() != null ? " " + user.getPatronymic() : ""
            );
        }
    }
}
