package com.java.datastructures.tree;

import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> {

    private int nodeCount = 0;
    private Node root = null;

    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        binarySearchTree.add(5);
        binarySearchTree.add(2);
        binarySearchTree.add(10);
        binarySearchTree.add(15);
        binarySearchTree.add(1);
        binarySearchTree.add(8);
        binarySearchTree.add(4);

        Iterator<Integer> iterator = binarySearchTree.treeIterator(TreeTraversalOrder.POST_ORDER);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

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

    public boolean remove(T elem) {

        if (contains(elem)) {
            remove(root, elem);
            nodeCount--;
            return true;
        }

        return false;

    }

    private Node remove(Node root, T elem) {
        if (root == null) return null;

        int cmp = elem.compareTo(root.value);

        if (cmp < 0) {
            root.left = remove(root.left, elem);
        } else if (cmp > 0) {
            root.right = remove(root.right, elem);
        } else {
            /*
            When we find the element we are looking for then, check if the right subtree is null.
            If right subtree is null, then the successor of the root will be the left subtree of the required element.
             */
            if (root.right == null) {
                return root.left;

                //If the left subtree is null, then the right subtree would be the successor after removal.

            } else if (root.left == null) {
                return root.right;

                //When there are both subtrees of the required element, then we go the right subtree, find out
                // the leftmost element. That leftmost element will be the new successor.
            } else {
                Node tmp = findMin(root.right);

                root.value = tmp.value;

                //Remove the leftmost element after assigning the successor.
                root.right = remove(root.right, tmp.value);
            }
        }
        return root;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public Iterator<T> treeIterator(TreeTraversalOrder order) {

        switch (order) {
            case IN_ORDER:
                return inOrderTraversal();
            case PRE_ORDER:
                return preOrderTraversal();
            case LEVEL_ORDER:
                return levelOrderTraversal();

            case POST_ORDER:
                return postOrderTraversal();

            default:
                return null;
        }

    }

    /*
     Traverse all left subtrees, then all right subtrees and then the root
     */
    private Iterator<T> postOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Stack<Node> stack1 = new java.util.Stack<>();
        final java.util.Stack<Node> stack2 = new java.util.Stack<>();
        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            if (node != null) {
                stack2.push(node);
                if (node.left != null) stack1.push(node.left);
                if (node.right != null) stack1.push(node.right);
            }
        }
        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack2.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return stack2.pop().value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /*
        Traverse level wise. Elements at one level are traversed first before we move the next level.
     */
    private Iterator<T> levelOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(root);

        return new java.util.Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                Node node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /*
        Traverse root, left subtrees and then the right subtrees.
     */
    private Iterator<T> preOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();

        stack.push(root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                Node node = stack.pop();

                /*
                1st push the right node to the stack, then the left. So that left node always comes up first before
                right node during traversal.
                 */
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);

                return node.value;
            }
        };

    }

    /*
      Traverse the tree in order. In this case traverse all the left subtrees, then right subtrees all the way
      to the root.
     */
    private Iterator<T> inOrderTraversal() {
        final int expectedNodeCount = nodeCount;
        final Stack<Node> stack = new Stack<>();

        stack.push(root);

        //traverse all the left nodes and push them to the stack
        return new Iterator<>() {
            Node trav = root;

            @Override
            public boolean hasNext() {
                //If in between iterating, the node count changes, then throw exception.
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if (expectedNodeCount != nodeCount) throw new java.util.ConcurrentModificationException();

                while (trav != null && trav.left != null) {
                    stack.push(trav.left);
                    trav = trav.left;
                }

                //After we reach end of the left subtree, we get leaf node
                Node node = stack.pop();

                if (node.right != null) {
                    stack.push(node.right);
                    trav = node.right;
                }

                return node.value;
            }
        };
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


}
