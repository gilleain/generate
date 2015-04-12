package coloring;

import graph.model.IntEdge;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

import combinatorics.SubsetLister;

public class SimpleExhaustiveEdgeColorer implements EdgeColorer {
    
    public List<IntGraph> color(IntGraph g) {
        List<IntGraph> coloredGraphs = new ArrayList<IntGraph>();
        SubsetLister<IntEdge> lister = new SubsetLister<IntEdge>(g.edges);
        for (List<IntEdge> edgeList : lister) {
            IntGraph c = new IntGraph(g);
            for (IntEdge e : edgeList) {
                c.getEdge(e.a, e.b).o = 2;
            }
            coloredGraphs.add(c);
        }
        return coloredGraphs;
    }
    
}
