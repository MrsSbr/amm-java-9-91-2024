package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DataGenerator {

    private static final int ORDERS = 2341;

    private static final Random random = new Random();

    private static final TypeDish[] DISHES = {
            TypeDish.BORCH,
            TypeDish.PASTA,
            TypeDish.STEW,
            TypeDish.CAESAR,
            TypeDish.CAKE
    };

    private static final Ingredients[] INGREDIENTS = {
         Ingredients.APPLE,
         Ingredients.BEET,
         Ingredients.CABBAGE,
         Ingredients.BUTTER,
         Ingredients.CARROT,
         Ingredients.CHEESE,
         Ingredients.CINNAMON,
         Ingredients.CREAM,
         Ingredients.CROUTONS,
         Ingredients.EGG,
         Ingredients.EGGPLANT,
         Ingredients.FLOUR,
         Ingredients.GARLIC,
         Ingredients.MEET,
         Ingredients.ONION,
         Ingredients.PASTA,
         Ingredients.POTATO,
         Ingredients.SALAD,
         Ingredients.SAUCE,
         Ingredients.SPICES,
         Ingredients.SUGAR,
         Ingredients.TOMATO,
         Ingredients.ZUCCHINI
    };

    private static ArrayList<Ingredients> generateRandomIngredients(TypeDish dish) {
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        switch (dish) {
            case BORCH -> {
                ingredients.addAll(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE,
                        Ingredients.POTATO, Ingredients.CARROT, Ingredients.ONION,
                        Ingredients.GARLIC, Ingredients.MEET));
                break;
            }
            case PASTA -> {
                ingredients.addAll(Arrays.asList(Ingredients.PASTA, Ingredients.CREAM,
                        Ingredients.CHEESE, Ingredients.BUTTER, Ingredients.GARLIC));
                break;
            }
            case CAESAR -> {
                ingredients.addAll(Arrays.asList(Ingredients.MEET, Ingredients.CHEESE,
                        Ingredients.CROUTONS, Ingredients.SAUCE));
                break;
            }
            case STEW -> {
                ingredients.addAll(Arrays.asList(Ingredients.EGGPLANT, Ingredients.CABBAGE,
                        Ingredients.TOMATO, Ingredients.ONION, Ingredients.GARLIC,
                        Ingredients.BUTTER, Ingredients.SPICES));
                break;
            }
            case CAKE -> {
                ingredients.addAll(Arrays.asList(Ingredients.APPLE, Ingredients.FLOUR,
                        Ingredients.SUGAR, Ingredients.BUTTER, Ingredients.EGG,
                        Ingredients.CINNAMON));
                break;
            }
        }
        return ingredients;
    }

    public static Dish() {
        TypeDish typeDish = DISHES[random.nextInt(DISHES.length)];
        return;
        //int price = 100 + (random.nextInt() * 1000);
    }
}
