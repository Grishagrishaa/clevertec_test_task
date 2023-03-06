package ru.clevertec.clevertecTaskRest.cache.lruCache.node;

import ru.clevertec.clevertecTaskRest.cache.lruCache.DoublyLinkedList;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.api.ILinkedListNode;


public class DummyLinkedListNode<T> implements ILinkedListNode<T> {
    private DoublyLinkedList<T> list;

    public DummyLinkedListNode(DoublyLinkedList<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasElement() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T getElement() throws NullPointerException {
        throw new NullPointerException();
    }

    @Override
    public void detach() {
        return;
    }

    @Override
    public DoublyLinkedList<T> getListReference() {
        return list;
    }

    @Override
    public ILinkedListNode<T> setPrev(ILinkedListNode<T> next) {
        return next;
    }

    @Override
    public ILinkedListNode<T> setNext(ILinkedListNode<T> prev) {
        return prev;
    }

    @Override
    public ILinkedListNode<T> getPrev() {
        return this;
    }

    @Override
    public ILinkedListNode<T> getNext() {
        return this;
    }

    @Override
    public ILinkedListNode<T> search(T value) {
        return this;
    }
}
