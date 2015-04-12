package degreeseq;

import graph.model.IntGraph;

public interface KiralyTreeListener {

    public void handle(int[] degreeSequence, IntGraph g);
    
}
