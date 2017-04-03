package edu.neu.graph.basic;

import edu.neu.graph.basic.models.Color;
import edu.neu.graph.basic.models.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HappyMole on 4/1/17.
 */
public class DepthFirstSearch {
    int time = 0;

    public void solve(List<Node> nodes) {
        for (Node node : nodes) {
            if (node.color == Color.WHITE) {
                visit(node);
            }
        }
    }

    private void visit(Node node) {
        time++;     // white node has just been discovered
        node.discoverTime = time;
        node.color = Color.GRAY;

        for (Node neighbor : node.neighbors) {    // explore edge (node, neighbor)
            if (neighbor.color == Color.WHITE) {
                neighbor.parent = node;
                visit(neighbor);
            }
        }

        node.color = Color.BLACK;    // black node; it is finished
        time++;
        node.finishTime = time;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        node1.neighbors.add(node2);
        node1.neighbors.add(node3);
        node2.neighbors.add(node4);
        node4.neighbors.add(node5);
        node3.neighbors.add(node6);
        node6.neighbors.add(node1);

        List<Node> graph = new ArrayList<>();
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);

        DepthFirstSearch depthFirstSearch = new DepthFirstSearch();
        depthFirstSearch.solve(graph);

        for (Node node : graph) {
            System.out.println("node" + node.label + ": " + node.discoverTime + "/" + node.finishTime);
        }
    }
}
