package generate;

import graph.group.GraphDiscretePartitionRefiner;
import graph.group.TraversalBacktracker;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;
import group.Permutor;

public class CanonicalChecker {
    
  
    public static boolean isCanonical(IntGraph graph) {
        if (!graph.edgesInOrder()) return false;
        Permutor permutor = new Permutor(graph.getVertexCount());
        String original = graph.getSortedEdgeString();
        while (permutor.hasNext()) {
            int[] p = permutor.getNextPermutation();
            String permuted = graph.getSortedPermutedEdgeString(p);
            
            if (original.compareTo(permuted) > 0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isCanonical2(IntGraph graph) {
        if (!graph.edgesInOrder()) return false;
        int n = graph.getVertexCount();
        PermutationGroup symN = PermutationGroup.makeSymN(n);
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autN = refiner.getAutomorphismGroup(graph);
        
        String original = graph.getSortedEdgeString();
        TraversalBacktracker traveller = new TraversalBacktracker(original, symN, graph, autN);
        symN.apply(traveller);
        return traveller.checkedAll();
    }
    
    public static boolean isCanonical3(IntGraph graph) {
//        if (!graph.edgesInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = 
//            new GraphDiscretePartitionRefiner(false, false);
//            new GraphDiscretePartitionRefiner(false, true);
                new GraphDiscretePartitionRefiner();    // XXX
        return refiner.isCanonical(graph);
    }
    
    public static boolean isCanonical4(IntGraph graph) {
        if (!graph.edgesInOrder()) return false;
//        if (!graph.colorsInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.isCanonical(graph);
        return refiner.getFirst().isIdentity();
    }
    
    public static boolean isCanonical5(IntGraph parent, IntGraph child) {
    	if (parent == null || parent.getVertexCount() < 2) return true;
    	GraphDiscretePartitionRefiner parentRefiner = new GraphDiscretePartitionRefiner();
    	parentRefiner.isCanonical(parent);
//    	Permutation canonParent = parentRefiner.getFirst();
    	Permutation canonParent = parentRefiner.getBest();
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner();
    	childRefiner.isCanonical(child);
//    	Permutation canonChild = childRefiner.getFirst();
    	Permutation canonChild = childRefiner.getBest();
    	IntGraph parentCanonised = parent.getPermutedGraph(canonParent.getValues());
    	if (!parentCanonised.edgesInOrder()) return false;
    	String parentString = parentCanonised.toString();
    	parentString = parentString.substring(1, parentString.length() - 1);
    	IntGraph childCanonised = child.getPermutedGraph(canonChild.getValues());
    	if (!childCanonised.edgesInOrder()) return false;
    	String childString = childCanonised.toString();
    	childString = childString.substring(1, childString.length() - 1);
    	boolean isPrefix = childString.contains(parentString); 
    	System.out.println("[" + parentString + "] -> [" + childString + "] : " + isPrefix);
    	return isPrefix;
    }
    
    public static boolean isCanonical6(IntGraph parent, IntGraph child, int i, int j) {
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner();
    	PermutationGroup group = childRefiner.getAutomorphismGroup(child);
    	for (Permutation p : group.all()) {
    		System.out.println(p);
    	}
    	return true;
    }

    public static boolean isCanonical3(IntGraph graph, boolean checkEdgeOrder) {
        if (checkEdgeOrder && !graph.edgesInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = 
//            new GraphDiscretePartitionRefiner(false, false);
//            new GraphDiscretePartitionRefiner(false, true);
                new GraphDiscretePartitionRefiner();    // XXX
        return refiner.isCanonical(graph);
    }
    
}
