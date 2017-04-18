package edu.neu.graph.maxflow;

import java.util.LinkedList;
import java.util.Queue;

/*
    This algorithm is an implementation of the Ford-Fulkerson method for
        computing the maximum flow in a flow network in O(VE^2) time.

    Basic idea:
    1. Maintain a residual graph which represents the graph minus the flow
    2. Find an augmenting path in the residual graph through BFS
    3. Try saturating the path, and update the residual graph
    4. Keep performing the step 2 and 3 until there is no augmenting path
 */
public class EdmondsKarp {
    public int fordFulkerson(int[][] graph, int src, int dest) {
        int vertexNum = graph.length;
        /*
            Create a residual graph and fill the residual graph with
                given capacities in the original graph as residual capacities
                in residual graph.
         */
        int[][] residualGraph = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        int[] parents = new int[vertexNum];
        int maxFlow = 0;
        // augment the flow while there is a path from source to destination
        while (pathExists(residualGraph, src, dest, parents)) {
            // find the maximum flow we can augment through the path found
            int pathFlow = Integer.MAX_VALUE;
            for (int v = dest; v != src; v = parents[v]) {
                int u = parents[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            /*
                Update the residual capacities of the edges and reversed edges
                    along the path
             */
            for (int v = dest; v != src; v = parents[v]) {
                int u = parents[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            // add the path flow to the overall max flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    /*
        Check if there is a path from src to dest.
        Fill the parents array to restore the path in future.
     */
    private boolean pathExists(int[][] residualGraph, int src, int dest, int[] parents) {
        int vertexNum = residualGraph.length;

        boolean[] visited = new boolean[vertexNum];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(src);
        visited[src] = true;
        parents[src] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < vertexNum; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.offer(v);
                    parents[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[dest];
    }
}
