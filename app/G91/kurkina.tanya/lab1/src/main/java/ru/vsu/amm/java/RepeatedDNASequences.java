package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RepeatedDNASequences {

    public static Set<String> findRepeatedDNASequences(String s) {
      Set<String> seen = new HashSet<>(); //для хранения всех уникальных 10-симв послед-тей, которые встречались
      Set<String> repeated = new HashSet<>(); //для хранения послед-тей, которые встр больше 1 раза

      for (int i = 0; i <= s.length() - 10; i++) {
          String sequence = s.substring(i, i + 10); //получение подстроки длиной 10 символов
          if (!seen.add(sequence)) {
              repeated.add(sequence);
          }
      }
      return repeated; //набор повторяющихся послед-тей
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input DNA sequence:");

        try {
            String s = scanner.nextLine();

            if (s.length() < 1 || s.length() > 100000) {
                throw new IllegalArgumentException("Invalid length of the sequence.");
            }

            if (!s.matches("[ACGT]+")) {
                throw new IllegalArgumentException("DNA sequence can contain only 'A', 'C', 'G', 'T'.");
            }

            Set<String> result = findRepeatedDNASequences(s);

            System.out.println("Repeated sequences:");
            for (String seq : result) {
                System.out.println(seq);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}