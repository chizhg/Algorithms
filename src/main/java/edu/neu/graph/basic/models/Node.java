package edu.neu.graph.basic.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HappyMole on 4/3/17.
 */
public class Node {
    public Node parent;
    public Color color;
    public List<Node> neighbors;
    public int distance;
    public int discoverTime;
    public int finishTime;
    public int label;

    public Node(int label) {
        this.label = label;
        this.parent = null;
        this.color = Color.WHITE;
        this.neighbors = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
        this.discoverTime = Integer.MAX_VALUE;
        this.finishTime = Integer.MAX_VALUE;
    }
}
