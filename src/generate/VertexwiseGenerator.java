package generate;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

public class VertexwiseGenerator {
    
    public List<Graph> graphs;
    
    public int maxLength;
    
    public int maxDegree;
    
    public VertexwiseGenerator(int maxLength, int maxDegree) {
        this.graphs = new ArrayList<Graph>();
        this.maxLength = maxLength;
        this.maxDegree = maxDegree;
    }
    
    public void generate() {
        Graph initialGraph = new Graph();
        this.generate(initialGraph);
    }
    
    public void generate(Graph g) {
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
