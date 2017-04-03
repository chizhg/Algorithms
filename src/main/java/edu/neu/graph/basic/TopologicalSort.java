package edu.neu.graph.basic;

import edu.neu.graph.basic.models.Node;

import java.util.*;

/**
 * Created by HappyMole on 4/1/17.
 */
public class TopologicalSort {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);

        node1.neighbors.add(node2);
        node1.neighbors.add(node5);
        node4.neighbors.add(node5);
        node2.neighbors.add(node5);
        node2.neighbors.add(node3);
        node7.neighbors.add(node3);
        node7.neighbors.add(node8);
        node3.neighbors.add(node9);
        node8.neighbors.add(node9);

        Node[] nodeArr = new Node[]{node1, node2, node3, node4, node5, node6, node7, node8, node9};
        List<Node> graph = Arrays.asList(nodeArr);

        TopologicalSort topologicalSort = new TopologicalSort();
//        List<Node> result = topologicalSort.dfsSolve(graph);
        List<Node> result = topologicalSort.indegreeSolve(graph);
        for (Node node : result) {
            System.out.println(node.label);
        }
    }

    public List<Node> dfsSolve(List<Node> graph) {
        DepthFirstSearch depthFirstSearch = new DepthFirstSearch();
        depthFirstSearch.solve(graph);

        List<Node> result = new ArrayList<>(graph);
        Collections.sort(result, (n1, n2) -> (n2.finishTime - n1.finishTime));
        return result;
    }

    public List<Node> indegreeSolve(List<Node> graph) {
        Map<Node, Integer> indegrees = new HashMap<>();
        for (Node node : graph) {
            for (Node neighbor : node.neighbors) {
                indegrees.put(neighbor, indegrees.getOrDefault(neighbor, 0) + 1);
            }
        }

        List<Node> result = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph) {
            if (!indegrees.containsKey(node)) {
                result.add(node);
                queue.offer(node);
            }
        }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Node neighbor : node.neighbors) {
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
