package edu.neu.graph.SSSP;

import edu.neu.graph.basic.TopologicalSort;
import edu.neu.graph.models.Graph;
import edu.neu.graph.models.Vertex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HappyMole on 4/4/17.
 */
public class DAG {
    public static void main(String[] args) {
        /*
           10     1
        /------>b--->c
        |       ^   ^|
        |       |  / |
        a      2| /3 |4
        |       |/   v
        \------>d--->e
           5      5
        */
        Vertex vertex1 = new Vertex('a');
        Vertex vertex2 = new Vertex('b');
        Vertex vertex3 = new Vertex('c');
        Vertex vertex4 = new Vertex('d');
        Vertex vertex5 = new Vertex('e');

        vertex1.weightMap.put(vertex2, 10);
        vertex1.weightMap.put(vertex4, 5);
        vertex1.neighbors.addAll(vertex1.weightMap.keySet());

        vertex2.weightMap.put(vertex3, 1);
        vertex2.neighbors.addAll(vertex2.weightMap.keySet());

        vertex3.weightMap.put(vertex5, 4);
        vertex3.neighbors.addAll(vertex3.weightMap.keySet());

        vertex4.weightMap.put(vertex2, 3);
        vertex4.weightMap.put(vertex3, 9);
        vertex4.weightMap.put(vertex5, 2);
        vertex4.neighbors.addAll(vertex4.weightMap.keySet());

        Vertex[] vertices = new Vertex[]{vertex1, vertex2, vertex3, vertex4, vertex5};
        List<Vertex> vertexList = Arrays.asList(vertices);
        Graph graph = new Graph(vertexList);

        DAG dag = new DAG();
        dag.solve(graph, vertex1);

        for (Vertex vertex : graph.V) {
            System.out.println("vertex " + (char)vertex.label + " : " + vertex.distance);
        }
    }

    public void solve(Graph graph, Vertex src) {
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE, and to itself as 0
        for (int i = 0; i < graph.V.size(); i++) {
            graph.V.get(i).distance = Integer.MAX_VALUE;
        }
        src.distance = 0;

        // Step 2: Topologically sort the vertices of G
        TopologicalSort topologicalSort = new TopologicalSort();
        List<Vertex> sortResult = topologicalSort.dfsSolve(graph.V);

        // Step 3: Take vertex in topologically sorted order,
        // and relax neighbor vertices with the current distance and edge weights
        for (int i = 0; i < sortResult.size(); i++) {
            Vertex vertex = sortResult.get(i);
            for (Vertex neighbor : vertex.weightMap.keySet()) {
                int edgeWeight = vertex.weightMap.get(neighbor);
                if (vertex.distance != Integer.MAX_VALUE && vertex.distance + edgeWeight < neighbor.distance) {
                    neighbor.distance = vertex.distance + edgeWeight;
                }
            }
        }
    }
}
