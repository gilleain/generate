package degreeseq;

import group.Partition;
import model.Graph;
import model.GraphSignature;

public class SignatureOrbitPartitioner implements OrbitPartitioner {
    
    @Override
    public Partition getOrbitPartition(Graph g, int[] degreeSequence) {
        GraphSignature graphSignature = new GraphSignature(g);
        
        return null;
    }
    
}
