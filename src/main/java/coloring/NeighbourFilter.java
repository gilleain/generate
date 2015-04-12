package coloring;

import filter.Filter;
import graph.model.Graph;
import graph.model.IntEdge;
import graph.model.IntGraph;

public class NeighbourFilter implements Filter {
    
    public boolean filter(Graph graph) {
        IntGraph g = (IntGraph) graph;  // XXX unchecked!
        for (int i = 0; i < g.getEdgeCount(); i++) {
            IntEdge eI = g.edges.get(i);
            for (int j = i + 1; j < g.edges.size(); j++) {
                IntEdge eJ = g.edges.get(j);
                if (eI.adjacent(eJ) && eI.o > 1 && eJ.o > 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
