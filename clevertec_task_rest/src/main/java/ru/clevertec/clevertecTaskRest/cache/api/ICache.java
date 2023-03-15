package ru.clevertec.clevertecTaskRest.cache.api;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ICache<K, V> {
    V put(K key, V value);

    Optional<V> get(K key);

    default boolean remove(K key){
        return false;
    }
}
