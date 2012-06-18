package augmentation;

import group.Partition;
import group.Permutation;
import group.SSPermutationGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Generates all k-subsets representatives given an automorphism group.
 * 
 * @author maclean
 *
 */
public abstract class OrbitRepresentativeGenerator<T> {
	
	public List<SortedSet<Integer>> getOrbitCombinations(SSPermutationGroup autG, int n) {
		Partition orbitPartition = getOrbitPartition(autG, n);
		SortedSet<Integer> singletonSet = new TreeSet<Integer>();
		SortedSet<Integer> candidateSet = new TreeSet<Integer>();
		for (int i = 0; i < orbitPartition.size(); i++) {
			SortedSet<Integer> cell = orbitPartition.getCell(i);
			int first = cell.first();
			if (representativeIsCandidate(first)) {
				singletonSet.add(first);
				for (int element : cell) {
					candidateSet.add(element);
				}
			}
		}
		
		List<SortedSet<Integer>> combinations = new ArrayList<SortedSet<Integer>>();
		
		// add the empty set
		combinations.add(new TreeSet<Integer>());
		
		List<SortedSet<Integer>> emptySet = new ArrayList<SortedSet<Integer>>();
		emptySet.add(new TreeSet<Integer>());
		combine(candidateSet, autG, n, emptySet, combinations);
		return combinations;
	}
	
	/**
	 * Checks that the representative element in an orbit is a potential candidate.
	 * 
	 * @param orbitRep the minimal element in an orbit
	 * @return true if this rep (and the members of its orbit) should be considered
	 */
	public abstract boolean representativeIsCandidate(int orbitRep);
	
	/**
	 * Construct an orbit partition from an automorphism group. 
	 * 
	 * @param autG the automorphism group
	 * @param n the size of the object the group permutes
	 * @return an orbit partition
	 */
	public Partition getOrbitPartition(SSPermutationGroup autG, int n) {
		// needed for checking which elements have been assigned to an orbit 
		int[] orbits = new int[n];
		
		Partition orbitPartition = new Partition();
		int assignedCount = 0;
		SortedSet<Integer> currentOrbit = null;
		for (int i = 0; i < n; i++) {
			if (orbits[i] != 0) continue;
			if (currentOrbit == null) {
				currentOrbit = new TreeSet<Integer>();
				currentOrbit.add(i);
				orbitPartition.addCell(currentOrbit);
				orbits[i] = orbitPartition.size();
				assignedCount++;
			}
			for (Permutation permutation : autG.all()) {
				Set<Integer> toAdd = new TreeSet<Integer>();
				for (int element : currentOrbit) {
					int partner = permutation.get(element);
					if (!currentOrbit.contains(partner)) {
						toAdd.add(partner);
						orbits[partner] = orbitPartition.size();
						assignedCount++;
					}
				}
				currentOrbit.addAll(toAdd);	// XXX can this fail?
			}
			currentOrbit = null;
			if (assignedCount == n) break;
		}
		return orbitPartition;
	}
	
	private void combine(
			SortedSet<Integer> candidateSet,
			SSPermutationGroup autG,
			int n,
			List<SortedSet<Integer>> parents,
			List<SortedSet<Integer>> combinations) {
		List<SortedSet<Integer>> children = new ArrayList<SortedSet<Integer>>();
		for (SortedSet<Integer> parent : parents) {
			int first;
			if (parent.size() == 0) {
				first = -1;
			} else {
				first = parent.last() + 1;
			}
			for (int element : candidateSet.tailSet(first)) {
				SortedSet<Integer> child = new TreeSet<Integer>(parent);
				child.add(element);
				if (combinationIsValid(child) && isMinimalInOrbit(child, autG)) {
					children.add(child);
					combinations.add(child);
				}
			}
		}
		if (children.size() > 0) {
			combine(candidateSet, autG, n, children, combinations);
		}
	}
	
	/**
	 * Check a combination to see if it fulfills some particular criteria.
	 * 
	 * @param combination
	 * @return
	 */
	public abstract boolean combinationIsValid(SortedSet<Integer> combination);
	
	private boolean isMinimalInOrbit(SortedSet<Integer> set, SSPermutationGroup autG) {
		String original = setToString(set);
		for (Permutation permutation : autG.all()) {
			if (setToString(apply(permutation, set)).compareTo(original) < 0) {
				return false;
			}
		}
		return true;
	}
	
	private String setToString(SortedSet<Integer> set) {
		StringBuffer buffer = new StringBuffer();
		int count = set.size();
		for (int element : set) {
			buffer.append(element);
			count--;
			if (count > 0) {
				buffer.append(',');
			}
		}
		return buffer.toString();
	}
	
	private SortedSet<Integer> apply(Permutation permutation, SortedSet<Integer> set) {
		SortedSet<Integer> permutedSet = new TreeSet<Integer>();
		for (int element : set) {
			permutedSet.add(permutation.get(element));
		}
		return permutedSet;
	}

}
