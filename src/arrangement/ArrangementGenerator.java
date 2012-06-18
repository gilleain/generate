package arrangement;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

public class ArrangementGenerator {
	
	public List<Graph> makeArrangements(int n) {
		List<Graph> arrangements = new ArrayList<Graph>();
		make(arrangements, 0, n);
		return arrangements;
	}

	/**
	 * For each arrangement in the list, make all children by adding the minimal edge and reject
	 * those that are non-canonical. These children are then recursively passed to the method,
	 * for i up to n.
	 *  
	 * @param arrangements
	 * @param n
	 */
	public void make(List<Graph> arrangements, int i, int n) {
		if (i == n) return;
		List<Graph> children = new ArrayList<Graph>();
		for (Graph parent : arrangements) {
			for (Graph child : extend(parent)) {
				if (isCanonical(child)) {
					children.add(child);
				}
			}
		}
		make(children, i + 1, n);
	}

	public boolean isCanonical(Graph child) {
		return BruteForceCanonicalChecker.isCanonical(child);
	}

	public List<Graph> extend(Graph parent) {
		return ExhaustiveExtender.extend(parent);
	}

}
