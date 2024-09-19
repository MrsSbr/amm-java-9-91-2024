package ru.vsu.amm.java;

import java.util.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Main {

    public static String[] inputWords()
    {
        int N;
        String[] words;
        System.out.println("Enter the number of words: ");
        Scanner in = new Scanner(System.in);
        N =  in.nextInt();
        words = new String[N];
        System.out.println("Enter the words: ");
        for (int i = 0; i < N; i++)
            words[i] = in.next();
        return words;
    }

    public static String translate(String word)
    {
        String[] morseCode = {".-","-...","-.-.","-..",".","..-.",
                "--.","....","..",".---","-.-",".-..","--","-.",
                "---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        char[] englishSAlf = {'a','b','c','d','e','f','g','h','i','j',
                'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        StringBuilder translated = new StringBuilder();
        List<char[]> asList = Arrays.asList(englishSAlf);
        CharacterIterator itr = new StringCharacterIterator(word);
        List<Character> listC = new ArrayList<Character>();
        for (char c : englishSAlf) {
            listC.add(c);
        }
        while (itr.current() != CharacterIterator.DONE)
        {
            char symbol;
            int index;
            symbol = itr.current();
            index = listC.indexOf(symbol);
            translated.append(morseCode[index]);
            itr.next();
        }
        return translated.toString();
    }

    public static Set<String> variousTransformations(String[] words)
    {
        Set<String> set = new HashSet<String>();
        for(String word : words)
            set.add(translate(word));
        return set;
    }

    public static void main(String[] args)
    {
        String[] a = inputWords();
        Set<String> variousTransformations = variousTransformations(a);
        for(String word : variousTransformations)
            System.out.println(word);
        System.out.println(variousTransformations.size());
    }
}