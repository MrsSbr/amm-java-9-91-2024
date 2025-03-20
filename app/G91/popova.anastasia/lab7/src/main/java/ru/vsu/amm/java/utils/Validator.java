package ru.vsu.amm.java;

public class Validator {

    private static final String NAME_PATTERN = "^[a-zA-Z0-9._-]{3,30}$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._-]+@{1}[a-zA-Z.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValidName(String name) {
        return name != null && name.matches(NAME_PATTERN);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

}
