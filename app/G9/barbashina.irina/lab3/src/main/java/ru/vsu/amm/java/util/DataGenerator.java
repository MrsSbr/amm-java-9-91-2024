package ru.vsu.amm.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ru.vsu.amm.java.entity.Dish;
import ru.vsu.amm.java.entity.Order;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;

public class DataGenerator {

    private static final int ORDERS = 2341;

    public static Random random = new Random();

    private static final TypeDish[] DISHES = TypeDish.values();

    private static final Ingredients[] INGREDIENTS = Ingredients.values();


    private static List<Ingredients> generateRandomIngredients(TypeDish dish) {
        List<Ingredients> ingredients = new ArrayList<>();
        switch (dish) {
            case BORCH -> {
                ingredients.addAll(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE,
                        Ingredients.POTATO, Ingredients.CARROT, Ingredients.ONION,
                        Ingredients.GARLIC, Ingredients.MEET));
                break;
            }
            case KARBONARA -> {
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
            case CHARLOTTE -> {
                ingredients.addAll(Arrays.asList(Ingredients.APPLE, Ingredients.FLOUR,
                        Ingredients.SUGAR, Ingredients.BUTTER, Ingredients.EGG,
                        Ingredients.CINNAMON));
                break;
            }
        }
        return ingredients;
    }

    public static Dish generateRandomDish() {
        TypeDish typeDish = DISHES[random.nextInt(DISHES.length)];
        List<Ingredients> ingredients = generateRandomIngredients(typeDish);
        int price = 100 + random.nextInt(901);
        return Dish.builder()
                .dish(typeDish)
                .ingredients(ingredients)
                .price(price)
                .build();
    }

    public static Order generateRandomOrder() {

        ArrayList<Dish> dishes = new ArrayList<>();
        int dishCount = random.nextInt(5) + 1;
        for (int i = 0; i < dishCount; i++) {
            dishes.add(generateRandomDish());
        }
        return Order.builder()
                .dishes(dishes)
                .build();
    }
}
