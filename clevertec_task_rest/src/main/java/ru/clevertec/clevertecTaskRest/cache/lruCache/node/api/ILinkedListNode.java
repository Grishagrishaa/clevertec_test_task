package ru.clevertec.clevertecTaskRest.cache.lruCache.node.api;

import ru.clevertec.clevertecTaskRest.cache.lruCache.DoublyLinkedList;

public interface ILinkedListNode<V> {
    boolean hasElement();

    boolean isEmpty();

    V getElement() throws NullPointerException;

    void detach();

    DoublyLinkedList<V> getListReference();

    ILinkedListNode<V> setPrev(ILinkedListNode<V> prev);

    ILinkedListNode<V> setNext(ILinkedListNode<V> next);

    ILinkedListNode<V> getPrev();

    ILinkedListNode<V> getNext();

    ILinkedListNode<V> search(V value);
}
