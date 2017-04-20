package edu.neu.graph.maxflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Edge {
    int u, v;
    int capacity, flow;

    public Edge(int u, int v, int capacity, int flow) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        this.flow = flow;
    }
}

class Vertex {
    int label;
    int height;
    int excessFlow;

    public Vertex(int label) {
        this.label = label;
    }
}

class Graph {
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;

    public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
}

public class PushRelabel {
    public static void main(String[] args) {
        Vertex s = new Vertex(0);
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex t = new Vertex(5);
        ArrayList<Vertex> vertices = new ArrayList<>(Arrays.asList(s, v1, v2, v3, v4, t));

        Edge sv1 = new Edge(0, 1, 16, 0);
        Edge sv2 = new Edge(0, 2, 13, 0);
        Edge v2v1 = new Edge(2, 1, 4, 0);
        Edge v1v3 = new Edge(1, 3, 12, 0);
        Edge v3v2 = new Edge(3, 2, 9, 0);
        Edge v2v4 = new Edge(2, 4, 14, 0);
        Edge v4v3 = new Edge(4, 3, 7, 0);
        Edge v3t = new Edge(3, 5, 20, 0);
        Edge v4t = new Edge(4, 5, 4, 0);
        ArrayList<Edge> edges = new ArrayList<>(Arrays.asList(sv1, sv2, v2v1, v1v3, v3v2, v2v4, v4v3, v3t, v4t));

        Graph graph = new Graph(vertices, edges);

        PushRelabel pushRelabel = new PushRelabel();
        System.out.println(pushRelabel.doPushRelabel(graph, s, t));
    }

    public int doPushRelabel(Graph graph, Vertex src, Vertex dest) {
        preflow(graph, src);

        int overflowVertex;
        // loop until no vertex is in overflow
        while ((overflowVertex = findOverflowVertex(graph.vertices, src, dest)) != -1) {
            if (!push(overflowVertex, graph)) {
                relabel(overflowVertex, graph);
            }
        }

        // the destination vertex's excess flow would be the final maximum flow
        return dest.excessFlow;
    }

    private void preflow(Graph graph, Vertex src) {
        List<Vertex> vertices = graph.vertices;
        List<Edge> edges = graph.edges;
        // set all vertices' height and excess flow as 0
        for (Vertex vertex : vertices) {
            vertex.height = 0;
            vertex.excessFlow = 0;
        }

        // set all edges' flow as 0
        for (Edge edge : edges) {
            edge.flow = 0;
        }
        // set the source vertex's height as the total number of vertices
        src.height = vertices.size();

        /*
            1. saturate the edges starting from the source vertex
            2. initialize excess flow for source vertex's neighbors
            3. update the residual graph by adding an reversed edge
         */
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            Edge edge = edges.get(i);
            if (edge.u == src.label) {
                edge.flow = edge.capacity;
                vertices.get(edge.v).excessFlow += edge.capacity;
                src.excessFlow -= edge.capacity;
                edges.add(new Edge(edge.v, src.label, 0, -edge.flow));
            }
        }
    }

    // push flow from an overflow vertex u
    private boolean push(int u, Graph graph) {
        List<Vertex> vertices = graph.vertices;
        List<Edge> edges = graph.edges;
        // traverse all edges to find neighbors of u to which flow can be pushed
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (edge.u == u) {
                // if flow is equal to capacity, it cannot be pushed
                if (edge.flow == edge.capacity) {
                    continue;
                }

                // push is only possible if the neighbor's height is
                // smaller than that of the overflowing vertex
                if (vertices.get(u).height > vertices.get(edge.v).height) {
                    // flow to be pushed is equal to minimum of
                    // remaining flow on edge and excess flow.
                    int flow = Math.min(edge.capacity - edge.flow, vertices.get(u).excessFlow);
                    // reduce excess flow for overflowing vertex
                    vertices.get(u).excessFlow -= flow;
                    // increase excess flow for the neighbor
                    vertices.get(edge.v).excessFlow += flow;
                    edge.flow += flow;
                    // update the residual graph
                    updateReverseEdgeFlow(edges, i, flow);
                    return true;
                }
            }
        }

        return false;
    }

    private void relabel(int u, Graph graph) {
        List<Edge> edges = graph.edges;
        List<Vertex> vertices = graph.vertices;

        // find the neighbor with the minimum height
        int minNeighborHeight = Integer.MAX_VALUE;
        for (Edge edge : edges) {
            if (edge.u == u) {
                // cannot relabel is flow is equal to capacity
                if (edge.flow == edge.capacity) {
                    continue;
                }

                // update height of u
                if (vertices.get(edge.v).height < minNeighborHeight) {
                    minNeighborHeight = vertices.get(edge.v).height;
                    vertices.get(u).height = minNeighborHeight + 1;
                }
            }
        }

    }

    // update reverse flow for flow added on ith edge
    private void updateReverseEdgeFlow(List<Edge> edges, int i, int flow) {
        int u = edges.get(i).v, v = edges.get(i).u;
        for (Edge edge : edges) {
            if (edge.u == u && edge.v == v) {
                edge.flow -= flow;
                return;
            }
        }

        // add reversed edge in the residual graph
        Edge edge = new Edge(u, v, flow, 0);
        edges.add(edge);
    }

    // return label of overflowing vertex, return -1 if no vertex is found
    private int findOverflowVertex(List<Vertex> vertices, Vertex src, Vertex dest) {
        for (Vertex vertex : vertices) {
            if (vertex.label != src.label && vertex.label != dest.label && vertex.excessFlow > 0) {
                return vertex.label;
            }
        }
        return -1;
    }
}
