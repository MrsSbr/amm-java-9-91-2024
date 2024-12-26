package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Entity.Message;
import ru.vsu.amm.java.Service.MessageService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TelegramTest {

    @Test
    void testManWithLongestMessageNull() {
        assertThrows(NullPointerException.class, () ->
                MessageService.manWithLongestMessage(null));
    }

    @Test
    void testDayWithMostWordNullMessages() {
        assertThrows(NullPointerException.class, () ->
                MessageService.dayWithMostWord(null, "gf"));
    }

    @Test
    void testDayWithMostWordNullAll() {
        assertThrows(NullPointerException.class, () ->
                MessageService.dayWithMostWord(null, null));
    }

    @Test
    void testDayWithMostWordNullWord() {
        List<Message> messages = Arrays.asList(
                new Message(LocalDate.now().minusYears(2), "Gadzhiev", "Hello!"),
                new Message(LocalDate.now().minusYears(2), "Aksenov", "Hello!"),
                new Message(LocalDate.now().minusYears(2), "Gadzhiev", "Bye!")
        );

        assertThrows(NullPointerException.class, () ->
                MessageService.dayWithMostWord(messages, null));
    }

    @Test
    void testLeastUsedEmotionsInTheLastYearNull() {
        assertThrows(NullPointerException.class, () ->
                MessageService.leastUsedEmotionsInTheLastYear(null));
    }

    @Test
    void testManWithLongestMessage() {
        List<Message> messages = Arrays.asList(
                new Message(LocalDate.now().minusYears(2), "Gadzhiev", "Hello!"),
                new Message(LocalDate.now().minusYears(2), "Aksenov", "Hello! How are you?"),
                new Message(LocalDate.now().minusYears(2), "Gadzhiev", "Bye!")
        );

        Set<String> result = MessageService.manWithLongestMessage(messages);
        assertEquals(Set.of("Aksenov"), result);
    }

    @Test
    void testAllManWithLongestMessage() {
        List<Message> messages = Arrays.asList(
                new Message(LocalDate.now().minusYears(2), "Gadzhiev", "Hello!"),
                new Message(LocalDate.now().minusYears(2), "Aksenov", "Hello!")

        );

        Set<String> result = MessageService.manWithLongestMessage(messages);
        assertEquals(Set.of("Aksenov", "Gadzhiev"), result);
    }

    @Test
    void testDayWithMostWordMessages() {
        List<Message> messages = Arrays.asList(
                new Message(LocalDate.now().minusDays(3), "Gadzhiev", "Hello! Maksim"),
                new Message(LocalDate.now().minusDays(3), "Aksenov", "Hello! How are you, Maksim?"),
                new Message(LocalDate.now().minusDays(2), "Gadzhiev", "Bye! Maksim")
        );

        Set<DayOfWeek> result = MessageService.dayWithMostWord(messages, "Maksim");
        assertEquals(Set.of(LocalDate.now().minusDays(3).getDayOfWeek()), result);
    }

    @Test
    void testLeastUsedEmotionsInTheLastYear() {
        List<Message> messages = Arrays.asList(
                new Message(LocalDate.now().minusDays(3), "Gadzhiev", "Hello! Maksim ️✌️"),
                new Message(LocalDate.now().minusDays(3), "Aksenov", "Hello! ✌️ How are you, Maksim"),
                new Message(LocalDate.now().minusDays(2), "Gadzhiev", "Bye! Maksim")
        );

        String result = MessageService.leastUsedEmotionsInTheLastYear(messages);
        assertEquals("️✌️", result);
    }



}
