package ru.vsu.amm.java.Checks;
package ru.vsu.amm.java.checks;

public class ArrayLimitations {
    private final int ARRAY_MIN_LENGTH = 1;
    private final int ARRAY_MAX_LENGTH = 100000;
    private final int MIN_ALLOWED_ELEMENT_VALUE = -1000000000;
    private final int MAX_ALLOWED_ELEMENT_VALUE = 1000000000;

    public void checkArrayLengthOrThrow(int length) {
        if (length < ARRAY_MIN_LENGTH || length > ARRAY_MAX_LENGTH) {
            throw new IllegalArgumentException("The length of the array must be in the range from 1 to 100000.");
        }
    }

    public void checkArrayElementValueOrThrow(int number) {
        if (number < MIN_ALLOWED_ELEMENT_VALUE || number > MAX_ALLOWED_ELEMENT_VALUE) {
            throw new IllegalArgumentException("The array elements must be in the range from -10^9 to 10^9.");
        }
    }
}