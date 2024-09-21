package ru.vsu.amm.java;


public class Main {

    final static int LENGTH = 9;
    final static int BLOCK = 3;

    public static boolean checkRow(char [][] board, int length, int i, char val) {
        for (int k = 0; k < length; k++) {
            if (board[i][k] == val) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkColumn(char [][] board, int length, int j, char val) {
        for (int k = 0; k < length; k++) {
            if (board[k][j] == val) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkBlocks(char [][] board, int block, int row, int col, char val) {
        row -= row % block;
        col -= col % block;
        for (int i = 0; i < block; i++)
        {
            for (int j = 0; j < block; j++) {
                if (board[row + i][col + j] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean sudokuSolver(char [][] board, int length, int block) {
        char digit;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (board[i][j] == '.') {
                    for (int k = 0; k < length; k++) {
                        digit = Character.forDigit(k + 1, 10);
                        if (checkRow(board, length, i, digit)
                        && checkColumn(board, length, j, digit)
                        && checkBlocks(board, block, i, j, digit)) {
                            board[i][j] = digit;
                            if (sudokuSolver(board, length, block)) {
                                return true;
                            }
                            board[i][j] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void printSudokuSolvation(char [][] board, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    public static void main(String[] args) {
       char[][] board = {{'5','3','.','.','7','.','.','.','.'},
                        {'6','.','.','1','9','5','.','.','.'},
                        {'.','9','8','.','.','.','.','6','.'},
                        {'8','.','.','.','6','.','.','.','3'},
                        {'4','.','.','8','.','3','.','.','1'},
                        {'7','.','.','.','2','.','.','.','6'},
                        {'.','6','.','.','.','.','2','8','.'},
                        {'.','.','.','4','1','9','.','.','5'},
                        {'.','.','.','.','8','.','.','7','9'}};

       if (sudokuSolver(board, LENGTH, BLOCK)) {
           printSudokuSolvation(board, LENGTH);
       }
       else {
           System.out.println("No solvation!");
       }
    }
}