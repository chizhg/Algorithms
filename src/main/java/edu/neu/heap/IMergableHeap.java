package edu.neu.heap;

/**
 * Created by HappyMole on 4/14/17.
 */
public interface IMergableHeap<T extends BaseNode> {
    int size();

    T searchByLabel(int label);

    T searchByVal(int val);

    T minimum();

    void insert(T node);

    void decreaseKey(T node, int destVal);

    void delete(T node);

    T extractMin();

    void print();
}
