package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Set;

public class Solve {
    static boolean isvalid(String board[][]) {
        Set<String> result = new HashSet<>();
        String current;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board.length; ++j) {
                current = board[i][j];
                if (current != ".") {
                    if (!result.add("row" + i + current) || !result.add("column" + j + current) || !result.add("square" + i / 3 + j / 3 + current)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
