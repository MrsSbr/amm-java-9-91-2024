package ru.vsu.amm.java;

import java.util.HashSet;

public class MagicSquaresInGrid {

    public static void main(String[] args) {
        int[][] grid1 = {{4, 3, 8, 4}, {9, 5, 1, 9}, {2, 7, 6, 2}};
        int[][] grid2 = {{8}};

        System.out.println(numberMagicSquaresInGrid(grid1));
        System.out.println(numberMagicSquaresInGrid(grid2));
    }

    public static int numberMagicSquaresInGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (rows < 3 || cols < 3) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i <= rows - 3; i++) {
            for (int j = 0; j <= cols - 3; j++) {
                if (isMagic(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isMagic(int[][] grid, int row, int col) {
        HashSet<Integer> uniqueNumbers = new HashSet<>();
        int[] sums = new int[8];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num = grid[row + i][col + j];

                if (num < 1 || num > 9) {
                    return false;
                }
                if (!uniqueNumbers.add(num)) {
                    return false;
                }

                sums[i] += num;
                sums[3 + j] += num;
                if (i == j) {
                    sums[6] += num;
                }
                if (i + j == 2) {
                    sums[7] += num;
                }
            }
        }

        for (int sum : sums) {
            if (sum != 15) {
                return false;
            }
        }
        return true;
    }
}
