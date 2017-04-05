package edu.neu.graph.SSSP;

import edu.neu.graph.models.Graph;
import edu.neu.graph.models.Vertex;
import edu.neu.heap.fibonacci_heap.FibonacciHeap;
import edu.neu.heap.fibonacci_heap.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Dijkstra's algorithm solves the single-source shortest-paths problem
    on a weighted, directed graph G=(V,E) for the case in which all edge
    weights are non-negative.

    The algorithm is based on a greedy strategy.
    Core idea is always selecting the "closest" vertex to the source,
    and relaxing all neighbor vertices.

    O(V*lgV+E) running time
 */
public class Dijkstra {
    public static void main(String[] args) {
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);

        vertex1.weightMap.put(vertex2, 10);
        vertex1.weightMap.put(vertex4, 5);

        vertex2.weightMap.put(vertex3, 1);
        vertex2.weightMap.put(vertex4, 2);

        vertex3.weightMap.put(vertex5, 4);

        vertex4.weightMap.put(vertex2, 3);
        vertex4.weightMap.put(vertex3, 9);
        vertex4.weightMap.put(vertex5, 2);

        vertex5.weightMap.put(vertex3, 6);

        Vertex[] vertices = new Vertex[]{vertex1, vertex2, vertex3, vertex4, vertex5};
        List<Vertex> vertexList = Arrays.asList(vertices);
        Graph graph = new Graph(vertexList);

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.solve(graph, vertex1);

        for (Vertex vertex : graph.V) {
            System.out.println("vertex " + vertex.label + " : " + vertex.distance);
        }
    }

    public void solve(Graph graph, Vertex src) {
        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE, and to itself as 0
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        for (int i = 0; i < graph.V.size(); i++) {
            Vertex vertex = graph.V.get(i);
            vertex.distance = Integer.MAX_VALUE;
            vertexMap.put(vertex.label, vertex);
        }
        src.distance = 0;

        // Step 2: initialize fibonacci heap, and insert all vertex
        // into the heap
        FibonacciHeap heap = FibonacciHeap.makeHeap();
        for (int i = 0; i < graph.V.size(); i++) {
            Vertex vertex = graph.V.get(i);
            heap.insert(new Node(vertex.label, vertex.distance));
        }

        // Step 3: extract the node with the minimum distance, and
        // update the vertex, which is the final result.
        // Relax all vertices' distances reachable from the current minimum
        // vertex.
        while (heap.size() != 0) {
            Node node = heap.extractMin();
            Vertex vertex = vertexMap.get(node.label);
            vertex.distance = node.key;
            for (Vertex neighbor : vertex.weightMap.keySet()) {
                int edgeWeight = vertex.weightMap.get(neighbor);
                if (vertex.distance != Integer.MAX_VALUE && vertex.distance + edgeWeight < neighbor.distance) {
                    Node neighborNode = heap.searchByLabel(neighbor.label);
                    heap.decreaseKey(neighborNode, vertex.distance + edgeWeight);
                }
            }
        }
    }
}
