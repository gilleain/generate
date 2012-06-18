package arrangement;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

/**
 * Extend an arrangement by all possible ways.
 * 
 * @author maclean
 *
 */
public class ExhaustiveExtender {
	
	public static List<Graph> extend(Graph parent) {
		List<Graph> children = new ArrayList<Graph>();
		for (int i = 0; i < parent.getVertexCount() - 1; i++) {
			if (parent.hasEdge(i, i + 1)) {
				continue;
			} else {
				children.add(makeGraphWithShortEdge(parent, i, i + 1));
			}
		}
		return children;
	}
	
	public static Graph makeGraphWithLongEdge(Graph parent, Graph.Edge edge, int eindexB) {
		Graph child = new Graph();
		
		return child;
	}
	
	public static Graph makeGraphWithShortEdge(Graph parent, int start, int end) {
		Graph child = new Graph();
		
		// copy the edges less than and equal to the current
		int last = -1;
		int lastIndex = -1;
		int eSize = parent.edges.size();
		for (int i = 0; i < eSize; i++) {
			Graph.Edge edge = parent.edges.get(i);
			if (edge.a > start) {
				child.makeEdge(edge.a + 2, edge.b);
			} else if (edge.b > start) {
				child.makeEdge(edge.a, edge.b + 2);
			} else {
				child.makeEdge(edge.a, edge.b);
			}
			if (Math.abs(edge.a - edge.b) == 1){
				last = Math.max(edge.a, edge.b);
			} else {
				last = Math.min(edge.a, edge.b);
			}
			if (last >= start) {
				lastIndex = i;
				break;
			}
		}
		
		// add the short edge
		child.makeEdge(last + 1, last + 2);
		
		// make new edges, two on from parent
		for (int i = lastIndex + 1; i < eSize; i++) {
			Graph.Edge edge = parent.edges.get(i);
			child.makeEdge(edge.a + 2, edge.b + 2);
		}
		
		return child;
	}

}
