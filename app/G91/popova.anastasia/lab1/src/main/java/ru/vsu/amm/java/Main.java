package ru.vsu.amm.java;

/*
 *Magic Squares In Grid
 *Магический квадрат 3 x 3 представляет собой таблицу 3 x 3, заполненную уникальными числами от 1 до 9,
 *так что каждая строка, столбец и обе диагонали имеют одинаковую сумму.
 *Дана таблица row x col целых чисел, сколько существует 3 x 3 «магических квадратов»? (Каждый квадрат непрерывен).
 *
 *Пример 1:
 *Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
 *Output: 1
 *Пример 2:
 *Input: grid = [[8]]
 *Output: 0
 *
 *Ограничения:
 *row == grid.length
 *col == grid[i].length
 *1 <= row, col <= 10
 *0 <= grid[i][j] <= 15
 */

public class Main {
    static final int SIZE = 3;

    // Проверяем, что все числа в исходной матрице в диапазоне от 0 до 15
    private static boolean isInRangeInitial(int[][] grid) {
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid.length; ++j) {
                if (grid[i][j] < 0 || grid[i][j] > 15) {
                    return false;
                }
            }
        }
        return true;
    }

    // Проверяем, что все числа в квадрате уникальны и находятся в диапазоне от 1 до 9
    private static boolean isUniqueInRange(int[][] grid, int row, int col) {
        int[] count = new int[8];
        for (int i = row; i < row + SIZE; ++i) {
            for (int j = col; j < col + SIZE; ++j) {
                int num = grid[i][j];
                if (num < 1 || num > 9 || count[num+1] != 0) {
                    return false;
                }
                count[num+1]++;
            }
        }
        return true;
    }

    private static boolean isMagic(int[][] grid, int row, int col) {
        if(!isUniqueInRange(grid, row, col)) {
            return false;
        }
        // Считаем сумму строк и столбцов
        int sum = grid[row][col] + grid[row][col + 1] + grid[row][col + 2];
        for (int i = 0; i < SIZE; ++i) {
            if (grid[row + i][col] + grid[row + i][col + 1] + grid[row + i][col + 2] != sum) {
                return false;
            }
            if (grid[row][col + i] + grid[row + 1][col + i] + grid[row + 2][col + i] != sum) {
                return false;
            }
        }
        // Проверяем диагонали
        if (grid[row][col] + grid[row + 1][col + 1] + grid[row + 2][col + 2] != sum) {
            return false;
        }
        if (grid[row][col + 2] + grid[row + 1][col + 1] + grid[row + 2][col] != sum) {
            return false;
        }
        return true;
    }

    public static int countMagicSquares(int[][] grid) {
        int rowCount = grid.length;
        int colCount = grid[0].length;
        if (rowCount < SIZE || colCount < SIZE) {
            System.out.println("oops! initial matrix is too small");
            return 0;
        }
        int count = 0;
        for (int i = 0; i <= grid.length - SIZE; ++i) {
            for (int j = 0; j <= grid[0].length - SIZE; ++j) {
                if (isMagic(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("test 1:");
        int[][] grid1 = {
                {4, 3, 8, 4},
                {9, 5, 1, 9},
                {2, 7, 6, 2}
        };
        if (!isInRangeInitial(grid1)) {
            System.out.println("oops! initial matrix contains inappropriate numbers");
        }
        else {
            System.out.println(countMagicSquares(grid1));
        }
        System.out.println("====================");
        System.out.println("test 2:");
        int[][] grid2 = {{8}};
        if (!isInRangeInitial(grid2)) {
            System.out.println("oops! initial matrix contains inappropriate numbers");
        }
        else {
            System.out.println(countMagicSquares(grid2));
        }
    }
}
