package degreeseq;

import graph.model.Graph;
import group.Partition;

public interface OrbitPartitioner {
    
    Partition getOrbitPartition(Graph g, int[] degreeSequence);
    
}
