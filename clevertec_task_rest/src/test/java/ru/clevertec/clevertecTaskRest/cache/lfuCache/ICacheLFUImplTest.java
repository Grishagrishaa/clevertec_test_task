package ru.clevertec.clevertecTaskRest.cache.lfuCache;

import org.junit.jupiter.api.Test;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ICacheLFUImplTest {
    private final ICache<Long, String> cache = new ICacheLFUImpl<>(5);

    @Test
    void updateDataTest() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        String expected = "New test - 5";
        cache.put(1L, expected);

        assertEquals(expected, cache.get(1L).get());
    }

    @Test
    void checkPutShouldEvictValue() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        cache.get(5L);
        cache.get(5L);
        cache.get(5L);
        cache.get(5L);

        cache.put(6L, "6");

        assertEquals(Optional.empty(), cache.get(1L));
    }


    @Test
    void checkRemoveShouldReturnFalseWhenKeyIsNotPresent() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        assertFalse(cache.remove(6L));
    }

    @Test
    void checkRemoveShouldRemoveEntry() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        cache.remove(5L);

        assertEquals(Optional.empty(), cache.get(5L));
    }
}