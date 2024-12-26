package ru.vsu.amm.java.Util;

import ru.vsu.amm.java.Entity.Message;

import java.time.LocalDate;
import java.time.Year;
import java.util.Random;
import java.util.stream.IntStream;


public class MessageFactory {
    private final static Random random = new Random();
    private final static double CHANCE_SPACE = 0.2;
    private final static double CHANCE_EMOJI = 0.1;
    private final static int EMOJI_START = 0x1F600;
    private final static int EMOJI_END = 0x1F64F;

    public MessageFactory() {
    }

    public static boolean isEmoji(int c) {
        return EMOJI_START <= c && c <= EMOJI_END;
    }

    public static Message generateMessage() {
        int year = random.nextInt(2022, LocalDate.now().getYear() + 1);
        int day = random.nextInt(1, Year.isLeap(year) ? 367 : 366);
        LocalDate date = LocalDate.ofYearDay(year, day);

        var fio = IntStream.range(0, 3)
                .mapToObj(x -> (char) (random.nextInt(26) + 'a'))
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);

        int textLenght = random.nextInt(0, 100);
        var text = IntStream.range(0, textLenght)
                .mapToObj(x -> {
                    if (random.nextDouble() < CHANCE_EMOJI) {
                        int emojiCodePoint = random.nextInt(EMOJI_START, EMOJI_END + 1);
                        return " " + new String(Character.toChars(emojiCodePoint)) + " ";
                    } else {
                        char ch = (char) (random.nextInt(26) + 'a');
                        return random.nextDouble() < CHANCE_SPACE ? ch + " " : ch;
                    }
                })
                .reduce("", (str, ch) -> str + ch, (x, y) -> y);

        return new Message(date, fio, text);
    }


}