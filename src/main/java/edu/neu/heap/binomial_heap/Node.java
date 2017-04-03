package edu.neu.heap.binomial_heap;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int label;
    public int key;
    int degree;

    Node parent;
    Node child;
    Node sibling;

    public Node(int key) {
        this.key = key;
        degree = 0;
        parent = null;
        child = null;
        sibling = null;
    }

    public Node(int label, int key) {
        this(key);
        this.label = label;
    }

    public void print() {
        Node dummyNode = new Node(-1);
        dummyNode.child = this;
        dummyNode.print("", true, true);
    }

    private void print(String prefix, boolean isTail, boolean isDummy) {
        if (!isDummy) {
            System.out.println(prefix + "│\n" + prefix + (isTail ? "\\-- " : "├── ") + key);
        }
        List<Node> children = collectChildren();
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false, false);
        }

        if (children.size() > 0) {
            children.get(children.size() - 1).print(prefix + (isTail ? "    " : "│   "), true, false);
        }
    }

    private List<Node> collectChildren() {
        List<Node> nodeList = new ArrayList<>();
        Node curt = this.child;
        while (curt != null) {
            nodeList.add(0, curt);
            curt = curt.sibling;
        }

        return nodeList;
    }
}
