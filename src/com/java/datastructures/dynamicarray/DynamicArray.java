package com.java.datastructures.dynamicarray;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DynamicArray<T> implements Iterable<T> {

    private int len;
    private int capacity;
    private T arr[];

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        this.capacity = capacity;
        this.arr = (T[]) new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}
