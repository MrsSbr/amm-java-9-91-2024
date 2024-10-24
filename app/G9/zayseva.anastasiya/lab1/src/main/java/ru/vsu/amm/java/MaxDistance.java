package ru.vsu.amm.java;

public class MaxDistance {
    public static int maxDistToClosest(int[] seats) {
        int maxDistance = 0;
        int lastOccupied = -1;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                if (lastOccupied == -1) {
                    maxDistance = i;
                } else {
                    int distance = (i - lastOccupied) / 2;
                    maxDistance = Math.max(maxDistance, distance);
                }
                lastOccupied = i;
            }
        }
        maxDistance = Math.max(maxDistance, seats.length - 1 - lastOccupied);
        return maxDistance;
    }
}