package generate;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;


/**
 * Extremely simple graph generator that checks for canonical graphs at each 
 * step.
 * 
 * @author maclean
 *
 */
public class EdgewiseGenerator {
    
    public List<IntGraph> graphs;
    
    public int maxLength;
    
    public int maxDegree;
    
    public EdgewiseGenerator(int maxLength, int maxDegree) {
        this.graphs = new ArrayList<IntGraph>();
        this.maxLength = maxLength;
        this.maxDegree = maxDegree;
    }
    
    public void generate() {
//        generate(true);
    	generate(false);
    }
    
    public void generate(boolean checkEdgeOrder) {
        IntGraph initialGraph = new IntGraph();
        this.generate(null, initialGraph, checkEdgeOrder);
    }
    
    public void generate(IntGraph parent, IntGraph g, boolean checkEdgeOrder) {
        if (g.getVertexCount() >= this.maxLength) return;
        
//        if (g.getVertexCount() < 2 || CanonicalChecker.isCanonical3(g, checkEdgeOrder)) {
        if (CanonicalChecker.isCanonical5(parent, g)) {
            System.out.println("CANON\t" + g);
            if (g.getVertexCount() == this.maxLength - 1 && g.isConnected()) {
//                if (CanonicalChecker.isCanonical3(g, checkEdgeOrder)) {
//            	if (CanonicalChecker.isCanonical5(parent, g)) {
                    this.graphs.add(g);
//                }
            }
        } else {
            System.out.println(".....\t" + g);
            return;
        }
        
//        int n = Math.max(2, g.getVertexCount() + 1);
//        int n = Math.max(2, g.getVertexCount());
        int n = Math.max(2, maxLength);
        for (int i = 0; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                if (canAddBond(i, j, g)) {
                    generate(g, g.makeNew(i, j), checkEdgeOrder);
                } else {
                    continue;
                }
            }
        }
    }
    
    public boolean canAddBond(int i, int j, IntGraph g) {
        return     i != j 
                && !g.isConnected(i, j)
                && !g.saturated(i, maxDegree)
                && !g.saturated(j, maxDegree);
    }
}
