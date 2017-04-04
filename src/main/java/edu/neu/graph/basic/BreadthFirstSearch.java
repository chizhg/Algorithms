package edu.neu.graph.basic;

import edu.neu.graph.models.Color;
import edu.neu.graph.models.Vertex;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by HappyMole on 4/1/17.
 */
public class BreadthFirstSearch {
    public void solve(Vertex vertex) {
        vertex.color = Color.GRAY;
        vertex.distance = 0;
        vertex.parent = null;

        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(vertex);

        while (!queue.isEmpty()) {
            vertex = queue.poll();
            System.out.println("vertex " + vertex.label + ":" + vertex.distance);
            for (Vertex neighbor : vertex.neighbors) {
                if (neighbor.color == Color.WHITE) {
                    neighbor.color = Color.GRAY;
                    neighbor.distance = vertex.distance + 1;
                    neighbor.parent = vertex;
                    queue.offer(neighbor);
                }
            }
            vertex.color = Color.BLACK;
        }
    }

    public void printPath(Vertex src, Vertex dest, StringBuilder pathBuilder) {
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
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);

        vertex1.neighbors.add(vertex2);
        vertex1.neighbors.add(vertex3);
        vertex2.neighbors.add(vertex4);
        vertex4.neighbors.add(vertex5);
        vertex3.neighbors.add(vertex6);
        vertex6.neighbors.add(vertex1);

        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
        breadthFirstSearch.solve(vertex1);

        StringBuilder pathBuilder = new StringBuilder();
        breadthFirstSearch.printPath(vertex1, vertex5, pathBuilder);
        System.out.println(pathBuilder.toString());
    }
}
