package generate;

import group.Permutation;
import group.Permutor;
import group.SSPermutationGroup;
import model.Graph;
import model.GraphDiscretePartitionRefiner;
import model.TraversalBacktracker;

public class CanonicalChecker {
    
  
    public static boolean isCanonical(Graph graph) {
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
    
    public static boolean isCanonical2(Graph graph) {
        if (!graph.edgesInOrder()) return false;
        int n = graph.getVertexCount();
        SSPermutationGroup symN = SSPermutationGroup.makeSymN(n);
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        SSPermutationGroup autN = refiner.getAutomorphismGroup(graph);
        
        String original = graph.getSortedEdgeString();
        TraversalBacktracker traveller = new TraversalBacktracker(original, symN, graph, autN);
        symN.apply(traveller);
        return traveller.checkedAll();
    }
    
    public static boolean isCanonical3(Graph graph) {
//        if (!graph.edgesInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = 
//            new GraphDiscretePartitionRefiner(false, false);
            new GraphDiscretePartitionRefiner(false, true);
        return refiner.isCanonical(graph);
    }
    
    public static boolean isCanonical4(Graph graph) {
        if (!graph.edgesInOrder()) return false;
//        if (!graph.colorsInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.isCanonical(graph);
        return refiner.getFirst().isIdentity();
    }
    
    public static boolean isCanonical5(Graph parent, Graph child) {
    	if (parent == null || parent.getVertexCount() < 2) return true;
    	GraphDiscretePartitionRefiner parentRefiner = new GraphDiscretePartitionRefiner(false, false);
    	parentRefiner.isCanonical(parent);
//    	Permutation canonParent = parentRefiner.getFirst();
    	Permutation canonParent = parentRefiner.getBest();
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner(false, false);
    	childRefiner.isCanonical(child);
//    	Permutation canonChild = childRefiner.getFirst();
    	Permutation canonChild = childRefiner.getBest();
    	Graph parentCanonised = parent.getPermutedGraph(canonParent.getValues());
    	if (!parentCanonised.edgesInOrder()) return false;
    	String parentString = parentCanonised.toString();
    	parentString = parentString.substring(1, parentString.length() - 1);
    	Graph childCanonised = child.getPermutedGraph(canonChild.getValues());
    	if (!childCanonised.edgesInOrder()) return false;
    	String childString = childCanonised.toString();
    	childString = childString.substring(1, childString.length() - 1);
    	boolean isPrefix = childString.contains(parentString); 
    	System.out.println("[" + parentString + "] -> [" + childString + "] : " + isPrefix);
    	return isPrefix;
    }
    
    public static boolean isCanonical6(Graph parent, Graph child, int i, int j) {
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner(false, false);
    	SSPermutationGroup group = childRefiner.getAutomorphismGroup(child);
    	for (Permutation p : group.all()) {
    		System.out.println(p);
    	}
    	return true;
    }

    public static boolean isCanonical3(Graph graph, boolean checkEdgeOrder) {
        if (checkEdgeOrder && !graph.edgesInOrder()) return false;
        GraphDiscretePartitionRefiner refiner = 
//            new GraphDiscretePartitionRefiner(false, false);
            new GraphDiscretePartitionRefiner(false, true);
        return refiner.isCanonical(graph);
    }
    
}
