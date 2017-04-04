package edu.neu.graph.basic;

import edu.neu.graph.models.Vertex;

import java.util.*;

/**
 * Created by HappyMole on 4/1/17.
 */
public class TopologicalSort {
    public static void main(String[] args) {
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);
        Vertex vertex7 = new Vertex(7);
        Vertex vertex8 = new Vertex(8);
        Vertex vertex9 = new Vertex(9);

        vertex1.neighbors.add(vertex2);
        vertex1.neighbors.add(vertex5);
        vertex4.neighbors.add(vertex5);
        vertex2.neighbors.add(vertex5);
        vertex2.neighbors.add(vertex3);
        vertex7.neighbors.add(vertex3);
        vertex7.neighbors.add(vertex8);
        vertex3.neighbors.add(vertex9);
        vertex8.neighbors.add(vertex9);

        Vertex[] vertexArr = new Vertex[]{vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7, vertex8, vertex9};
        List<Vertex> graph = Arrays.asList(vertexArr);

        TopologicalSort topologicalSort = new TopologicalSort();
//        List<Vertex> result = topologicalSort.dfsSolve(graph);
        List<Vertex> result = topologicalSort.indegreeSolve(graph);
        for (Vertex vertex : result) {
            System.out.println(vertex.label);
        }
    }

    public List<Vertex> dfsSolve(List<Vertex> graph) {
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch();
        depthFirstSearch.solve(graph);

        List<Vertex> result = new ArrayList<>(graph);
        Collections.sort(result, (n1, n2) -> (n2.finishTime - n1.finishTime));
        return result;
    }

    public List<Vertex> indegreeSolve(List<Vertex> graph) {
        Map<Vertex, Integer> indegrees = new HashMap<>();
        for (Vertex vertex : graph) {
            for (Vertex neighbor : vertex.neighbors) {
                indegrees.put(neighbor, indegrees.getOrDefault(neighbor, 0) + 1);
            }
        }

        List<Vertex> result = new ArrayList<>();
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex vertex : graph) {
            if (!indegrees.containsKey(vertex)) {
                result.add(vertex);
                queue.offer(vertex);
            }
        }

        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            for (Vertex neighbor : vertex.neighbors) {
                indegrees.put(neighbor, indegrees.get(neighbor) - 1);
                if (indegrees.get(neighbor) == 0) {
                    result.add(neighbor);
                    indegrees.remove(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }
}
