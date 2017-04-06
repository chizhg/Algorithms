package edu.neu.graph.MST.prim;

import edu.neu.graph.models.Graph;
import edu.neu.graph.models.Vertex;
import edu.neu.heap.fibonacci_heap.FibonacciHeap;
import edu.neu.heap.fibonacci_heap.Node;

import java.util.*;

/*
    Greedy


 */
public class Prim {
    public static void main(String[] args) {
        /*
        original graph

          2   3
        1 - 2 - 3
        |  / \  |
       6| /8  \5|7
        |/     \|
        4 ----- 5
            9


        minimum spanning tree

          2   3
        1 - 2 - 3
        |    \
       6|     \5
        |      \
        4       5
         */
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);

        vertex2.neighbors.add(vertex1);
        vertex2.neighbors.add(vertex3);
        vertex2.neighbors.add(vertex4);
        vertex2.neighbors.add(vertex5);
        vertex2.weightMap.put(vertex1, 2);
        vertex2.weightMap.put(vertex3, 3);
        vertex2.weightMap.put(vertex4, 8);
        vertex2.weightMap.put(vertex5, 5);

        vertex1.neighbors.add(vertex4);
        vertex1.neighbors.add(vertex2);
        vertex1.weightMap.put(vertex4, 6);
        vertex1.weightMap.put(vertex2, 2);

        vertex3.neighbors.add(vertex2);
        vertex3.neighbors.add(vertex5);
        vertex3.weightMap.put(vertex2, 3);
        vertex3.weightMap.put(vertex5, 7);

        vertex4.neighbors.add(vertex1);
        vertex4.neighbors.add(vertex2);
        vertex4.neighbors.add(vertex5);
        vertex4.weightMap.put(vertex1, 6);
        vertex4.weightMap.put(vertex2, 8);
        vertex4.weightMap.put(vertex5, 9);

        vertex5.neighbors.add(vertex3);
        vertex5.neighbors.add(vertex2);
        vertex5.neighbors.add(vertex4);
        vertex5.weightMap.put(vertex3, 7);
        vertex5.weightMap.put(vertex2, 5);
        vertex5.weightMap.put(vertex4, 9);

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(vertex1);
        vertices.add(vertex2);
        vertices.add(vertex3);
        vertices.add(vertex4);
        vertices.add(vertex5);

        Graph graph = new Graph(vertices);
        new Prim().solve(graph, vertex1);
        for (Vertex vertex : graph.V) {
            if (vertex.parent != null) {
                System.out.println(vertex.label + "---" + vertex.parent.label);
            }
        }
    }

    public void solve(Graph graph, Vertex startNode) {
        Map<Integer, Vertex> vertexMap = new HashMap<>();
        for (Vertex vertex : graph.V) {
            vertex.key = Integer.MAX_VALUE;
            vertex.parent = null;
            vertexMap.put(vertex.label, vertex);
        }

        startNode.key = 0;
        FibonacciHeap heap = FibonacciHeap.makeHeap();
        for (Vertex vertex : graph.V) {
            heap.insert(new Node(vertex.label, vertex.key));
        }

        Set<Vertex> visited = new HashSet<>();
        while (heap.size() != 0) {
            Node node = heap.extractMin();
            int label = node.label;
            Vertex vertex = vertexMap.get(label);
            for (Vertex neighbor : vertex.neighbors) {
                int weight = vertex.weightMap.get(neighbor);
                if (!visited.contains(neighbor) && weight < neighbor.key) {
                    neighbor.parent = vertex;
                    Node neighborNode = heap.searchByLabel(neighbor.label);
                    heap.decreaseKey(neighborNode, weight);
                    neighbor.key = weight;
                }
            }
        }
    }
}
