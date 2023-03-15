package ru.clevertec.clevertecTaskRest.cache.lruCache;

import org.junit.jupiter.api.Test;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ICacheLRUImplTest {
    private ICache<Long, String> cache = new ICacheLRUImpl<>(5);

    @Test
    public void addDataTest() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        assertAll(
                () -> assertEquals("1", cache.get(1L).get()),
                () -> assertEquals("2", cache.get(2L).get()),
                () -> assertEquals("3", cache.get(3L).get()),
                () -> assertEquals("4", cache.get(4L).get()),
                () -> assertEquals("5", cache.get(5L).get())
                );
    }

    @Test
    public void EvictDataTest() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");
        assertFalse(cache.get(1L).isPresent());
    }

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
    void removeDataTest() {
        cache.put(1L, "1");
        cache.put(2L, "2");
        cache.put(3L, "3");
        cache.put(4L, "4");
        cache.put(5L, "5");

        cache.remove(1L);
        assertEquals(Optional.empty(), cache.get(1L));
    }
}
