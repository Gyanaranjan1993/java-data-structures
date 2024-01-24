package com.java.datastructures.stack;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Stack<T> implements Iterable<T> {

    private final LinkedList<T> linkedList = new LinkedList<>();

    public Stack() {
    }

    public Stack(T value){
        push(value);
    }

    public void push(T value){
        linkedList.addLast(value);
    }

    public T pop(T value){
        return linkedList.removeLast();
    }

    public T peek(){
        return linkedList.peek();
    }

    @Override
    public Iterator<T> iterator() {
        return linkedList.iterator();
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
