package com.java.datastructures.tree;

public class BinarySearchTree<T extends Comparable<T>> {

    private int nodeCount = 0;
    private Node root = null;

    public int size() {
        return nodeCount;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(T elem) {
        //if the tree already contains the value, then return false
        if (contains(elem))
            return false;
        else {
            root = add(root, elem);
            nodeCount++;
            return true;
        }
    }

    private Node add(Node root, T elem) {

        if (root == null) {
            root = new Node(elem, null, null);
        } else {
            if (elem.compareTo(root.value) < 0) {
                root.left = add(root.left, elem);
            } else {
                root.right = add(root.right, elem);
            }
        }

        return root;
    }

    public boolean contains(T elem) {
        return contains(root, elem);

    }

    private boolean contains(Node root, T elem) {

        if (root == null) return false;

        int cmp = elem.compareTo(root.value);

        //if elem is less than the root, then we need to traverse to the left sub-tree,
        //If the elem is greater than the root, then we need to traverse to the right subtree.
        if (cmp < 0) {
            return contains(root.left, elem);
        } else if (cmp > 0) {
            return contains(root.right, elem);
        }

        //else as cmp is zero, then we found the element.
        return true;
    }

    public boolean remove(T elem){

        if(contains(elem)){
            remove(root, elem);
            nodeCount--;
            return true;
        }

        return false;

    }

    private Node remove(Node root, T elem) {
        if(root == null) return null;

        int cmp = elem.compareTo(root.value);

        if(cmp < 0){
            root.left = remove(root.left, elem);
        } else if(cmp > 0){
            root.right = remove(root.right, elem);
        } else{
            /*
            When we find the element we are looking for then, check if the right subtree is null.
            If right subtree is null, then the successor of the root will be the left subtree of the required element.
             */
            if(root.right == null){
                return root.left;

              //If the left subtree is null, then the right subtree would be the successor after removal.

            } else if (root.left == null) {
                return root.right;

              //When there are both subtrees of the required element, then we go the right subtree, find out
              // the leftmost element. That leftmost element will be the new successor.
            } else{
                Node tmp = findMin(root.right);

                root.value = tmp.value;

                //Remove the leftmost element after assigning the successor.
                root.right = remove(root.right, tmp.value);
            }
        }
        return root;
    }

    private Node findMin(Node node) {
        while (node.left != null){
            node = node.left;
        }

        return node;
    }

    private class Node {
        T value;
        Node left;
        Node right;

        public Node(T value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(5);
        binarySearchTree.add(2);
        binarySearchTree.add(10);
        binarySearchTree.add(15);
    }


}
