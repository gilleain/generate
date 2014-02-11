package filter;

import graph.model.Graph;

import java.util.BitSet;

public class DegreeSequenceFilter implements Filter {
    
    private int[] degreeSequence;
    
    public DegreeSequenceFilter(int... degreeSequence) {
        this.degreeSequence = degreeSequence;
    }
    
    public boolean filter(Graph graph) {
        BitSet found = new BitSet();
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (contains(graph.degree(i), found)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    
    private boolean contains(int degree, BitSet found) {
        int length = degreeSequence.length;
        for (int degreeSeqIndex = 0; degreeSeqIndex < length; degreeSeqIndex++) {
            if (found.get(degreeSeqIndex)) {
                continue;
            } else {
                if (degreeSequence[degreeSeqIndex] == degree) {
                    found.set(degreeSeqIndex);
                    return true;
                }
            }
        }
        return false;
    }

}
