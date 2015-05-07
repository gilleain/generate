package cpa.degree;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import combinatorics.KSubsetLister;

import cpa.Augmentation;
import cpa.Augmentor;

public class DegreeSequenceAugmentor implements Augmentor<ResidualDegreeGraphPair> {
    
//    private int[] residuals;
    
    public DegreeSequenceAugmentor(int[] residuals) {
//        this.residuals = residuals;
    }

    @Override
    public List<Augmentation<ResidualDegreeGraphPair>> augment(Augmentation<ResidualDegreeGraphPair> parent) {
        List<Augmentation<ResidualDegreeGraphPair>> augmentations = new ArrayList<Augmentation<ResidualDegreeGraphPair>>();
        
        ResidualDegreeGraphPair parentObj = parent.getAugmentedObject();
        IntGraph parentGraph = parentObj.getGraph();
        int[] residuals = parentObj.getResiduals();
        int vertexToConnect = getVertexToConnect(parentGraph, residuals);
        
        for (Set<Integer> verticesToConnectTo : getSets(parentGraph, vertexToConnect, residuals)) {
            augmentations.add(
                    new DegreeSequenceAugmentation(
                            parentGraph, residuals, vertexToConnect, verticesToConnectTo));
        }
        return augmentations;
    }
    
    /**
     * @return the first non-zero index
     */
    private int getVertexToConnect(IntGraph parentGraph, int[] residuals) {
        for (int index = 0; index < residuals.length; index++) {
            if (residuals[index] != 0) { 
                return index;
            }
        }
        return -1;  // XXX ?
    }

    /**
     * @return sets of a particular size 
     */
    private Iterable<Set<Integer>> getSets(IntGraph parentGraph, int start, int[] residuals) {
        // XXX - can we always do this?
        int size = residuals[start];
        
        List<Integer> elements = getElements(start, residuals);
        if (size > elements.size()) {
//            System.err.println("No subsets of size " + size + " in " + elements + " from " + parentGraph);
            return new ArrayList<Set<Integer>>();
        }
        return new KSubsetLister<>(size, elements);
    }

    /**
     * @return the set of vertices that need connection
     */
    private List<Integer> getElements(int start, int[] residuals) {
        List<Integer> connectables = new ArrayList<Integer>();
        for (int index = start + 1; index < residuals.length; index++) {
            if (residuals[index] > 0) { 
                connectables.add(index);
            }
        }
//        System.out.println("connect set " + connectables);
        return connectables;
    }

}
