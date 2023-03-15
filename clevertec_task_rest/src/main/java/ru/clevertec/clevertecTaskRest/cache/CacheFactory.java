package ru.clevertec.clevertecTaskRest.cache;

import org.springframework.stereotype.Component;
import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.cache.enums.ECacheType;
import ru.clevertec.clevertecTaskRest.cache.lfuCache.ICacheLFUImpl;
import ru.clevertec.clevertecTaskRest.cache.lruCache.ICacheLRUImpl;

@Component
public class CacheFactory {
    public<K, V> ICache<K, V> getCache(Integer size, ECacheType ECacheType){
        return switch (ECacheType) {
            case LFU -> new ICacheLFUImpl<>(size);
            case LRU -> new ICacheLRUImpl<>(size);
        };
    }
}
