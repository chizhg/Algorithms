package edu.neu.graph.SSSP;

import edu.neu.graph.models.Edge;
import edu.neu.graph.models.Graph;
import edu.neu.graph.models.Vertex;

import java.lang.*;
import java.util.Arrays;
import java.util.List;

/*
    The Bellman-Ford algorithm solves the single-source shortest-paths problem
    in the general case in which edge weights may be negative.

    Core idea is to relax each edge for |V|-1 times.

    O(V*E) running time
*/
public class BellmanFord {
    public boolean solve(Graph graph, Vertex src) {
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE, and to itself as 0
        for (int i = 0; i < graph.V.size(); i++) {
            graph.V.get(i).distance = Integer.MAX_VALUE;
        }
        src.distance = 0;

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < graph.V.size(); i++) {
            for (int j = 0; j < graph.E.size(); j++) {
                Edge edge = graph.E.get(j);
                Vertex u = edge.src;
                Vertex v = edge.dest;
                int weight = edge.weight;
                // RELAXATION
                if (u.distance != Integer.MAX_VALUE && u.distance + weight < v.distance) {
                    v.distance = u.distance + weight;
                }
            }
        }

        // Step 3: check for negative-distance cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative distance cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j = 0; j < graph.E.size(); j++) {
            Edge edge = graph.E.get(j);
            Vertex u = edge.src;
            Vertex v = edge.dest;
            int weight = edge.weight;
            if (u.distance != Integer.MAX_VALUE && u.distance + weight < v.distance) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Vertex vertex0 = new Vertex(0);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex[] vertices = new Vertex[]{vertex0, vertex1, vertex2, vertex3, vertex4};
        List<Vertex> vertexList = Arrays.asList(vertices);


        Edge edge1 = new Edge(vertex0, vertex1, -1);
        Edge edge2 = new Edge(vertex0, vertex2, 4);
        Edge edge3 = new Edge(vertex1, vertex2, 3);
        Edge edge4 = new Edge(vertex1, vertex3, 2);
        Edge edge5 = new Edge(vertex1, vertex4, 2);
        Edge edge6 = new Edge(vertex3, vertex2, 5);
        Edge edge7 = new Edge(vertex3, vertex1, 1);
        Edge edge8 = new Edge(vertex4, vertex3, -3);
        Edge[] edges = new Edge[]{edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8};
        List<Edge> edgeList = Arrays.asList(edges);

        Graph graph = new Graph(vertexList, edgeList);
        BellmanFord bellmanFord = new BellmanFord();
        bellmanFord.solve(graph, vertexList.get(0));

        for (Vertex vertex : graph.V) {
            System.out.println("vertex " + vertex.label + " : " + vertex.distance);
        }
    }
}