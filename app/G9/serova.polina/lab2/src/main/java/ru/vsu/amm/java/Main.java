package ru.vsu.amm.java;


public class Main {

    public static void main(String[] args) {
        Confectionery confectionery = new Confectionery("Шоколадная фабрика");
        Confectioner confectioner = new Confectioner("Виталий", 12000.24);
        Taster taster = new Taster("Тамара", 73000.01);

        confectionery.hireWorker(confectioner);
        confectionery.hireWorker(taster);

        System.out.println(confectionery);
        confectioner.createRecipe("Candy");
        confectioner.doWork();
        taster.doWork();
        taster.evaluateDessert("Candy", 10);
    }
}