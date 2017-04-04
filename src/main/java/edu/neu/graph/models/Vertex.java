package edu.neu.graph.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HappyMole on 4/3/17.
 */
public class Vertex {
    // all vertices must have labels
    public int label;
    // BFS or DFS
    public Color color;

    // DFS
    public int discoverTime;
    public int finishTime;

    public Vertex parent;

    public int key;
    public int distance;

    public List<Vertex> neighbors;
    public Map<Vertex, Integer> weightMap;

    public Vertex(int label) {
        this.label = label;

        this.color = Color.WHITE;
        this.discoverTime = Integer.MAX_VALUE;
        this.finishTime = Integer.MAX_VALUE;

        this.parent = null;
        this.key = Integer.MAX_VALUE;
        this.distance = Integer.MAX_VALUE;

        this.neighbors = new ArrayList<>();
        this.weightMap = new HashMap<>();
    }
}
