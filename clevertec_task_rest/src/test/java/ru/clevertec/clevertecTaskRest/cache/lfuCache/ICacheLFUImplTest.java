package com.clevertec.clevertectesttaskrest.cache.lfu;

import com.clevertec.clevertectesttaskrest.cache.Cache;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {

    @Test
    void checkPutShouldReturnFalseWhenCapacityIsZero() {
        Cache<Long, String> cache = new LFUCache<>(0);

        assertFalse(cache.put(1L, "Some-value"));
    }

    @Test
    void checkPutShouldUpdateWhenKeyIsPresent() {
        Cache<Long, String> cache = new LFUCache<>(2);
        cache.put(1L, "Some-value-1");
        cache.put(1L, "New-value-1");

        assertEquals("New-value-1", cache.get(1L).get());
    }

    @Test
    void checkPutShouldEvictValue() {
        Cache<Long, String> cache = new LFUCache<>(2);
        cache.put(1L, "Some-value-1");
        cache.put(2L, "Some-value-2");
        cache.get(2L);
        cache.get(2L);
        cache.put(3L, "Some-value-3");

        assertEquals(Optional.empty(), cache.get(1L));
    }


    @Test
    void checkRemoveShouldReturnFalseWhenKeyIsNotPresent() {
        Cache<Long, String> cache = new LFUCache<>(2);
        cache.put(1L, "Some-value-1");
        cache.put(2L, "Some-value-2");

        assertFalse(cache.remove(10L));
    }

    @Test
    void checkRemoveShouldRemoveEntry() {
        Cache<Long, String> cache = new LFUCache<>(2);
        cache.put(1L, "Some-value-1");
        cache.put(2L, "Some-value-2");
        cache.get(1L);
        cache.remove(2L);

        assertEquals(Optional.empty(), cache.get(2L));
    }
}