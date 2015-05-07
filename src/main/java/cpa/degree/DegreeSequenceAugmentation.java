package cpa.degree;

import graph.model.IntGraph;

import java.util.Set;

import cpa.Augmentation;

public class DegreeSequenceAugmentation implements Augmentation<ResidualDegreeGraphPair> {
    
    private ResidualDegreeGraphPair augmentedObject;
    
    private Integer vertexToConnect;
    
    private Set<Integer> verticesToAddTo;
    
    public DegreeSequenceAugmentation(int[] residuals) {
        augmentedObject = new ResidualDegreeGraphPair(new IntGraph(), residuals);
    }
    
    public DegreeSequenceAugmentation(IntGraph start, int[] residuals) {
        augmentedObject = new ResidualDegreeGraphPair(new IntGraph(start), residuals);
    }
    
    public DegreeSequenceAugmentation(IntGraph graph, int[] parentResiduals, Integer vertexToConnect, Set<Integer> verticesToAddTo) {
        this.verticesToAddTo = verticesToAddTo;
        augmentedObject = make(graph, parentResiduals, vertexToConnect, verticesToAddTo);
    }

    private ResidualDegreeGraphPair make(IntGraph g, int[] parentResiduals, Integer vertexToConnect, Set<Integer> verticesToAddTo) {
        IntGraph h = new IntGraph(g);
        int[] residuals = new int[parentResiduals.length]; 
        for (int i = 0; i < parentResiduals.length; i++) {
            if (verticesToAddTo.contains(i)) {
                h.makeEdge(vertexToConnect, i);
                residuals[i] = parentResiduals[i] - 1;
            } else {
                residuals[i] = parentResiduals[i];
            }
        }
        return new ResidualDegreeGraphPair(h, residuals);
    }

    @Override
    public ResidualDegreeGraphPair getAugmentedObject() {
        return augmentedObject;
    }

    @Override
    public boolean isCanonical() {
        // TODO Auto-generated method stub
        return true;
    }

}
