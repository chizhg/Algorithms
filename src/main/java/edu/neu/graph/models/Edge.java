package edu.neu.graph.models;


/**
 * Created by HappyMole on 4/3/17.
 */
public class Edge {
    public Vertex src;
    public Vertex dest;
    public int weight;

    public Edge(Vertex src, Vertex dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}