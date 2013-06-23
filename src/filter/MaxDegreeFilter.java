package filter;

import graph.model.Graph;

public class MaxDegreeFilter implements Filter {
    
    private int maxDegree;
    
    public MaxDegreeFilter(int maxDegree) {
        this.maxDegree = maxDegree;
    }
    
    public boolean filter(Graph graph) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (graph.degree(i) <= maxDegree) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

}
