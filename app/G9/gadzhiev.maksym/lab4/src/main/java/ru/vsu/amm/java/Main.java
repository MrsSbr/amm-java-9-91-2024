package ru.vsu.amm.java;

import ru.vsu.amm.java.Entity.Message;
import ru.vsu.amm.java.Service.MessageService;
import ru.vsu.amm.java.Util.FileWorker;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private final static String PATH = "app/G9/gadzhiev.maksym/lab4/src/main/java/ru/vsu/amm/java/Resources/Message.txt";
    private final static Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {

        List<Message> messagess = Arrays.asList(
                new Message(LocalDate.now().minusDays(3), "Gadzhiev", "Hello! Maksim"),
                new Message(LocalDate.now().minusDays(3), "Aksenov", "Hello! How are you, Maksim?"),
                new Message(LocalDate.now().minusDays(2), "Gadzhiev", "Bye! Maksim")
        );

        Set<DayOfWeek> result = MessageService.dayWithMostWord(messagess, "Maksim");
        System.out.println(result);
        try {
            FileWorker.generateFile(PATH, 10);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Not create file\n");
        }

        List<Message> messages;

        try {
            messages = FileWorker.getFromFile(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Not read file\n");
            return;
        }

        try {
            System.out.println("Человек, которому было отправлено самое длинное сообщение: ");
            System.out.println(MessageService.manWithLongestMessage(null));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NUll pointer\n");
        }

        try {
            System.out.println("В какой день недели, слово, введенное с клавиатуры, встречается больше всего раз: ");
            System.out.println(MessageService.leastUsedEmotionsInTheLastYear(messages));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NUll pointer\n");
        }

        try {
            Scanner console = new Scanner(System.in);
            String word = console.nextLine();
            System.out.println("Самый наименее употребимый эмоции за последний год: ");
            System.out.println(MessageService.dayWithMostWord(messages, word));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "NULL pointer\n");
        }


    }
}