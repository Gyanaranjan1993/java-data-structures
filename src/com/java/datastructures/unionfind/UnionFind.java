package com.java.datastructures.unionfind;

public class UnionFind {

    //The number of elements in the union find
    int size;

    //Used to track size of each of the set
    int[] sz;

    //parent[i] points to parent of i, if parent[i] = i , then we can say i is a root node
    int[] parent;

    int numComponents;

    public UnionFind(int size){
        this.size = numComponents = size;
        sz = new int[size];
        parent = new int[size];

        for (int i = 0; i <size ; i++){
            //Initially each elements parent is itself
            parent[i] = i;

            //Size of each set is 1
            sz[i] = i;
        }
    }

    //Find which set/component P belongs to
    public int find(int p){
        int root = p;

        //Find the root element of P, we do this by using while loop. The while loop ends
        //when the parent of the element is the element itself.
        while (root != parent[root])
            root = parent[root];

        //After finding the root, do the path compression process. That means we assign parents
        //of each element traversed while going from P -> root element, to the root element.
        //Compress the path leading back to the root element
        //This is called path compression, which gives us amortized time complexity.

        while (p != root) {
            int next = parent[p];
            parent[p] = root;
            p = next;
        }
        return root;
    }
}
