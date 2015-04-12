package degreeseq;

import graph.model.IntGraph;
import group.Partition;

public interface OrbitPartitioner {
    
    Partition getOrbitPartition(IntGraph g, int[] degreeSequence);
    
}
