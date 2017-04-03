package edu.neu.graph.MST.kruskal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Vertex {
    int label;
    public Vertex(int label) {
        this.label = label;
    }
}


class Graph {
    List<Vertex> vertices;
    List<Edge> edges;

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
}

class Edge {
    int src;
    int dest;
    int weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

public class MSTKruskal {
    public static void main(String[] args) {
        /*
        original graph:

                8
            1 ----- 4
           /|      / \
         4/ |11   /2  \
         /  |    /     \
        0   |   3       \4
         \  |  / \       \
         9\ | /7  \6      \
           \|/     \       \
            2 ----- 5 ----- 6
                1       2

         minimum spanning tree:

                8
            1 ----- 4
           /       / \
         4/       /2  \
         /       /     \
        0       3       \4
                         \
                          \
                           \
            2 ----- 5 ----- 6
                1       2
         */


        List<Vertex> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        vertices.add(new Vertex(0));
        vertices.add(new Vertex(1));
        edges.add(new Edge(0, 1, 4));

        vertices.add(new Vertex(2));
        edges.add(new Edge(0, 2, 9));
        edges.add(new Edge(1, 2, 11));

        vertices.add(new Vertex(3));
        edges.add(new Edge(2, 3, 7));

        vertices.add(new Vertex(4));
        edges.add(new Edge(1, 4, 8));
        edges.add(new Edge(3, 4, 2));

        vertices.add(new Vertex(5));
        edges.add(new Edge(2, 5, 1));
        edges.add(new Edge(3, 5, 6));

        vertices.add(new Vertex(6));
        edges.add(new Edge(4, 6, 4));
        edges.add(new Edge(5, 6, 2));

        Graph graph = new Graph(vertices, edges);
        List<Edge> result = new MSTKruskal().solve(graph);

        for (Edge edge : result) {
            System.out.println(edge.src + "-->" + edge.dest);
        }
    }

    public List<Edge> solve(Graph graph) {
        List<Edge> result = new ArrayList<>();

        List<Integer> labels = new ArrayList<>();
        for (Vertex vertex : graph.vertices) {
            labels.add(vertex.label);
        }
        UnionFind unionFind = new UnionFind(labels);

        List<Edge> edges = graph.edges;
        Collections.sort(edges, (e1, e2) -> (e1.weight - e2.weight));

        for (Edge edge : edges) {
            Node srcNode = unionFind.getNode(edge.src);
            Node destNode = unionFind.getNode(edge.dest);

            if (unionFind.find(srcNode) != unionFind.find(destNode)) {
                result.add(edge);
                unionFind.union(srcNode, destNode);
            }
        }

        return result;
    }
}
