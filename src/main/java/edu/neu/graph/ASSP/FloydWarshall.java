package edu.neu.graph.ASSP;

import java.util.Arrays;

public class FloydWarshall {
    public static final int INFI = Integer.MAX_VALUE;
    public static final int NIL = -1;

    public static void main(String[] args) {
        /*
                10
            0-------->3
            |         ^
           5|         |1
            v         |
            1-------->2
                 3
         */

        int[][] graph = {{0, 5, INFI, 10},
                         {INFI, 0, 3, INFI},
                         {INFI, INFI, 0, 1},
                         {INFI, INFI, INFI, 0}};
        int n = graph.length;
        // initial distance is the same as the graph:
        // - initial values of shortest distances are based on shortest paths
        //   considering no intermediate vertex
        int[][] dist = Arrays.copyOf(graph, n);

        // initial parent:
        // - if i==j, or j is not reachable from i, j's parent is NIL
        // - otherwise j's parent is i
        int[][] parents = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || graph[i][j] == INFI) {
                    parents[i][j] = NIL;
                } else {
                    parents[i][j] = i;
                }
            }
        }

        FloydWarshall floydWarshall = new FloydWarshall();
        floydWarshall.solve(graph, dist, parents);
        System.out.println("shortest path weights from any vertex to any vertex: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == INFI) {
                    System.out.print("INFI  ");
                } else {
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }

        System.out.println("parents of each vertex in the shortest paths: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (parents[i][j] == NIL) {
                    System.out.print("NIL  ");
                } else {
                    System.out.print(parents[i][j] + "    ");
                }
            }
            System.out.println();
        }
    }

    public void solve(int[][] graph, int[][] dist, int[][] parents) {
        int n = graph.length;

        /* Add all vertices one by one to the set of intermediate
           vertices.
          ---> Before start of a iteration, we have shortest
               distances between all pairs of vertices such that
               the shortest distances consider only the vertices in
               set {0, 1, 2, .. k-1} as intermediate vertices.
          ----> After the end of a iteration, vertex no. k is added
                to the set of intermediate vertices and the set
                becomes {0, 1, 2, .. k} */
        for (int k = 0; k < n; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < n; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < n; j++) {
                    // if vertex k is on the shortest path from i to j,
                    // then update the value of dist[i][j]
                    if (dist[i][k] != INFI && dist[k][j] != INFI
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        parents[i][j] = parents[k][j];
                    }
                }
            }
        }
    }
}
