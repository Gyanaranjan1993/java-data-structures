package com.java.datastructures.linkedlist;

public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public Node(T value) {
            this.value = value;
        }
    }

    public void addFirst(T value) {
        if (isEmpty()) {
            head = tail = new Node(value, null, null);
        } else {
            /*
            Create a new node with next node as head
            Assign previous node of current head to the new node
            Assign newNode to head
             */
            Node newNode = new Node(value, head, null);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T value) {
        if (isEmpty()) {
            head = tail = new Node(value, null, null);
        } else {
            tail.next = new Node(value, null, tail);
            tail = tail.next;
        }
        size++;
    }

    public void add(T value, int index) {

        if(index > size) throw new IllegalArgumentException("index can't be larger than the current size");

        if (isEmpty())
            addFirst(value);
        else {
            // if the pos is n we need to traverse to n-1 position
            int i = 0;
            Node<T> currentNode = head;
            Node<T> positionNode = null;

            while (i < index && currentNode != null) {
                i++;
                positionNode = currentNode;
                currentNode = currentNode.next;
            }

            if (currentNode == null)
                throw new ArrayIndexOutOfBoundsException("Out of memory");
            else {
                Node<T> newNode = new Node<>(value, currentNode, positionNode);
                currentNode.prev = newNode;
                positionNode.next = newNode;
            }
        }
    }
    /*
    Return the head value
     */
    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException("Linked List is empty");
        else return (T) head.value;
    }

    /*
    Return the tail value
     */
    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException("Linked List is empty");
        else return tail.value;
    }

    /*
     Set head to head.next and reduce the size by 1
     Check if the List becomes empty, If yes, then set tail to null as well.
     Else set head.prev to null.
     */
    public T removeFirst() {
        if (isEmpty())
            throw new RuntimeException("Linked List is empty");
        else {
            T value = head.value;
            head = head.next;
            --size;
            if (isEmpty()) {
                tail = null;
            } else {
                head.prev = null;
            }
            return value;
        }
    }

    /*
        Traverse through each node, clear previous and next nodes along with the value.
        At the end set head and tail to null.
     */
    public void clear() {
        var node = head;

        while (node != null) {
            var next = node.next;
            node.prev = node.next = null;
            node.value = null;
            node = next;
        }

        head = tail = null;
        size = 0;
    }

    /*
     If the node's previous value is null, that means it's the head, so we delete the first node
     If the node's next value is null, that means it's the tail, so we delete the last node
     Otherwise, we just reassign the pointers.
     */
    public T remove(Node<T> n){

        if(n.prev == null) removeFirst();
        if(n.next == null) removeLast();

        n.prev.next = n.next;
        n.next.prev = n.prev;

        T value = n.value;

        //cleanup memory, optional in java
        n.value = null;
        n.prev = n.next = null;

        //return the value held by the node
        return value;
    }

    public void removeAtIndex(int index) {
        if(index < 0 || index >= size)
            throw new IllegalArgumentException("List does not contain any value at specified index");

        //traverse to index - 1 position
        int i = 0;
        Node<T> trav = head;
        while(i < index){
            trav = trav.next;
            i++;
        }

        remove(trav);
    }

    private void removeLast() {
        if(isEmpty()) throw new RuntimeException("List is empty");

        tail.prev.next = null;
        tail = tail.prev;
        size--;
    }

    private boolean isEmpty() {
        return size == 0;
    }

}
