package ru.vsu.amm.java;


public class Main {
  public static int countLitBulbs(int n) {
    if (n < 0) {
      return 0;
    }
    return (int) Math.sqrt(n);
  }

  public static void main(String[] args) {
    System.out.println(countLitBulbs(3));
    System.out.println(countLitBulbs(0));
    System.out.println(countLitBulbs(1));
    System.out.println(countLitBulbs(7));
    System.out.println(countLitBulbs(50));
  }
}