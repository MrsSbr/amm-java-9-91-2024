package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Entity.Answers;
import ru.vsu.amm.java.Entity.Reply;
import ru.vsu.amm.java.Constans.Constants;
import ru.vsu.amm.java.Enums.CarBrand;
import ru.vsu.amm.java.Service.AnswersService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswersServiceTest {
    private static AnswersService answerService;
    private List<Reply> replyList;
    private Answers answersContainer;

    @Test
    public void testMostPopularBrand() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 40)
        );

        Answers answersContainer = new Answers(replyList);
        answerService = new AnswersService(answersContainer);

        CarBrand result = answerService.mostPopularBrand();

        assertEquals(CarBrand.BMW, result);
    }

    @Test
    public void testBrandByAge() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 30),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.HONDA, 40),
                new Reply(CarBrand.VOLKSWAGEN, 40)
        );

        Answers answersContainer = new Answers(replyList);
        answerService = new AnswersService(answersContainer);

        List<CarBrand> result = answerService.brandByAge();

        assertEquals(CarBrand.BMW, result.get(20 - Constants.MIN_AGE));
        assertEquals(CarBrand.TOYOTA, result.get(30 - Constants.MIN_AGE));
        assertEquals(CarBrand.HONDA, result.get(40 - Constants.MIN_AGE));
    }

    @Test
    public void testUniqueBrands() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 30),
                new Reply(CarBrand.HONDA, 40)
        );

        Answers answersContainer = new Answers(replyList);
        answerService = new AnswersService(answersContainer);

        List<CarBrand> result = answerService.uniqueBrands();

        assertEquals(2, result.size());
        assertTrue(result.contains(CarBrand.TOYOTA));
        assertTrue(result.contains(CarBrand.HONDA));
    }
}