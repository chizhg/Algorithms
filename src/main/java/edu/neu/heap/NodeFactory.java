package edu.neu.heap;

/**
 * Created by HappyMole on 4/14/17.
 */
public class NodeFactory {
    private int nodeType;

    public NodeFactory(int nodeType) {
        this.nodeType = nodeType;
    }

    public BaseNode create(int key) {
        if (nodeType == 1) {
            return new edu.neu.heap.binomial_heap.Node(key);
        } else {
            return new edu.neu.heap.fibonacci_heap.Node(key);
        }
    }
}
