package tree;

import java.util.ArrayList;
import java.util.List;

import model.Graph;
import model.GraphSignature;

public class OrderlyTreeGenerator {

	public static List<Graph> generate(int n) {
		List<Graph> trees = new ArrayList<Graph>();
		Graph edgeTree = new Graph("0:1");
		trees.add(edgeTree);
		int i = 2;
		while (i < n) {
			trees = generate(i, trees);
			i++;
		}
		return trees;
	}
	
	public static List<Graph> generate(int n, List<Graph> parents) {
		List<Graph> children = new ArrayList<Graph>();
		String currentMax = "";
		for (Graph parent : parents) {
			for (int i = 0; i < n - 1; i++) {
				for (int j = i + 1; j < n; j++) {
					Graph child = makeChild(parent, i, j);
					String childAsString = child.getSortedEdgeString();
					if (childAsString.compareTo(currentMax) > 0 && isCanonical(child)) {
						children.add(child);
						currentMax = childAsString;
					}
				}
			}
		}
		return children;
	}
	
	private static Graph makeChild(Graph parent, int i, int j) {
		int n = parent.getVertexCount();
		Graph child = new Graph();
		n++;
		return child;
	}
	
	private static boolean isCanonical(Graph tree) {
		GraphSignature sig = new GraphSignature(tree);
		return sig.isCanonicallyLabelled();
	}
}
