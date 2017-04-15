package edu.neu.heap;

/**
 * Created by HappyMole on 4/14/17.
 */
public abstract class BaseNode {
    public int label;
    public int key;
    // number of children in the child list
    public int degree;

    public abstract void print();

    public final String toString() {
        return Integer.toString(key);
    }
}