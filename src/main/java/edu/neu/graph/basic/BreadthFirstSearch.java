package edu.neu.graph.basic;

import edu.neu.graph.basic.models.Color;
import edu.neu.graph.basic.models.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by HappyMole on 4/1/17.
 */
public class BreadthFirstSearch {
    public void solve(Node node) {
        node.color = Color.GRAY;
        node.distance = 0;
        node.parent = null;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            node = queue.poll();
            System.out.println("node " + node.label + ":" + node.distance);
            for (Node neighbor : node.neighbors) {
                if (neighbor.color == Color.WHITE) {
                    neighbor.color = Color.GRAY;
                    neighbor.distance = node.distance + 1;
                    neighbor.parent = node;
                    queue.offer(neighbor);
                }
            }
            node.color = Color.BLACK;
        }
    }

    public void printPath(Node src, Node dest, StringBuilder pathBuilder) {
        if (src == dest) {
            pathBuilder.append(src.label);
        } else if (dest.parent == null) {
            System.out.println("no path from " + src.label + " to " + dest.label + " exists");
        } else {
            printPath(src, dest.parent, pathBuilder);
            pathBuilder.insert(0, dest.label + "->");
        }
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

        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
        breadthFirstSearch.solve(node1);

        StringBuilder pathBuilder = new StringBuilder();
        breadthFirstSearch.printPath(node1, node5, pathBuilder);
        System.out.println(pathBuilder.toString());
    }
}
