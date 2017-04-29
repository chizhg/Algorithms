package edu.neu.graph.models;

import java.util.List;

public class Graph {
    public List<Vertex> V;
    public List<Edge> E;

    public Graph(List<Vertex> V, List<Edge> E) {
        this.V = V;
        this.E = E;
    }

    public Graph(List<Vertex> V) {
        this.V = V;
    }
}
