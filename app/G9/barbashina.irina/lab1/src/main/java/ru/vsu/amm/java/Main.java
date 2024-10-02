package ru.vsu.amm.java;

import java.util.*;

public class Main {
    private static final String[] MORSE_CODE = {".-", "-...", "-.-.", "-..", ".", "..-.",
            "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.",
            "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};

    private static final char[] ENGLISH_ALPHABET = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static Map<Character, String> morseCodeMap = new HashMap<>() {
        {
            put('a', ".-");
            put('b', "-...");
            put('c', "-.-.");
            put('d', "-..");
            put('e', ".");
            put('f', "..-.");
            put('g', "--.");
            put('h', "....");
            put('i', "..");
            put('j', ".---");
            put('k', "-.-");
            put('l', ".-..");
            put('m', "--");
            put('n', "-.");
            put('o', "---");
            put('p', ".--.");
            put('q', "--.-");
            put('r', ".-.");
            put('s', "...");
            put('t', "-");
            put('u', "..-");
            put('v', "...-");
            put('w', ".--");
            put('x', "-..-");
            put('y', "-.--");
            put('z', "--..");
        }
    };


    public static String[] inputWords() {
        System.out.println("Enter the number of words: ");
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        String[] words = new String[N];
        System.out.println("Enter " + N + " words: ");
        for (int i = 0; i < N; i++) {
            words[i] = in.next();
        }
        return words;
    }

    public static String translate(String input) {
        StringBuilder result = new StringBuilder(" ");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            result.append(morseCodeMap.get(ch));
        }
        return result.toString();
    }

    public static Set<String> variousTransformations(String[] words) {
        Set<String> set = new HashSet<>();
        for (String word : words) {
            set.add(translate(word));
        }
        return set;
    }

    public static void main(String[] args) {
        String[] words = inputWords();
        Set<String> variousTransformations = variousTransformations(words);
        System.out.println(variousTransformations.size());
    }
}