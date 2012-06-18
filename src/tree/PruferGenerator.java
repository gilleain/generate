package tree;

import model.Graph;

/**
 * An unranking generator that uses the PrŸfer correspondence between labelled trees on n
 * and the set of all (n - 2)-tuples of vertices. 
 * 
 * @author maclean
 *
 */
public class PruferGenerator {
	
	public static Graph rankToTree(int rank, int n) {
		return sequenceToTree(rankToSequence(rank, n), n);
	}
	
	public static int sequenceToRank(int[] sequence) {
		int r = 0;
		int p = 1;
		int n = sequence.length;
		for (int i = n - 2; i > 0; i--) {
			r += p * (sequence[i] - 1);
			p *= n;
		}
		return r;
	}
	
	public static int[] rankToSequence(int r, int n) {
		double rank = r;
		int[] sequence = new int[n];
		for (int i = n - 2; i > 0; i--) {
			sequence[i] = (int) ((rank % n) + 1);
			double x = (double)(rank - sequence[i] + 1);
			rank = Math.floor(x / n);
		}
		return sequence;
	}
	
	public static int[] treeToSequence(Graph tree) {
		int n = tree.getVertexCount();
		int[] seq = new int[n];
		int[] degrees = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			degrees[i] = 0;
		}
		for (Graph.Edge edge : tree.edges) {
			degrees[edge.a + 1]++;
			degrees[edge.b + 1]++;
		}
		
		Graph treeCopy = new Graph(tree);
		for (int i = 1; i <= n - 2; i++) {
			int x = n;
			while (degrees[x] != 1) {
				x--;
			}
			int y = n;
			while (!treeCopy.hasEdge(x - 1, y - 1)) {
				y--;
			}
			seq[i] = y;
			degrees[x]--;
			degrees[y]--;
			treeCopy.edges.remove(treeCopy.getEdge(x - 1, y - 1));
		}
		return seq;
	}
	
	public static Graph sequenceToTree(int[] pruferSequence, int n) {
		Graph tree = new Graph();
		pruferSequence[n - 1] = 1;
		int[] degrees = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			degrees[i] = 1;
		}
		for (int i = 1; i <= n - 2; i++) {
			degrees[pruferSequence[i]] = degrees[pruferSequence[i]] + 1;  
		}
		for (int i = 1; i <= n - 1; i++) {
			int x = n;
			while (degrees[x] != 1) {
				x--;
			}
			int y = pruferSequence[i];
			degrees[x]--;
			degrees[y]--;
			tree.makeEdge(x - 1, y - 1);
		}
		return tree;
	}

}
