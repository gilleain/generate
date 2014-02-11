package fusanes;

import graph.model.Graph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleFusaneLabeler {
    
    public static final Map<Integer, List<int[]>> degreeLabelMap = new HashMap<Integer, List<int[]>>();
    
    static {
        List<int[]> d1 = new ArrayList<int[]>();
        d1.add(new int[] { 5 });
        degreeLabelMap.put(1, d1);
        List<int[]> d2 = new ArrayList<int[]>();
        d2.add(new int[] { 1, 3 });
        d2.add(new int[] { 3, 1 });
        d2.add(new int[] { 2, 2 });
        degreeLabelMap.put(2, d2);
        List<int[]> d3 = new ArrayList<int[]>();
        d3.add(new int[] { 1, 1, 1 });
        degreeLabelMap.put(3, d3);
    }
    
    public static List<FusaneInnerDual> label(Graph tree) {
        List<FusaneInnerDual> duals = new ArrayList<FusaneInnerDual>();
        duals.add(new FusaneInnerDual(tree));
        BitSet visited = new BitSet(tree.getVertexCount());
        duals = recursivelyLabel(tree, 0, visited, duals);
        return duals;
    }
    
    private static List<FusaneInnerDual> recursivelyLabel(
            Graph tree, int currentIndex, BitSet visited, List<FusaneInnerDual> parents) {
        visited.set(currentIndex);
		int degree = tree.degree(currentIndex);
		List<FusaneInnerDual> children = new ArrayList<FusaneInnerDual>();
		for (FusaneInnerDual partialLabeling : parents) {
		    for (int[] labelList : degreeLabelMap.get(degree)) {
		        children.add(new FusaneInnerDual(partialLabeling, currentIndex, labelList));
		    }
		}
		List<FusaneInnerDual> labellings = children;
		for (int neighbour : tree.getConnected(currentIndex)) {
		    if (visited.get(neighbour)) continue;
		    labellings = recursivelyLabel(tree, neighbour, visited, labellings);
		}
		return labellings;
	}
}
