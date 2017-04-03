package edu.neu.graph.MST.kruskal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    int label;
    Node parent;
    int rank;

    public Node(int label) {
        this.label = label;
        this.rank = 0;
    }
}

public class UnionFind {
    Map<Integer, Node> nodeMap = new HashMap<>();

    public UnionFind(List<Integer> labels) {
        for (int label : labels) {
            Node node = new Node(label);
            node.parent = node;
            nodeMap.put(label, node);
        }
    }

    public Node getNode(int label) {
        return nodeMap.get(label);
    }

    public Node find(Node node) {
        if (node != node.parent) {
            node.parent = find(node.parent);
        }

        return node.parent;
    }


    public void union(Node n1, Node n2) {
        Node np1 = find(n1);
        Node np2 = find(n2);

        if (np1.rank > np2.rank) {
            np2.parent = np1;
        } else {
            np1.parent = np2;
            if (np1.rank == np2.rank) {
                np2.rank++;
            }
        }
    }
}
