package ru.vsu.amm.java;


import java.util.Arrays;
import java.util.Scanner;

public class Main {

    final static int LENGTH = 9;
    final static int BLOCK = 3;

    public static boolean checkRow(String[][] board, int i, String val) {
        for (int k = 0; k < LENGTH; k++) {
            if (board[i][k].equals(val)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkColumn(String[][] board, int j, String val) {
        for (int k = 0; k < LENGTH; k++) {
            if (board[k][j].equals(val)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkBlocks(String[][] board, int row, int col, String val) {
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

    public static boolean sudokuSolver(String[][] board) {
        String digit;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (board[i][j].equals(".")) {
                    for (int k = 0; k < LENGTH; k++) {
                        digit = String.valueOf(k + 1);
                        if (checkRow(board, i, digit)
                                && checkColumn(board, j, digit)
                                && checkBlocks(board, i, j, digit)) {
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

    public static void printSudokuSolvation(String[][] board, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

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
                if (source.matches("((\\d|\\.) ){8}(\\d|\\.)")) {
                    board[i] = scanner.nextLine().split(" ");
                } else {
                    throw new InvalidInputException("Incorrect line");
                }
            }
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        if (sudokuSolver(board)) {
            System.out.println("Solvation:");
            System.out.print(Arrays.deepToString(board).replace("], ", "]\n"));
        } else {
            System.out.println("No solvation!");
        }
    }
}