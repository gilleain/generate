package tree;

import graph.model.Graph;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate non-isomorphic rooted trees using Beyer and Hedetniemi's (1980) algorithm.
 * 
 * Trees are represented as distance sequences, where the distance of vertex i from the root is
 * stored in the order returned by a preorder transversal. So [0, 1, 1] represents the tree [0]([1][2])
 * and [0, 1, 2, 2, 1] is [0]([1]([2][3])[4]).
 * 
 * For a sequence L = [l1, l2, ... ln], p is the largest index such that L[p] != 1 and q is the parent
 * of p (the first number q < p, such that L[p] - L[q] = 1). Then the successor s(L) = S = 
 * [s1, s2, ... sn] is L[i] for 1 <= i < p and S[i - (p - q)] for p <= i < n.
 * 
 * The number of trees on n vertices is given by OEIS sequence A000081 as:
 * 
 * [0, 1, 1, 2, 4, 9, 20, 48, 115, 286, 719, 1842, 4766...]
 * 
 * for n = 0 to 12, so it does not grow especially fast.
 * 
 * @author maclean
 *
 */
public class RootedTreeGenerator {
	
	public static List<Graph> generate(int n) {
		List<Graph> trees = new ArrayList<Graph>();
		int[] initialSequence = new int[n];
		for (int i = 0; i < n; i++) {
			initialSequence[i] = i;
		}
		trees.add(treeFromSeq(initialSequence));
		
		int[] currentSeq = initialSequence;
		boolean hasMore = true;
		while (hasMore) {
			int p;
			for (p = n - 1; p > 0; p--) {
				if (currentSeq[p] != 1) {
					break;
				}
			}
			int q;
			for (q = p - 1; q >= 0; q--) {
				if (currentSeq[p] - currentSeq[q] == 1) {
					break;
				}
			}
			int[] nextSeq = new int[n];
			for (int i = 0; i < p; i++) {
				nextSeq[i] = currentSeq[i];
			}
			for (int j = p; j < n; j++) {
				nextSeq[j] = nextSeq[j - (p - q)];
			}
			trees.add(treeFromSeq(nextSeq));
			boolean isTerminalSeq = true;
			for (int i = 1; i < n; i++) {
				if (nextSeq[i] != 1) {
					isTerminalSeq = false;
					break;
				}
			}
			hasMore = !isTerminalSeq;
			currentSeq = nextSeq;
		}
		return trees;
	}

	public static Graph treeFromSeq(int[] depthSeq) {
	    IntGraph tree = new IntGraph();
		int p = -1;
		for (int i = 0; i < depthSeq.length; i++) {
			int d = depthSeq[i];
			if (p == -1) {
				p = 0;		
			} else {
				int q;
				for (q = i - 1; q >= 0; q--) {
					if (d - depthSeq[q] == 1) {
						break;
					}
				}
				p = q;
			}
			if (i != 0) {
				tree.makeEdge(p, i);
			}
		}
		return tree;
	}

}
