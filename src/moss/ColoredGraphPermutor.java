package moss;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import moss.ColoredGraph.Edge;
import moss.ColoredGraph.Vertex;

import group.Permutor;

public class ColoredGraphPermutor extends Permutor implements Iterator<ColoredGraph> {

    private ColoredGraph graph;
    
    public ColoredGraphPermutor(ColoredGraph graph) {
        super(graph.vertices.size());
        this.graph = graph;
    }

    public ColoredGraph next() {
        return permute(getNextPermutation());
    }
    
    private ColoredGraph permute(int[] p) {
        ColoredGraph nextGraph = new ColoredGraph();
        for (int i = 0; i < p.length; i++) {
            Vertex v = graph.vertices.get(p[i]);
            nextGraph.addVertex(v.color);
        }
        Comparator<Edge> edgeComparator = new Comparator<Edge>() {

            public int compare(Edge e0, Edge e1) {
                if (e0.start < e1.start) {
                    return -1;
                } else {
                    if (e0.end < e1.end) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
            
        };
        for (Edge edge : graph.edges) {
            int ps = p[edge.start];
            int pe = p[edge.end];
            if (ps < pe) {
                nextGraph.addEdge(ps, pe, edge.color);
            } else {
                nextGraph.addEdge(pe, ps, edge.color);
            }
        }
        
        Collections.sort(nextGraph.edges, edgeComparator);
        
        return nextGraph;
    }

    public void remove() {
        
    }

}
