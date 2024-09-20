package ru.vsu.amm.java;

import java.util.HashMap;
import java.util.Map;

public class ConverterToRoman {
    private final Map<Integer, Character> convertToRoman = new HashMap<>() {{
        put(0, 'N');
        put(1, 'I');
        put(5, 'V');
        put(10, 'X');
        put(50, 'L');
        put(100, 'C');
        put(500, 'D');
        put(1000, 'M');
    }};

    public String convertToRoman(int number) {

        if (convertToRoman.containsKey(number)) {
            return convertToRoman.get(number).toString();
        }

        StringBuilder result = new StringBuilder();
        while (number >= 1000) {
            number -= 1000;
            result.append(convertToRoman.get(1000));
        }
        for (int i = 1000; number > 0; i /= 10) {
            if (number >= 9 * i) {
                number -= 9 * i;
                result.append(convertToRoman.get(i)).append(convertToRoman.get(10 * i));
            }
            if (number >= 5 * i) {
                result.append(convertToRoman.get(5 * i));
                while (number >= 6 * i) {
                    number -= i;
                    result.append(convertToRoman.get(i));
                }
                number -= 5 * i;
            }
            if (number >= 4 * i) {
                number -= (4 * i);
                result.append(convertToRoman.get(i)).append(convertToRoman.get(5 * i));
            }
            while (number >= i) {
                number -= i;
                result.append(convertToRoman.get(i));
            }
        }
        return result.toString();
    }
}
