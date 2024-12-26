package ru.vsu.amm.java;

import ru.vsu.amm.java.Entity.Message;
import ru.vsu.amm.java.Service.MessageService;
import ru.vsu.amm.java.Util.FileWorker;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static String PATH = "app/G9/gadzhiev.maksym/lab4/src/main/java/ru/vsu/amm/java/Resources/Message.txt";
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {


        try {
            FileWorker.generateFile(PATH, 10);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not create file\n" + Arrays.toString(e.getStackTrace()));
        }

        List<Message> messages;

        try {
            messages = FileWorker.getFromFile(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not read file\n" + Arrays.toString(e.getStackTrace()));
            return;
        }

        try {
            System.out.println("The person who received the longest message was: ");
            System.out.println(MessageService.manWithLongestMessage(messages));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NUll pointer\n" + Arrays.toString(e.getStackTrace()));
        }

        try {
            Scanner console = new Scanner(System.in);
            String word = console.nextLine();
            System.out.println("On what day of the week does the word typed from the keyboard occur the most times: ");
            System.out.println(MessageService.dayWithMostWord(messages, word));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NUll pointer\n" + Arrays.toString(e.getStackTrace()));
        }

        try {
            System.out.println("Least used emotion in the last year: ");
            System.out.println(MessageService.leastUsedEmotionsInTheLastYear(messages));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NULL pointer\n" + Arrays.toString(e.getStackTrace()));
        }

    }
}