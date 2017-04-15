package edu.neu.heap.fibonacci_heap;

import edu.neu.heap.BaseNode;

import java.util.*;

class NodeListIterator implements Iterator<Node> {
    private Queue<Node> items = new LinkedList<>();

    public NodeListIterator(Node start) {
        if (start == null) {
            return;
        }

        Node current = start;
        do {
            items.add(current);
            current = current.right;
        } while (start != current);
    }

    public boolean hasNext() {
        return items.peek() != null;
    }

    public Node next() {
        return items.poll();
    }
}

public class Node extends BaseNode {
    // whether this node has lost a child since the last time
    // x was made the child of another node
    boolean mark;
    // number of children in the child list
    int degree;
    Node parent;
    Node child;
    Node left;
    Node right;

    public Node(int key) {
        this.key = key;
        mark = false;
        degree = 0;
        parent = null;
        child = null;
        left = this;
        right = this;
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
        Node child = this.child;
        if (child != null) {
            nodeList.add(child);
            Node curt = child.right;
            while (curt != child) {
                nodeList.add(curt);
                curt = curt.right;
            }
        }

        return nodeList;
    }
}
