package edu.neu.graph.SSSP;

import java.util.List;

/**
 * Created by HappyMole on 4/3/17.
 */
public class Graph {
    List<Vertex> V;
    List<Edge> E;

    public Graph(List<Vertex> V, List<Edge> E) {
        this.V = V;
        this.E = E;
    }
}
