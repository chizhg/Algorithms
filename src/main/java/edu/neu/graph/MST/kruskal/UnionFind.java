package edu.neu.graph.MST.kruskal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    int label;
    Node parent;
    // an upper bound on the height of the node
    // (the number of edges in the longest simple path between it and a descendant leaf)
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

    public Node find(int label) {
        Node node = nodeMap.get(label);
        if (node != node.parent) {
            node.parent = find(node.parent.label);
        }

        return node.parent;
    }


    public void union(int label1, int label2) {
        Node np1 = find(label1);
        Node np2 = find(label2);

        /*
        - If the roots have unequal ranks, we make the root with higher rank the parent of the root with lower rank,
          but the ranks themselves remain unchanged.
        - If the roots have equal ranks, we arbitrarily choose one of the roots as the parent and increment its rank.
         */
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
