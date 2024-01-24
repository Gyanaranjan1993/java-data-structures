package com.java.datastructures.queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Queue implementation using a Java linked list
 * @param <T>
 */
public class Queue<T> implements Iterable<T> {

    private final LinkedList<T> list = new LinkedList<>();

    public Queue() {
    }

    public Queue(T element){
        enqueue(element);
    }

    public void enqueue(T element) {
        list.addLast(element);
    }

    public T dequeue(T element){
        if(isEmpty())
            throw new RuntimeException("Queue is empty");
        return list.removeFirst();
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    @Override
    public Iterator<T> iterator() {
        return list.iterator();
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
