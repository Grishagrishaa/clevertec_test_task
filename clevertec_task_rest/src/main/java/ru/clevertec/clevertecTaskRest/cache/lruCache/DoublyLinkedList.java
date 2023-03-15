package ru.clevertec.clevertecTaskRest.cache.lruCache;

import ru.clevertec.clevertecTaskRest.cache.lruCache.node.DummyLinkedListNode;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.LinkedListNode;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.api.ILinkedListNode;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DoublyLinkedList<T> {

    private DummyLinkedListNode<T> dummyNode;
    private ILinkedListNode<T> head;
    private ILinkedListNode<T> tail;
    private AtomicInteger size;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
 
    public DoublyLinkedList() {
        this.dummyNode = new DummyLinkedListNode<T>(this);
        clear();
    }

    public void clear() {
        this.lock.writeLock().lock();
        try {
            head = dummyNode;
            tail = dummyNode;
            size = new AtomicInteger(0);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public int size() {
        this.lock.readLock().lock();
        try {
            return size.get();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        this.lock.readLock().lock();
        try {
            return head.isEmpty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public boolean contains(T value) {
        this.lock.readLock().lock();
        try {
            return search(value).hasElement();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public ILinkedListNode<T> search(T value) {
        this.lock.readLock().lock();
        try {
            return head.search(value);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public ILinkedListNode<T> add(T value) {
        this.lock.writeLock().lock();
        try {
            head = new LinkedListNode<T>(value, head, this);
            if (tail.isEmpty()) {
                tail = head;
            }
            size.incrementAndGet();
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public boolean addAll(Collection<T> values) {
        this.lock.writeLock().lock();
        try {
            for (T value : values) {
                if (add(value).isEmpty()) {
                    return false;
                }
            }
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public ILinkedListNode<T> remove(T value) {
        this.lock.writeLock().lock();
        try {
            ILinkedListNode<T> iLinkedListNode = head.search(value);
            if (!iLinkedListNode.isEmpty()) {
                if (iLinkedListNode == tail) {
                    tail = tail.getPrev();
                }
                if (iLinkedListNode == head) {
                    head = head.getNext();
                }
                iLinkedListNode.detach();
                size.decrementAndGet();
            }
            return iLinkedListNode;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public ILinkedListNode<T> removeTail() {
        this.lock.writeLock().lock();
        try {
            ILinkedListNode<T> oldTail = tail;
            if (oldTail == head) {
                tail = head = dummyNode;
            } else {
                tail = tail.getPrev();
                oldTail.detach();
            }
            if (!oldTail.isEmpty()) {
                size.decrementAndGet();
            }
            return oldTail;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public ILinkedListNode<T> moveToFront(ILinkedListNode<T> node) {
        return node.isEmpty() ? dummyNode : updateAndMoveToFront(node, node.getElement());
    }

    public ILinkedListNode<T> updateAndMoveToFront(ILinkedListNode<T> node, T newValue) {
        this.lock.writeLock().lock();
        try {
            if (node.isEmpty() || (this != (node.getListReference()))) {
                return dummyNode;
            }
            detach(node);
            add(newValue);
            return head;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    private void detach(ILinkedListNode<T> node) {
        if (node != tail) {
            node.detach();
            if (node == head) {
                head = head.getNext();
            }
            size.decrementAndGet();
        } else {
            removeTail();
        }
    }
}
