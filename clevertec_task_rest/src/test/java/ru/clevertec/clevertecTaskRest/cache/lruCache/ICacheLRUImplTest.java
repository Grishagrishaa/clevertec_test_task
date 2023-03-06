package com.baeldung.lrucache;

import com.baeldung.lrucache.api.ICache;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import static org.junit.Assert.*;

public class ICacheLRUImplUnitTest {

    @Test
    public void addSomeDataToCache_WhenGetData_ThenIsEqualWithCacheElement() {
        ICacheLRUImpl<String, String> lruCache = new ICacheLRUImpl<>(3);
        lruCache.put("1", "test1");
        lruCache.put("2", "test2");
        lruCache.put("3", "test3");
        assertEquals("test1", lruCache.get("1").get());
        assertEquals("test2", lruCache.get("2").get());
        assertEquals("test3", lruCache.get("3").get());
    }

    @Test
    public void addDataToCacheToTheNumberOfSize_WhenAddOneMoreData_ThenLeastRecentlyDataWillEvict() {
        ICacheLRUImpl<String, String> lruCache = new ICacheLRUImpl<>(3);
        lruCache.put("1", "test1");
        lruCache.put("2", "test2");
        lruCache.put("3", "test3");
        lruCache.put("4", "test4");
        assertFalse(lruCache.get("1").isPresent());
    }

    @Test
    public void runMultiThreadTask_WhenPutDataInConcurrentToCache_ThenNoDataLost() throws Exception {
        final int size = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        ICache<Integer, String> ICache = new ICacheLRUImpl<>(size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        try {
            IntStream.range(0, size).<Runnable>mapToObj(key -> () -> {
                ICache.put(key, "value" + key);
                countDownLatch.countDown();
            }).forEach(executorService::submit);
            countDownLatch.await();
        } finally {
            executorService.shutdown();
        }
        assertEquals(ICache.size(), size);
        IntStream.range(0, size).forEach(i -> assertEquals("value" + i, ICache.get(i).get()));
    }
}
