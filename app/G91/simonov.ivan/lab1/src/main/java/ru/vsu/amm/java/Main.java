package ru.vsu.amm.java;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    final static int LENGTH = 9;
    final static int BLOCK = 3;

    // проверка наличия цифры в строке
    public static boolean checkRow(String[][] board, int i, String val) {
        for (int k = 0; k < LENGTH; k++) {
            if (board[i][k].equals(val)) {
                return false;
            }
        }
        return true;
    }

    // проверка наличия цифры в столбце
    public static boolean checkColumn(String[][] board, int j, String val) {
        for (int k = 0; k < LENGTH; k++) {
            if (board[k][j].equals(val)) {
                return false;
            }
        }
        return true;
    }

    // проверка наличия цифры в блоке
    public static boolean checkBlock(String[][] board, int row, int col, String val) {
        int start_row = row - row % BLOCK;
        int start_col = col - col % BLOCK;
        for (int i = 0; i < BLOCK; i++) {
            for (int j = 0; j < BLOCK; j++) {
                if (board[start_row + i][start_col + j].equals(val)) {
                    return false;
                }
            }
        }
        return true;
    }

    // основной алгоритм решения Судоку
    public static boolean sudokuSolver(String[][] board) {
        String digit;
        // перебор всех элементов матрицы Судоку
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (board[i][j].equals(".")) {
                    // перебор всех возможных цифр
                    for (int k = 0; k < LENGTH; k++) {
                        digit = String.valueOf(k + 1);
                        // проверка наличия цифры в строке, столбце или блоке
                        if (checkRow(board, i, digit)
                                && checkColumn(board, j, digit)
                                && checkBlock(board, i, j, digit)) {
                            board[i][j] = digit;
                            if (sudokuSolver(board)) {
                                return true;
                            }
                            board[i][j] = ".";
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Исключение для некорректных данных при вводе
    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
//        String[][] board = {{"5", "3", ".", ".", "7", ".", ".", ".", "."},
//                {"6", ".", ".", "1", "9", "5", ".", ".", "."},
//                {".", "9", "8", ".", ".", ".", ".", "6", "."},
//                {"8", ".", ".", ".", ".", ".", ".", ".", "3"},
//                {"4", ".", ".", ".", ".", ".", ".", ".", "1"},
//                {"7", ".", ".", ".", ".", ".", ".", ".", "6"},
//                {".", "6", ".", ".", ".", ".", "2", "8", "."},
//                {".", ".", ".", "4", "1", "9", ".", ".", "5"},
//                {".", ".", ".", ".", "8", ".", ".", "7", "9"}};
        String[][] board = new String[LENGTH][LENGTH];
        try {
            Scanner scanner = new Scanner(System.in);
            String source;
            for (int i = 0; i < LENGTH; i++) {
                source = scanner.nextLine();
                // проверка вводимой строки на соответствие шаблону строки Судоку
                if (source.matches("((\\d|\\.) ){8}(\\d|\\.)")) {
                    board[i] = source.split(" ");
                } else {
                    throw new InvalidInputException("Incorrect line");
                }
            }
            System.out.println("Source:\n");
            System.out.print(Arrays.deepToString(board).replace("], ", "]\n"));
            System.out.print("\n\n");

            if (sudokuSolver(board)) {
                System.out.println("Solvation:\n");
                System.out.print(Arrays.deepToString(board).replace("], ", "]\n"));
                System.out.print("\n\n");
            } else {
                System.out.println("No solvation!");
            }
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }
}