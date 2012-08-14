package degreeseq;

import group.Partition;
import model.Graph;

public interface OrbitPartitioner {
    
    Partition getOrbitPartition(Graph g, int[] degreeSequence);
    
}
