package generate;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

public class VertexwiseGenerator {
    
    public List<IntGraph> graphs;
    
    public int maxLength;
    
    public int maxDegree;
    
    public VertexwiseGenerator(int maxLength, int maxDegree) {
        this.graphs = new ArrayList<IntGraph>();
        this.maxLength = maxLength;
        this.maxDegree = maxDegree;
    }
    
    public void generate() {
        IntGraph initialGraph = new IntGraph();
        this.generate(initialGraph);
    }
    
    public void generate(IntGraph g) {
        if (g.getVertexCount() >= this.maxLength) return;
        if (!CanonicalChecker.isCanonical3(g)) return;
        if (g.getVertexCount() < 2 || CanonicalChecker.isCanonical3(g)) {
            System.out.println("CANON\t" + g);
            if (g.getVertexCount() == this.maxLength - 1 && g.isConnected()) {
                this.graphs.add(g);
            }
        } else {
            return;
        }
    }

}
