package edu.neu.graph.ASSP;

import java.util.Arrays;

public class SimpleMatrixMulti {
    public static final int INFI = Integer.MAX_VALUE;
    public static final int NIL = -1;

    public int[][] solve(int[][] graph) {
        int n = graph.length;
        int[][] dist = Arrays.copyOf(graph, n);

        for (int m = 2; m < n; m++) {
            dist = extendedShortestPaths(graph, dist);
        }

        return dist;
    }

    private int[][] extendedShortestPaths(int[][] graph, int[][] dist) {
        int n = graph.length;
        int[][] nextDist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                nextDist[i][j] = INFI;
                for (int k = 0; k < n; k++) {
                    if (dist[i][k] != INFI && graph[k][j] != INFI) {
                        nextDist[i][j] = Math.min(nextDist[i][j], dist[i][k] + graph[k][j]);
                    }
                }
            }
        }

        return nextDist;
    }
}
