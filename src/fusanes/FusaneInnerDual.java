package fusanes;

import graph.model.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A reduced representation of a fusane as a labeled inner dual.
 * 
 * The inner dual of a planar drawing of a graph is just the dual graph with one vertex deleted,
 * along with the edges incident to it. This deleted vertex is the one corresponding to the 
 * outer face of the drawing.
 * 
 * This dual graph is then labeled with the arrangement of free edges in the corresponding fusane.
 * For example, if the central vertex in the dual o-o-o is labeled with {1,3} then the middle cycle
 * of the fusane has one free edge on (say) the upper side, and three free edges on the lower side.
 * This inner dual represents the molecule ???
 * 
 * @author maclean
 *
 */
public class FusaneInnerDual {
	
	/**
	 * The vertices and edges of the dual.
	 */
	private Graph dualGraph;
	
	/**
	 * Labels for the vertices of the dual, corresponding to the free edges in the cycle.
	 */
	private Map<Integer, List<Integer>> labels;
	
	/**
	 * Construct an inner dual from a specific
	 * graph - the argument is a dual graph, not
	 * the planar graph.
	 * 
	 * @param dualGraph
	 */
	public FusaneInnerDual(Graph dualGraph) {
		this.dualGraph = dualGraph;
		this.labels = new HashMap<Integer, List<Integer>>();
	}
	
	public FusaneInnerDual(FusaneInnerDual other) {
	    this(other.dualGraph);
	    for (int index : other.labels.keySet()) {
	        this.labels.put(index, new ArrayList<Integer>(other.labels.get(index)));
	    }
	}
	
	public FusaneInnerDual(FusaneInnerDual partialLabeling, int vertexToLabel, int[] labelList) {
	    this(partialLabeling);
	    setLabel(vertexToLabel, labelList);
    }

    public Map<Integer, List<Integer>> getLabels() {
		return labels;
	}
	
	public void setLabel(int vertexIndex, int... intLabels) {
		List<Integer> labelList = new ArrayList<Integer>();
		for (int label : intLabels) {
			labelList.add(label);
		}
		labels.put(vertexIndex, labelList);
	}
	
	public List<Integer> getLabelsFor(int vertexIndex) {
	    return labels.get(vertexIndex);
	}
	
	public List<Integer> getConnected(int v) {
	    return dualGraph.getConnected(v);
	}
	
	public String toString() {
		return dualGraph.getSortedEdgeString() + "->" + labels;
	}

}
