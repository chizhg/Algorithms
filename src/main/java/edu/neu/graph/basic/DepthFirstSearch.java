package edu.neu.graph.basic;

import edu.neu.graph.models.Color;
import edu.neu.graph.models.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HappyMole on 4/1/17.
 */
public class DepthFirstSearch {
    int time = 0;

    public void solve(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            if (vertex.color == Color.WHITE) {
                visit(vertex);
            }
        }
    }

    private void visit(Vertex vertex) {
        time++;     // white vertex has just been discovered
        vertex.discoverTime = time;
        vertex.color = Color.GRAY;

        for (Vertex neighbor : vertex.neighbors) {    // explore edge (vertex, neighbor)
            if (neighbor.color == Color.WHITE) {
                neighbor.parent = vertex;
                visit(neighbor);
            }
        }

        vertex.color = Color.BLACK;    // black vertex; it is finished
        time++;
        vertex.finishTime = time;
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

        List<Vertex> graph = new ArrayList<>();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.add(vertex3);
        graph.add(vertex4);
        graph.add(vertex5);
        graph.add(vertex6);

        DepthFirstSearch depthFirstSearch = new DepthFirstSearch();
        depthFirstSearch.solve(graph);

        for (Vertex vertex : graph) {
            System.out.println("vertex" + vertex.label + ": " + vertex.discoverTime + "/" + vertex.finishTime);
        }
    }
}
