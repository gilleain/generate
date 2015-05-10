package cpa.degree;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import setorbit.BruteForcer;
import setorbit.SetOrbit;
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
        this.vertexToConnect = vertexToConnect;
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
            } else if (i == vertexToConnect) {
                residuals[i] = 0;
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
        if (vertexToConnect == null) {
            return true;    // XXX ?
        }
        IntGraph augmentedGraph = augmentedObject.getGraph();
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autH = refiner.getAutomorphismGroup(augmentedGraph);
        Permutation best = refiner.getBest();
        int chosen = best.get(vertexToConnect);
        List<Integer> connected = getConnectedDownstream(chosen, augmentedGraph, best);
        boolean isCanonical = connected.size() == verticesToAddTo.size() && 
                inOrbit(connected, verticesToAddTo, autH);
        return isCanonical;
    }
    
    private List<Integer> getConnectedDownstream(int connectionPoint, IntGraph graph, Permutation best) {
        List<Integer> filtered = new ArrayList<Integer>();
        for (int i : graph.getConnected(connectionPoint)) {
            if (i > connectionPoint) {
                filtered.add(best.get(i));
            }
        }
        return filtered;
    }
    
    private boolean inOrbit(List<Integer> set, Set<Integer> toFind, PermutationGroup autH) {
        SetOrbit orbit = new BruteForcer().getInOrbit(set, autH);
        return orbit.contains(toFind);
    }

}
