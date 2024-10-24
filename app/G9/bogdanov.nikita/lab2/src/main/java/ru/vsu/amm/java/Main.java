package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {

        Movie actionMovie = new ActionMovie("Die Hard", 130, 2);
        Movie comedyMovie = new ComedyMovie("The Mask", 100, "Jim Carrey");
        Movie dramaMovie = new DramaMovie("Hatico", 117, true);

        System.out.println(actionMovie);
        System.out.println(comedyMovie);
        System.out.println(dramaMovie);

        System.out.println(((ActionMovie) actionMovie).getDescription());

        Movie anotherActionMovie = new ActionMovie("Die Hard", 130, 2);
        System.out.println("Movies are equal: " + actionMovie.equals(anotherActionMovie));

        if (comedyMovie instanceof ComedyMovie) {
            System.out.println("This is a comedy movie.");
        }
    }
}