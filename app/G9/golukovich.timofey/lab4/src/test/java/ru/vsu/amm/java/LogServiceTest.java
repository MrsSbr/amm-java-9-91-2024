package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Log;
import ru.vsu.amm.java.enums.HttpResponseCode;
import ru.vsu.amm.java.enums.Resource;
import ru.vsu.amm.java.services.LogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LogServiceTest {

    private static final Random RANDOM = new Random();

    @Test
    public void responseCodeStatTest() {
        List<Log> logs = List.of(
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Redirection),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.ServerError),
                new Log(LocalDate.now(), Resource.StackOverflow, RANDOM.nextInt(0, 999999), HttpResponseCode.ClientError),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Success)
        );
        var statistics = LogService.getHttpResponseCodesStatistics(logs);


        assertEquals(3L, statistics.get(HttpResponseCode.Success));
        assertEquals(1L, statistics.get(HttpResponseCode.ServerError));
        assertEquals(1L, statistics.get(HttpResponseCode.ClientError));
        assertEquals(1L, statistics.get(HttpResponseCode.Redirection));
        assertFalse(statistics.containsKey(HttpResponseCode.Informational));
    }

    @Test
    public void resourceStatTest() {
        List<Log> logs = List.of(
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Redirection),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.ServerError),
                new Log(LocalDate.now(), Resource.StackOverflow, RANDOM.nextInt(0, 999999), HttpResponseCode.ClientError),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Success)
        );
        var statistics = LogService.getResourceStatistics(logs);


        assertEquals(3L, statistics.get(Resource.GitHub));
        assertEquals(2L, statistics.get(Resource.Spotify));
        assertEquals(1L, statistics.get(Resource.StackOverflow));
        assertFalse(statistics.containsKey(Resource.Habr));
        assertFalse(statistics.containsKey(Resource.YouTube));
    }

    @Test
    public void mostUnstableResourceTest() {
        List<Log> logs = List.of(
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Redirection),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.ServerError),
                new Log(LocalDate.now(), Resource.StackOverflow, RANDOM.nextInt(0, 999999), HttpResponseCode.ClientError),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Success)
        );
        var resource = LogService.getMostUnstableResource(logs);

        assertEquals(Resource.GitHub, resource);
    }

    @Test
    public void mostStableResourceTest() {
        List<Log> logs = List.of(
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Redirection),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.ServerError),
                new Log(LocalDate.now(), Resource.StackOverflow, RANDOM.nextInt(0, 999999), HttpResponseCode.ClientError),
                new Log(LocalDate.now(), Resource.Spotify, RANDOM.nextInt(0, 999999), HttpResponseCode.ServerError),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.GitHub, RANDOM.nextInt(0, 999999), HttpResponseCode.Success),
                new Log(LocalDate.now(), Resource.Habr, RANDOM.nextInt(0, 999999), HttpResponseCode.Success)
        );

        var resource = LogService.getMostStableResource(logs);
        assertEquals(Resource.Habr, resource);
    }
}
