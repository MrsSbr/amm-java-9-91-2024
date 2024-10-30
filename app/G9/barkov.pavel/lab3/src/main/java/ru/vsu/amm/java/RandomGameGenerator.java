package ru.vsu.amm.java;

import java.util.*;

public class RandomGameGenerator {
    public static final Random random = new Random();
    private RandomGameGenerator(){}

    public static GameRecord Generate(){
        String[] genres = new String[]{"warGame","logical","card","rpg","euroGame"};
        Date data = new Date(random.nextInt(124)+1900,random.nextInt(11), random.nextInt(28));
        Integer part = random.nextInt(12);
        return  new GameRecord(data,"Game"+part, Genre.valueOf(genres[random.nextInt(5)]),
                random.nextInt(5000));
    }
}
