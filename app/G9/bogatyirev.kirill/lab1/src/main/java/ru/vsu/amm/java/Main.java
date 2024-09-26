package ru.vsu.amm.java;
import java.util.Scanner;

public class Burgers {

    private static final int BIGTASTY_TOMATO_SLICE = 4;
    private static final int BIGTASTY_CHEESE_SLICE = 1;

    private static final int JUNIOR_TOMATO_SLICE = 2;
    private static final int JUNIOR_CHEESE_SLICE = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество ломтиков томата");
        int tomatoSlice = scanner.nextByte();

        System.out.println("Введите количество ломтиков сыра");
        int cheeseSlice = scanner.nextByte();

        int[] result = checkBurgers(tomatoSlice, cheeseSlice);

        if(result.length == 0)
        {
            System.out.print("[]");
        }
        else
            System.out.print("[" + result[0] + "," + result[1] + "]");

    }

    private static int[] checkBurgers(int tomatoSlices, int cheeseSlices){
        int cntBigTasty = 0;
        while(cntBigTasty <= tomatoSlices / BIGTASTY_TOMATO_SLICE &&
                cntBigTasty <= cheeseSlices / BIGTASTY_CHEESE_SLICE){

            int restTomato = tomatoSlices - cntBigTasty * BIGTASTY_TOMATO_SLICE;
            int restCheese = cheeseSlices - cntBigTasty * BIGTASTY_CHEESE_SLICE;

            int cntJunior = restCheese / JUNIOR_CHEESE_SLICE;

            if (cntJunior * JUNIOR_TOMATO_SLICE == restTomato) {
                return new int[]{cntBigTasty, cntJunior};
            }

            cntBigTasty++;
        }

        return new int[]{};
    }
}