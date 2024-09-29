package ru.vsu.amm.java;

public class Main
{
  public static int countLitBulbs(int n)
  {
    if (n < 0)
    {
      return 0;
    }
    return (int) Math.sqrt(n); // Возвращаем кол-во квадратов чисел, которые <= n
  }

  public static void main(String[] args) {
    System.out.println(countLitBulbs(3));  // Output: 1
    System.out.println(countLitBulbs(0));  // Output: 0
    System.out.println(countLitBulbs(1));  // Output: 1
    System.out.println(countLitBulbs(7)); // Output: 2
    System.out.println(countLitBulbs(50)); // Output: 7
  }
}