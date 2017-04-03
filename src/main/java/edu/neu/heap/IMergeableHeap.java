package edu.neu.heap;

/**
 * Created by HappyMole on 4/3/17.
 */
public interface IMergeableHeap<T> {
    int size();

    T searchByLabel(int label);

    T searchByVal(int val);

    T minimum();

    T extractMin();

    void insert(T node);

    void delete(T node);

    void decreaseKey(T node, int destVal);

    T union(IMergeableHeap heap);

    void print();
}
