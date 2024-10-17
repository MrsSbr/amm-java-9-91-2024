package ru.vsu.amm.java.catbreed;

public class Directory {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 3;

    public static void main(String[] args) {

        CatBreed longhair = new Longhair("Munchkin", "Long-haired, cat with short legs.");
        CatBreed semiLonghair = new SemiLonghair("Maine Coon", "Large, friendly cat with semi-long hair.");
        CatBreed shorthair = new Shorthair("British Shorthair", "Short-haired, sturdy cat.");
        CatBreed siamese = new SiameseOrientalShorthair("Siamese", "Slim, vocal cat with short hair.");

        longhair.displayInfo();
        semiLonghair.displayInfo();
        shorthair.displayInfo();
        siamese.displayInfo();

        int randomValue = (int) (Math.random() * (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;

        CatBreed[] cats = {longhair, semiLonghair, shorthair, siamese};
        CatBreed myCat = cats[randomValue];

        if (myCat instanceof Longhair) {
            System.out.println("This is a Longhair cat.");
            ((Longhair) myCat).shed();
            ((Longhair) myCat).groom();
        } else if (myCat instanceof SemiLonghair) {
            System.out.println("This is a Semi-longhair cat.");
            ((SemiLonghair) myCat).play();
            ((SemiLonghair) myCat).groom();
        } else if (myCat instanceof Shorthair) {
            System.out.println("This is a Shorthair cat.");
            ((Shorthair) myCat).hunt();
        } else if (myCat instanceof SiameseOrientalShorthair) {
            System.out.println("This is a Siamese-Oriental Shorthair cat.");
            ((SiameseOrientalShorthair) myCat).vocalize();
        }


        System.out.println(myCat.compareSize(cats[(randomValue + 1) % 4]));
    }
}