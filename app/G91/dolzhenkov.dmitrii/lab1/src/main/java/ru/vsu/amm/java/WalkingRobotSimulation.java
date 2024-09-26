package ru.vsu.amm.java;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WalkingRobotSimulation {
    
    private static final int[][] DIRECTION = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public static int maxDistance(int[] commands, int[][] obstacles) {
        int x = 0;
        int y = 0;
        int maxDistance = 0;
        int d = 0;
        for (int command : commands) {
            switch (command) {
                case -1:
                    d = (d + 1) % 4;
                    break;
                case -2:
                    d = (d + 3) % 4;
                    break;
                case 1, 2, 3, 4, 5, 6, 7, 8, 9:
                    for (int i = 0; i < command; i++) {
                        int nextX = x + DIRECTION[d][0];
                        int nextY = y + DIRECTION[d][1];
                        boolean flag = false;
                        for (int[] obstacle : obstacles) {
                            if (obstacle[0] == nextX && obstacle[1] == nextY) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            break;
                        }
                        x = nextX;
                        y = nextY;
                        maxDistance = Math.max(maxDistance, x * x + y * y);
                    }
                    break;
                default:
                    return -1;
            }
        }
        return maxDistance;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print(MessagesConsole.ENTERING_NUMBER_OF_OBSTACLES);
            int countObstacles = in.nextInt();
            int[][] obstacles = new int[countObstacles][2];
            for (int i = 0; i < countObstacles; i++) {
                System.out.printf(MessagesConsole.ENTERING_OBSTACLE, i + 1);
                obstacles[i][0] = in.nextInt();
                obstacles[i][1] = in.nextInt();
            }

            System.out.print(MessagesConsole.ENTERING_NUMBER_OF_COMMANDS);
            int countCommands = in.nextInt();
            int[] commands = new int[countCommands];
            for (int i = 0; i < countCommands; i++) {
                System.out.printf(MessagesConsole.ENTERING_COMMAND, i + 1);
                commands[i] = in.nextInt();
            }

            int answer = maxDistance(commands, obstacles);
            if (answer >= 0) {
                System.out.println(MessagesConsole.ANSWER + answer);
            } else {
                System.out.println(MessagesConsole.ERROR_MESSAGE);
            }
        } catch (InputMismatchException e) {
            System.out.println(MessagesConsole.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
