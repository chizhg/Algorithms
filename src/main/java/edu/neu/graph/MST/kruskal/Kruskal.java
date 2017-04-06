package edu.neu.graph.MST.kruskal;

import edu.neu.graph.models.Edge;
import edu.neu.graph.models.Graph;
import edu.neu.graph.models.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Greedy




 */
public class Kruskal {
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
        Vertex vertex0 = new Vertex(0);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);
        Vertex vertex6 = new Vertex(6);

        vertices.add(new Vertex(0));
        vertices.add(new Vertex(1));
        edges.add(new Edge(vertex0, vertex1, 4));

        vertices.add(new Vertex(2));
        edges.add(new Edge(vertex0, vertex2, 9));
        edges.add(new Edge(vertex1, vertex2, 11));

        vertices.add(new Vertex(3));
        edges.add(new Edge(vertex2, vertex3, 7));

        vertices.add(new Vertex(4));
        edges.add(new Edge(vertex1, vertex4, 8));
        edges.add(new Edge(vertex3, vertex4, 2));

        vertices.add(new Vertex(5));
        edges.add(new Edge(vertex2, vertex5, 1));
        edges.add(new Edge(vertex3, vertex5, 6));

        vertices.add(new Vertex(6));
        edges.add(new Edge(vertex4, vertex6, 4));
        edges.add(new Edge(vertex5, vertex6, 2));

        Graph graph = new Graph(vertices, edges);
        List<Edge> result = new Kruskal().solve(graph);

        for (Edge edge : result) {
            System.out.println(edge.src.label + "-->" + edge.dest.label);
        }
    }

    public List<Edge> solve(Graph graph) {
        List<Edge> result = new ArrayList<>();

        List<Integer> labels = new ArrayList<>();
        for (Vertex vertex : graph.V) {
            labels.add(vertex.label);
        }
        UnionFind unionFind = new UnionFind(labels);

        List<Edge> edges = graph.E;
        Collections.sort(edges, (e1, e2) -> (e1.weight - e2.weight));

        for (Edge edge : edges) {
            int srcLabel = edge.src.label;
            int destLabel = edge.dest.label;

            if (unionFind.find(srcLabel) != unionFind.find(destLabel)) {
                result.add(edge);
                unionFind.union(srcLabel, destLabel);
            }
        }

        return result;
    }
}
