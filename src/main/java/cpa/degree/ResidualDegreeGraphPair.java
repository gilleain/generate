package cpa.degree;

import graph.model.IntGraph;

/**
 * Holder class for a pair of <graph, degree sequence> where the 
 * degree sequence is the residual or remaining degree to be unsatisfied.
 * 
 * @author maclean
 *
 */
public class ResidualDegreeGraphPair {
    
    private IntGraph graph;
    
    private int[] residuals;
    
    public ResidualDegreeGraphPair() {
        this.graph = new IntGraph();
    }
    
    public ResidualDegreeGraphPair(IntGraph graph, int[] residuals) {
        this.graph = graph;
        this.residuals = residuals;
    }

    public IntGraph getGraph() {
        return graph;
    }
    
    public int[] getResiduals() {
        return residuals;
    }

}
