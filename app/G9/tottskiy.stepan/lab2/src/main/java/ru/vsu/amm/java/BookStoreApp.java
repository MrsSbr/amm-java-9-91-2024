package ru.vsu.amm.java;

public class BookStoreApp {
    public static void main(String[] args) {
        // Создаем экземпляры книг
        Book fictionBook = new FictionBook("To Kill a Mockingbird", "Harper Lee", 10.99, 300);
        Book scienceBook = new ScienceBook("A Brief History of Time", "Stephen Hawking", 15.99, 390);
        Book fictionBookAnother = new FictionBook("1984", "George Orwell", 8.99, 150);

        // Вывод информации о книгах
        System.out.println(fictionBook);
        System.out.println(scienceBook);
        System.out.println(fictionBookAnother);

        // Демонстрация переопределения метода getDescription
        System.out.println("Description of fictionBook: " + fictionBook.getDescription());
        System.out.println("Description of scienceBook: " + scienceBook.getDescription());

        // Сравнение объектов
        System.out.println("fictionBook equals fictionBookAnother: " + fictionBook.equals(fictionBookAnother));


        if (fictionBook instanceof FictionBook) {
            System.out.println(fictionBook.getTitle() + " is a Fiction Book");
        }
        if (scienceBook instanceof ScienceBook) {
            System.out.println(scienceBook.getTitle() + " is a Science Book");
        }
    }
}
