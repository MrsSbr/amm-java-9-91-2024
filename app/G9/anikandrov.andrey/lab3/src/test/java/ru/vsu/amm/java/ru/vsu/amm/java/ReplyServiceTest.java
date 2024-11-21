package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Entity.Reply;
import ru.vsu.amm.java.Constans.Constants;
import ru.vsu.amm.java.Enums.CarBrand;
import ru.vsu.amm.java.Service.ReplyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReplyServiceTest {

    @Test
    public void testMostPopularBrand() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 40)
        );

        List<CarBrand> result = ReplyService.mostPopularBrand(replyList);

        assertNotNull(result);
        assertTrue(result.contains(CarBrand.BMW));
        assertFalse(result.contains(CarBrand.TOYOTA));
    }

    @Test
    public void testBrandByAge() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.VOLKSWAGEN, 40)
        );

        List<CarBrand> result = ReplyService.brandByAge(replyList);

        assertNotNull(result);
        assertTrue(result.contains(CarBrand.BMW));
        assertTrue(result.contains(CarBrand.TOYOTA));
        assertFalse(result.contains(CarBrand.HONDA));
        assertEquals(CarBrand.BMW, result.get(20 - Constants.MIN_AGE));
        assertEquals(CarBrand.TOYOTA, result.get(30 - Constants.MIN_AGE));
        assertEquals(CarBrand.VOLKSWAGEN, result.get(40 - Constants.MIN_AGE));
    }

    @Test
    public void testUniqueBrands() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 30),
                new Reply(CarBrand.HONDA, 40)
        );

        List<CarBrand> result = ReplyService.uniqueBrands(replyList);

        assertEquals(2, result.size());
        assertTrue(result.contains(CarBrand.TOYOTA));
        assertTrue(result.contains(CarBrand.HONDA));

    }
}