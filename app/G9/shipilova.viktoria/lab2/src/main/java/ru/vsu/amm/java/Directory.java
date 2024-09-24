package ru.vsu.amm.java;

public class Directory {
    public static void main(String[] args) {
        CatBreedImpl longhair = new Longhair("Persian", "Long-haired, elegant cat.");
        CatBreedImpl semiLonghair = new SemiLonghair("Maine Coon", "Large, friendly cat with semi-long hair.");
        CatBreedImpl shorthair = new Shorthair("British Shorthair", "Short-haired, sturdy cat.");
        CatBreedImpl siamese = new SiameseOrientalShorthair("Siamese", "Slim, vocal cat with short hair.");

        longhair.displayInfo();
        semiLonghair.displayInfo();
        shorthair.displayInfo();
        siamese.displayInfo();

        final int MIN_VALUE = 0;
        final int MAX_VALUE = 3;
        int randomValue = (int) (Math.random() * (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;

        CatBreedImpl[] cats = {longhair, semiLonghair, shorthair, siamese};
        CatBreedImpl myCat = cats[randomValue];

        if (myCat instanceof Longhair) {
            System.out.println("This is a Longhair cat.");
        } else if (myCat instanceof SemiLonghair) {
            System.out.println("This is a Semi-longhair cat.");
        } else if (myCat instanceof Shorthair) {
            System.out.println("This is a Shorthair cat.");
        } else if (myCat instanceof SiameseOrientalShorthair) {
            System.out.println("This is a Siamese-Oriental Shorthair cat.");
        }


        System.out.println(myCat.compareSize(cats[(randomValue + 1) % 4]));
    }
}