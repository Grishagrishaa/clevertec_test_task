package ru.clevertec.clevertecTaskRest.cache.lruCache;


import ru.clevertec.clevertecTaskRest.cache.api.ICache;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.CacheElement;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.api.ILinkedListNode;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;



public class ICacheLRUImpl<K, V> implements ICache<K, V> {
    private final int size;
    private final Map<K, ILinkedListNode<CacheElement<K, V>>> keyMap;
    private final DoublyLinkedList<CacheElement<K, V>> cacheElements;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public ICacheLRUImpl(int size) {
        this.size = size;
        this.keyMap = new ConcurrentHashMap<>(size);
        this.cacheElements = new DoublyLinkedList<>();
    }

    @Override
    public V put(K key, V value) {
        this.lock.writeLock().lock();
        try {
            CacheElement<K, V> item = new CacheElement<K, V>(key, value);
            ILinkedListNode<CacheElement<K, V>> newNode;
            if (this.keyMap.containsKey(key)) {
                ILinkedListNode<CacheElement<K, V>> node = this.keyMap.get(key);
                newNode = cacheElements.updateAndMoveToFront(node, item);
            } else {
                if (this.size() >= this.size) {
                    this.evictElement();
                }
                newNode = this.cacheElements.add(item);
            }
            if (newNode.isEmpty()) {
                return null;
            }
            this.keyMap.put(key, newNode);
            return value;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();
        try {
            ILinkedListNode<CacheElement<K, V>> iLinkedListNode = this.keyMap.get(key);
            if (iLinkedListNode != null && !iLinkedListNode.isEmpty()) {
                keyMap.put(key, this.cacheElements.moveToFront(iLinkedListNode));
                return Optional.of(iLinkedListNode.getElement().getValue());
            }
            return Optional.empty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean remove(K key) {
        this.lock.writeLock().lock();
        try {
            if (!keyMap.containsKey(key) || cacheElements.size() == 0) return false;

            ILinkedListNode<CacheElement<K, V>> cacheNode = keyMap.get(key);

            cacheNode.getPrev().setNext(cacheNode.getNext());
            cacheNode.getNext().setPrev(cacheNode.getPrev());

            cacheElements.remove(cacheNode.getElement());
            keyMap.remove(key);
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int size() {
        this.lock.readLock().lock();
        try {
            return cacheElements.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    private boolean evictElement() {
        this.lock.writeLock().lock();
        try {
            ILinkedListNode<CacheElement<K, V>> iLinkedListNode = cacheElements.removeTail();
            if (iLinkedListNode.isEmpty()) {
                return false;
            }
            keyMap.remove(iLinkedListNode.getElement().getKey());
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
