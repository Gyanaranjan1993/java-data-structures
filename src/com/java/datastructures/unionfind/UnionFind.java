package com.java.datastructures.unionfind;

/**
 * A Simple Disjoint set (UnionFind) Implementation using a set
 */
public class UnionFind {

    //The number of elements in the union find
    int size;

    //Used to track size of each of the set
    int[] sz;

    //parent[i] points to parent of i, if parent[i] = i , then we can say i is a root node
    int[] parent;

    //Represents the total number of sets
    int numComponents;

    public UnionFind(int size) {
        this.size = numComponents = size;
        sz = new int[size];
        parent = new int[size];

        for (int i = 0; i < size; i++) {
            //Initially each elements parent is itself
            parent[i] = i;

            //Size of each set is 1
            sz[i] = i;
        }
    }

    //Find which set/component P belongs to
    public int find(int p) {
        int root = p;

        /*
            Find the root element of P, we do this by using while loop. The while loop ends
            when the parent of the element is the element itself.
         */

        //
        while (root != parent[root])
            root = parent[root];

        /*
           After finding the root, do the path compression process. That means we assign parents
           of each element traversed while going from P -> root element, to the root element.
           Path compression gives us which gives us amortized time complexity.
         */

        while (p != root) {
            int next = parent[p];
            parent[p] = root;
            p = next;
        }
        return root;
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public int componentSize(int p) {
        return sz[find(p)];
    }

    public void unify(int p, int q) {
        int root1 = find(p);
        int root2 = find(q);

        // If the elements are in the same group already, then we do nothing.
        if (root1 == root2) return;

        /*
            If not we merge the smaller set to the larger set
            We do that by incrementing the size of larger set by size of the smaller set
            And assign the parent of root of the smaller set to that of the larger set
         */
        if (sz[root1] < sz[root2]) {
            sz[root2] += sz[root1];
            parent[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            parent[root2] = root1;
        }

        numComponents --;

    }

}
