package tree;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

/**
 * @author maclean
 *
 */
public class WROMTreeGenerator {
	
	public static List<Graph> generate(int n) {
		List<Graph> trees = new ArrayList<Graph>();
		int[] initialSeq = makeInitialSeq(n);
		
		int[] currentSeq = initialSeq;
		boolean hasMore = true;
		while (hasMore) {
			int m = -1;
			for (m = 2; m < n; m++) {
				if (currentSeq[m] == 1) {
					break;
				}
			}
			
			boolean accepted = true;
			if (m == -1) {
				accepted = false;
			} else {
			    
			    // L1 = [l[2] - 1, l[3] - 1, ... , l[m-1] - 1]
				int maxL1 = 0;
				for (int i = 1; i < m - 1; i++) {
					int x = currentSeq[i] - 1; 
					if (x > maxL1) {
						maxL1 = x;
					}
				}
				
                // L2 = [l[1], l[m], l[m+1], ... , l[n]]
				int maxL2 = 1;
				for (int i = m; i < n; i++) {
					if (currentSeq[i] > maxL2) {
						maxL2 = currentSeq[i];
					}
				}
				
				if (maxL2 > maxL1) {
					accepted = true;
				} else if (maxL2 == maxL1) {
					int lenL1 = m - 1;
					int lenL2 = n - m + 1;
					if (lenL1 < lenL2) {
						accepted = true;
					} else if (lenL1 == lenL2) {
						if (currentSeq[1] - 1 <= currentSeq[0]) {
							int l1Idx = 2;
							int l2Idx = m;
							boolean lexSmaller = true;
							for (; l2Idx < n; l1Idx++, l2Idx++) {
								if (currentSeq[l1Idx] > currentSeq[l2Idx]) {
									lexSmaller = false;
									break;
								}
							}
							accepted = lexSmaller;
						}
					} else {
						accepted = false;
					}
				} else {
					accepted = false;
				}
			}
			if (accepted) {
				trees.add(treeFromSeq(currentSeq));
			}
			currentSeq = successor(currentSeq);
			
			boolean isTerminalSeq = true;
			for (int i = 1; i < n; i++) {
				if (currentSeq[i] != 1) {
					isTerminalSeq = false;
					break;
				}
			}
			if (isTerminalSeq) {
				trees.add(treeFromSeq(currentSeq));
			}
			hasMore = !isTerminalSeq;
		}
		return trees;
	}
	
	public static int[] successor(int[] currentSeq) {
		int n = currentSeq.length;
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
		return nextSeq;
	}
	
	public static int[] makeInitialSeq(int n) {
		int[] seq = new int[n];
		int m = (int)Math.floor(n / 2) + 1;
		for (int i = 0; i < m; i++) {
			seq[i] = i;
		}
		int k = 1;
		for (int j = m; j < n; j++) {
			seq[j] = k;
			k++;
		}
		return seq;
	}
	
	public static Graph treeFromSeq(int[] depthSeq) {
		Graph tree = new Graph();
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
