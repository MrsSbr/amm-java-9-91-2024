package ru.vsu.amm.java;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String j = scanner.nextLine();
    String s = scanner.nextLine();

    int count = 0;
    StringBuilder letters = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      String symbol = Character.toString(s.charAt(i));
      if (j.contains(symbol) && !letters.toString().contains(symbol)) {
        letters.append(symbol);
        count++;
      }
    }

    System.out.println(count);
  }
}