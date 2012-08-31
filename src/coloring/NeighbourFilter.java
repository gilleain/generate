package coloring;

import model.Edge;
import model.Graph;
import filter.Filter;

public class NeighbourFilter implements Filter {
    
    public boolean filter(Graph g) {
        for (int i = 0; i < g.edges.size(); i++) {
            Edge eI = g.edges.get(i);
            for (int j = i + 1; j < g.edges.size(); j++) {
                Edge eJ = g.edges.get(j);
                if (eI.adjacent(eJ) && eI.o > 1 && eJ.o > 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
