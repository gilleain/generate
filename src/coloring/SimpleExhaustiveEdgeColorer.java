package coloring;

import java.util.ArrayList;
import java.util.List;

import model.Graph;
import model.Graph.Edge;

import combinatorics.SubsetLister;

public class SimpleExhaustiveEdgeColorer implements EdgeColorer {
    
    public List<Graph> color(Graph g) {
        List<Graph> coloredGraphs = new ArrayList<Graph>();
        SubsetLister<Edge> lister = new SubsetLister<Edge>(g.edges);
        for (List<Edge> edgeList : lister) {
            Graph c = new Graph(g);
            for (Edge e : edgeList) {
                c.getEdge(e.a, e.b).o = 2;
            }
            coloredGraphs.add(c);
        }
        return coloredGraphs;
    }
    
}
