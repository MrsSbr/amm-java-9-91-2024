package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.SongPlayback;
import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.service.MusicService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTests {
    private Map<String, List<SongPlayback>> songPlaybacks;

    @BeforeEach
    public void start() {
        songPlaybacks = generateStreamingData();
    }

    @Test
    public void amountOfRecentStreamsTest() {
        int amount = MusicService.getAmountOfRecentStreams(songPlaybacks,
                "title2", "artist2", Genre.POP);
        assertEquals(1, amount);
    }

    @Test
    public void amountOfRecentStreamsTestNull() {
        assertThrows(NullPointerException.class, () -> MusicService.getAmountOfRecentStreams(null,
                "title2", "artist2", Genre.POP));
    }

    @Test
    public void amountOfRecentStreamsTestEmpty() {
        int amount = MusicService.getAmountOfRecentStreams(new HashMap<>(),
                "title2", "artist2", Genre.POP);
        assertEquals(0, amount);
    }

    @Test
    public void userStreamingDataTest() {
        Map<String, Set<String>> streamingData = MusicService.getUserStreamingData(songPlaybacks);
        assertEquals(new HashMap<String, Set<String>>() {{
                put("user1", new HashSet<>() {{
                    add("title1 by artist1, CLASSICAL");
                    add("title2 by artist2, POP");
                }});
                put("user2", new HashSet<>() {{
                    add("title3 by artist3, INDIE");
                    add("title4 by artist4, EXPERIMENTAL");
                }});
                put("user3", new HashSet<>() {{
                    add("title5 by artist5, ROCK");
                    add("title6 by artist6, INDIE");
                }});
            }
        }, streamingData);
    }

    @Test
    public void userStreamingDataTestNull() {
        assertThrows(NullPointerException.class, () -> MusicService.getUserStreamingData(null));
    }

    @Test
    public void userStreamingDataTestEmpty() {
        assertEquals(new HashMap<>(), MusicService.getUserStreamingData(new HashMap<>()));
    }

    @Test
    public void userStreamingDataLeastPopularTest() {
        Map<String, Set<String>> streamingData = MusicService.getUserStreamingDataLeastPopular(songPlaybacks);
        assertEquals(new HashMap<String, Set<String>>() {{
                put("user1", new HashSet<>() {{
                    add("title1 by artist1, CLASSICAL");
                }});
                put("user2", new HashSet<>() {{
                    add("title4 by artist4, EXPERIMENTAL");
                }});
                put("user3", new HashSet<>() {{
                    add("title5 by artist5, ROCK");
                    add("title6 by artist6, INDIE");
                }});
            }
        }, streamingData);
    }

    @Test
    public void userStreamingDataLeastPopularTestNull() {
        assertThrows(NullPointerException.class, () -> MusicService.getUserStreamingDataLeastPopular(null));
    }

    @Test
    public void userStreamingDataLeastPopularTestEmpty() {
        assertEquals(new HashMap<>(), MusicService.getUserStreamingDataLeastPopular(new HashMap<>()));
    }

    @Test
    public void userStreamingDataMostFavoriteTest() {
        Map<String, String> streamingData = MusicService.getUserStreamingDataMostFavorite(songPlaybacks);
        assertEquals(new HashMap<String, String>() {{
                put("user1", "title1 by artist1, CLASSICAL");
                put("user2", "title3 by artist3, INDIE");
                put("user3", "title5 by artist5, ROCK");
            }
        }, streamingData);
    }

    @Test
    public void userStreamingDataMostFavoriteTestNull() {
        assertThrows(NullPointerException.class, () -> MusicService.getUserStreamingDataMostFavorite(null));
    }

    @Test
    public void userStreamingDataMostFavoriteTestEmpty() {
        assertEquals(new HashMap<>(), MusicService.getUserStreamingDataMostFavorite(new HashMap<>()));
    }

    public static Map<String, List<SongPlayback>> generateStreamingData() {
        return new HashMap<>() {{
                put("user1", new ArrayList<>(List.of(
                        new SongPlayback("title1", "artist1",
                                Genre.CLASSICAL, LocalDate.of(2020, 9, 15)),
                        new SongPlayback("title2", "artist2",
                                Genre.POP, LocalDate.now()),
                        new SongPlayback("title1", "artist1",
                                Genre.CLASSICAL, LocalDate.of(2023, 9, 12))
                )));
                put("user2", new ArrayList<>(List.of(
                        new SongPlayback("title3", "artist3",
                                Genre.INDIE, LocalDate.of(2024, 10, 15)),
                        new SongPlayback("title3", "artist3",
                                Genre.INDIE, LocalDate.of(2020, 10, 15)),
                        new SongPlayback("title4", "artist4",
                                Genre.EXPERIMENTAL, LocalDate.of(2020, 10, 15))
                )));
                put("user3", new ArrayList<>(List.of(
                        new SongPlayback("title5", "artist5",
                                Genre.ROCK, LocalDate.of(2023, 10, 15)),
                        new SongPlayback("title5", "artist5",
                                Genre.ROCK, LocalDate.of(2020, 10, 15)),
                        new SongPlayback("title6", "artist6",
                                Genre.INDIE, LocalDate.of(2020, 10, 15))
                )));
            }
        };
    }
}