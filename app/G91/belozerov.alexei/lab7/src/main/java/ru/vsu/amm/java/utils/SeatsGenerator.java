package ru.vsu.amm.java.utils;

import java.util.List;

public class SeatsGenerator {

    public static List<String> generateSeats(int seatsInRowNumber, int rowsNumber) {
        char row = 'A';
        String[] seats = new String[seatsInRowNumber * rowsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            for (int j = 0; j < seatsInRowNumber; j++) {
                seats[i * seatsInRowNumber + j] = String.valueOf(row) + (j + 1);
            }
            row++;
        }
        return List.of(seats);
    }
}
