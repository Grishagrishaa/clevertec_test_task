package ru.clevertec.clevertecTaskRest.cache.lruCache.node;


import ru.clevertec.clevertecTaskRest.cache.lruCache.DoublyLinkedList;
import ru.clevertec.clevertecTaskRest.cache.lruCache.node.api.ILinkedListNode;

public class LinkedListNode<T> implements ILinkedListNode<T> {
    private T value;
    private DoublyLinkedList<T> list;
    private ILinkedListNode next;
    private ILinkedListNode prev;

    public LinkedListNode(T value, ILinkedListNode<T> next, DoublyLinkedList<T> list) {
        this.value = value;
        this.next = next;
        this.setPrev(next.getPrev());
        this.prev.setNext(this);
        this.next.setPrev(this);
        this.list = list;
    }

    @Override
    public boolean hasElement() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public T getElement() {
        return value;
    }

    public void detach() {
        this.prev.setNext(this.getNext());
        this.next.setPrev(this.getPrev());
    }

    @Override
    public DoublyLinkedList<T> getListReference() {
        return this.list;
    }

    @Override
    public ILinkedListNode<T> setPrev(ILinkedListNode<T> prev) {
        this.prev = prev;
        return this;
    }

    @Override
    public ILinkedListNode<T> setNext(ILinkedListNode<T> next) {
        this.next = next;
        return this;
    }

    @Override
    public ILinkedListNode<T> getPrev() {
        return this.prev;
    }

    @Override
    public ILinkedListNode<T> getNext() {
        return this.next;
    }

    @Override
    public ILinkedListNode<T> search(T value) {
        return this.getElement() == value ? this : this.getNext().search(value);
    }
}
