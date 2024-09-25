/*
Дан целочисленный массив nums. Есть два игрока: игрок 1 и игрок 2.

Игроки 1 и 2 ходят по очереди, причем игрок 1 начинает первым.
Оба игрока начинают игру со счетом 0.
На каждом ходу игрок берет одно из чисел с любого конца массива,
    что уменьшает размер массива numsна 1.
Игрок добавляет выбранное число к своему счету.
Игра заканчивается, когда в массиве больше нет элементов.

Нужно вернуть true, если игрок 1 может выиграть игру.
Если очки обоих игроков равны, то побеждает игрок 1.
Оба игрока играют оптимально.
*/

/*
1 <= nums.length <= 20
0 <= nums[i] <= 10^7
*/

package ru.vsu.amm.java;

public class Main {
  static boolean winnerTask(int[] nums) {
    Integer[][] matrix = new Integer[nums.length][nums.length];
    return winnerRec(nums, 0, nums.length - 1, matrix) >= 0;
  }

  // Integer нужен, чтобы сравнивать с null
  // i matrix - граница слева
  // j matrix - граница справа
  public static int winnerRec(int[] nums, int i, int j, Integer[][] matrix)
  {
    int result = 0;

    if (i == j) {
      // осталось 1 значение в массиве - берёт его
      result = nums[i];
    }
    else if (matrix[i][j] != null) {
      // Значение для текущих границ уже вычислено - берём его
      result = matrix[i][j];
    }
    else {
      // взяли минус то, что получит другой
      int a = nums[i] - winnerRec(nums, i + 1, j, matrix);
      int b = nums[j] - winnerRec(nums, i, j - 1, matrix);

      // максимально много с учётом хода другого игрока
      // (максимальная разница между тем, что получит каждый)
      matrix[i][j] = Math.max(a, b);
      result = matrix[i][j];
    }
    return result;
  }

  public static void main(String[] args) {
    int[] nums1 = {1,5,2};
    System.out.println(winnerTask(nums1)); // false

    int[] nums2 = {1,5,233,7};
    System.out.println(winnerTask(nums2)); // true
  }
}