package cpa;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import augmentation.GraphOrbitRepresentativeGenerator;

/**
 * Augment a parent graph by adding a new vertex to all non-symmetric vertex subsets.
 *  
 * @author maclean
 *
 */
public class GraphVertexAugmentor implements Augmentor<IntGraph>{

    @Override
    public List<Augmentation<IntGraph>> augment(Augmentation<IntGraph> parent) {
        List<Augmentation<IntGraph>> augmentations = new ArrayList<Augmentation<IntGraph>>();
        IntGraph parentGraph = parent.getAugmentedObject();
        for (Set<Integer> rep : getOrbitReps(parentGraph)) {
            augmentations.add(new GraphVertexAugmentation(parentGraph, rep));
        }
        return augmentations;
    }
    
    private List<Set<Integer>> getOrbitReps(IntGraph g) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autG = refiner.getAutomorphismGroup(g);
        GraphOrbitRepresentativeGenerator orbRep = new GraphOrbitRepresentativeGenerator();
        orbRep.setGraph(g);
        // UGH
        List<SortedSet<Integer>> sortedSetList = orbRep.getOrbitCombinations(autG, g.getVertexCount());
        List<Set<Integer>> setList = new ArrayList<Set<Integer>>();
        for (SortedSet<Integer> sortedSet : sortedSetList) {
            Set<Integer> set = new HashSet<Integer>();
            set.addAll(sortedSet);
            setList.add(set);
        }
        return setList;
    }

    public Augmentation<IntGraph> getInitial() {
        return new GraphVertexAugmentation(new IntGraph("0:1"));
    }

}
