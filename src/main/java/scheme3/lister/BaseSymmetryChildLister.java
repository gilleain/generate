package scheme3.lister;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.PermutationGroup;

public abstract class BaseSymmetryChildLister {
    
    protected GraphDiscretePartitionRefiner refiner;
    
    public BaseSymmetryChildLister() {
        refiner = new GraphDiscretePartitionRefiner();
    }
    
    protected PermutationGroup getAut(IntGraph g) {
        return refiner.getAutomorphismGroup(g);
    }

}
