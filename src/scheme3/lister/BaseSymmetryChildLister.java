package scheme3.lister;

import group.SSPermutationGroup;
import model.Graph;
import model.GraphDiscretePartitionRefiner;

public abstract class BaseSymmetryChildLister {
    
    protected GraphDiscretePartitionRefiner refiner;
    
    public BaseSymmetryChildLister() {
        refiner = new GraphDiscretePartitionRefiner();
    }
    
    protected SSPermutationGroup getAut(Graph g) {
        return refiner.getAutomorphismGroup(g);
    }

}
