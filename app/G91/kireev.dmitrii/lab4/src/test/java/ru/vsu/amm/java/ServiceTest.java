package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Service.Service;
import ru.vsu.amm.java.Model.FileEntity;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    private final Service service = new Service();
    private final List<FileEntity> entities = getEntities();
    private final List<FileEntity> nullEntities = null;


    @Test
    public void testGetConfessionCountPerInstrument() {
        Map<TortureInstrument, Long> result = service.getConfessionCountPerInstrument(entities);
        assertEquals(2, result.size());
        assertEquals(1, result.get(TortureInstrument.PHYSICAL_BEATING));
        assertEquals(2, result.get(TortureInstrument.ELECTRIC_SHOCK));
    }

    @Test
    public void testNullGetConfessionCountPerInstrument() {
        Map<TortureInstrument, Long> result = service.getConfessionCountPerInstrument(nullEntities);
        assertEquals(result, new HashMap<>());
    }


    @Test
    public void testGetTortureDurationPerSuspect() {
        Map<String, Duration> result = service.getTortureDurationPerSuspect(entities);
        assertEquals(6, result.size());
        assertEquals("PT40M", result.get("David").toString());
        assertEquals("PT2H", result.get("Alice").toString());
    }


    @Test
    public void testNullGetTortureDurationPerSuspect() {
        Map<String, Duration> result = service.getTortureDurationPerSuspect(nullEntities);
        assertEquals(result, new HashMap<>());
    }


    @Test
    public void testGetTorturedByEveryInstrumentWithoutConfession() {
        List<String> result = service.getTorturedByEveryInstrumentWithoutConfession(entities);
        assertEquals(1, result.size());
        assertEquals("Alice", result.getFirst());

    }


    @Test
    public void testNullGetTorturedByEveryInstrumentWithoutConfession() {
        List<String> result = service.getTorturedByEveryInstrumentWithoutConfession(nullEntities);
        assertEquals(result, new ArrayList<>());
    }


    private List<FileEntity> getEntities() {
        return List.of(
                new FileEntity("Alice", TortureInstrument.WATERBOARDING, Duration.ofMinutes(30), false),
                new FileEntity("Alice", TortureInstrument.WHIP, Duration.ofMinutes(30), false),
                new FileEntity("Alice", TortureInstrument.ELECTRIC_SHOCK, Duration.ofMinutes(30), false),
                new FileEntity("Alice", TortureInstrument.PHYSICAL_BEATING, Duration.ofMinutes(30), false),
                new FileEntity("Bob", TortureInstrument.WHIP, Duration.ofMinutes(20), false),
                new FileEntity("Charlie", TortureInstrument.ELECTRIC_SHOCK, Duration.ofMinutes(10), true),
                new FileEntity("Chloe", TortureInstrument.PHYSICAL_BEATING, Duration.ofMinutes(60), true),
                new FileEntity("Carl", TortureInstrument.ELECTRIC_SHOCK, Duration.ofMinutes(10), true),
                new FileEntity("David", TortureInstrument.PHYSICAL_BEATING, Duration.ofMinutes(40), false));
    }


}
