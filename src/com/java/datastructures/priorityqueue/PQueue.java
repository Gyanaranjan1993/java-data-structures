package com.java.datastructures.priorityqueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Priority Queue implementation using a binary min heap.
 *
 * @param <T>
 */
public class PQueue<T extends Comparable<T>> {
    private List<T> heap = null;

    public PQueue() {
        this(1);
    }

    public PQueue(int size) {
        heap = new ArrayList<>(size);
    }

    public PQueue(T[] elems) {
        int size = elems.length;

        heap = new ArrayList<>(size);
        Collections.addAll(heap, elems);

        // Heapify process, O(n)
        //TODO: I don't understand this
        for (int i = Math.max(0, (size / 2) - 1); i >= 0; i--) sink(i);
    }

    public int size() {
        return heap.size();
    }

    public T peek() {
        if (size() == 0) return null;
        return heap.get(0);
    }

    public boolean contains(T elem) {
        return heap.contains(elem);
    }

    public T remove(T elem) {
        if (!heap.contains(elem)) {
            throw new RuntimeException("Queue does not have element");
        }

        int index = heap.indexOf(elem);
        removeAt(index);
        return elem;
    }

    public T removeAt(int index) {
        if (heap.isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        int indexOfLastElem = heap.size() - 1;
        T removedElement = heap.get(index);

        //swap the required index we want to remove with the last element
        swap(index, indexOfLastElem);

        //remove the last element
        heap.remove(indexOfLastElem);

        //If the needed index is the last element fox then we just return last element value
        if (index == indexOfLastElem) {
            return removedElement;
        }

        //Let's do a sink operation
        sink(index);

        //If sink does not work, then try swim
        if (heap.get(index).equals(removedElement)) {
            swim(index);
        }

        return removedElement;

    }

    /*
    Add an element to the heap's last index. Then we perform bottom up shift to maintain the heap invariant.
     */
    public void add(T elem) {
        //Add the element to the last index
        heap.add(elem);

        //perform swim operation
        swim(heap.size() - 1);
    }

    /*
    Return the top element from the queue
     */
    public T poll() {
        return removeAt(0);
    }

    /*
     This is a top-down approach where we go from the root node to each child.
     If the value of child is less than the parent we swap them
     */
    private void sink(int n) {
        int heapSize = size();

        while (true) {
            //get the left child
            int leftChildIndex = 2 * n + 1;
            //right child
            int rightChildIndex = 2 * n + 2;

            int smallest = leftChildIndex;

            if (rightChildIndex < heapSize && less(rightChildIndex, leftChildIndex)) {
                smallest = rightChildIndex;
            }

            // Stop if we're outside the bounds of the tree
            // or stop early if we cannot sink n anymore
            if (leftChildIndex >= heapSize || less(n, smallest)) break;

            swap(smallest, n);

            //Move down following the smallest index
            n = smallest;

        }
    }


    /*
    Bottom up approach where we check the last most element in the
    binary heap against its parent. If it's less than parent, then we
    swap the two elements. We do this in a loop until we satisfy the min heap invariant.
     */
    private void swim(int index) {

        //get the parent
        int parentIndex = (index - 1) / 2;

        //Keep bottom up process until we reach the root element
        //or the element is less than its parent.
        while (index > 0 && less(index, parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;

            // Get the new parent after we update the index
            parentIndex = (index - 1) / 2;
        }
    }

    private void swap(int index, int parentIndex) {
        T temp = heap.get(index);
        heap.set(index, heap.get(parentIndex));
        heap.set(parentIndex, temp);
    }

    private boolean less(int index, int parentIndex) {
        return heap.get(index).compareTo(heap.get(parentIndex)) < 0;
    }
}
