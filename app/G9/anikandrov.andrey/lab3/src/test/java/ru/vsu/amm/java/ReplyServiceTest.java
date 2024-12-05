package ru.vsu.amm.java;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.vsu.amm.java.Entity.Reply;
import ru.vsu.amm.java.Constans.Constants;
import ru.vsu.amm.java.Enums.CarBrand;
import ru.vsu.amm.java.Service.ReplyService;

import java.util.List;
import java.util.Objects;

import static java.util.function.Predicate.isEqual;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ReplyServiceTest {

    @Test
    public void testMostPopularBrand() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 40)
        );

        List<CarBrand> result = ReplyService.mostPopularBrand(replyList);

        assertNotNull(result);

        assertEquals(1, result.size());

        //assertTrue(result.contains(CarBrand.BMW));
        assertThat(result, hasItem(CarBrand.BMW));

        //assertFalse(result.contains(CarBrand.TOYOTA));
        assertThat(result, not(hasItem(CarBrand.TOYOTA)));
    }

    @Test
    public void testMostPopularBrandTwoMostPopular() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.HONDA, 20),
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 20),
                new Reply(CarBrand.TOYOTA, 30)
        );

        List<CarBrand> result = ReplyService.mostPopularBrand(replyList);

        assertNotNull(result);

        assertEquals(2, result.size());

        assertThat(result, hasItem(CarBrand.TOYOTA));
        assertThat(result, hasItem(CarBrand.BMW));
        assertThat(result, not(hasItem(CarBrand.HONDA)));
    }

    @Test
    public void testMostPopularBrandEmpty() {
        List<Reply> replyList = List.of();
        assertEquals(0, replyList.size());
        assertTrue(replyList.isEmpty());
    }

    @Test
    public void testMostPopularBrandSameBrand() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.BMW, 30),
                new Reply(CarBrand.BMW, 40)
        );

        List<CarBrand> result = ReplyService.mostPopularBrand(replyList);

        assertNotNull(result);

        assertEquals(1, result.size());

        //assertTrue(result.contains(CarBrand.BMW));
        assertThat(result, hasItem(CarBrand.BMW));
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

        //assertTrue(result.contains(CarBrand.TOYOTA));
        //assertTrue(result.contains(CarBrand.HONDA));
        assertThat(result, hasItem(CarBrand.TOYOTA));
        assertThat(result, hasItem(CarBrand.HONDA));

        //assertFalse(result.contains(CarBrand.BMW));
        assertThat(result, not(hasItem(CarBrand.BMW)));
    }

    @Test
    public void testUniqueBrandsEmpty() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, 20),
                new Reply(CarBrand.TOYOTA, 30),
                new Reply(CarBrand.BMW, 30),
                new Reply(CarBrand.TOYOTA, 40)
        );

        List<CarBrand> result = ReplyService.uniqueBrands(replyList);

        assertEquals(0, result.size());

        assertTrue(result.isEmpty());
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

        assertEquals(3, result.stream()
                                    .filter(item -> item != null)
                                    .count());

        //assertTrue(result.contains(CarBrand.BMW));
        //assertTrue(result.contains(CarBrand.TOYOTA));
        assertThat(result, hasItem(CarBrand.BMW));
        assertThat(result, hasItem(CarBrand.TOYOTA));

        assertFalse(result.contains(CarBrand.HONDA));
    }

    @Test
    public void testBrandByAgeWrongAge() {
        List<Reply> replyList = List.of(
                new Reply(CarBrand.BMW, -10),
                new Reply(CarBrand.VOLKSWAGEN, -5)
        );

        List<CarBrand> result = ReplyService.brandByAge(replyList);

        assertEquals(0, result.stream()
                .filter(item -> item != null)
                .count());


        assertTrue(result.stream().allMatch(Objects::isNull));
    }

}