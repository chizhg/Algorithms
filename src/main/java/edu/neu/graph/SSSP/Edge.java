package edu.neu.graph.SSSP;

/**
 * Created by HappyMole on 4/3/17.
 */
class Edge {
    Vertex src;
    Vertex dest;
    int weight;

    public Edge(Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}