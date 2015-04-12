package augment;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import augmentation.AugmentingGenerator;

public class CombineTest {
	
	private void combine(SortedSet<Integer> baseSet, 
						 List<List<Integer>> parents, 
						 List<List<Integer>> combinations) {
		List<List<Integer>> children = new ArrayList<List<Integer>>();
		for (List<Integer> parent : parents) {
			int lastInParent;
			if (parent.size() == 0) {
				lastInParent = -1;
			} else {
				lastInParent = parent.get(parent.size() - 1);
			}
			for (int element : baseSet.tailSet(lastInParent + 1)) {
				List<Integer> child = new ArrayList<Integer>(parent);
				child.add(element);
				children.add(child);
				combinations.add(child);
			}
		}
		if (children.size() > 0) {
			combine(baseSet, children, combinations);
		}
	}
	
	private TreeSet<Integer> makeTreeSet(int... elements) {
		TreeSet<Integer> treeSet = new TreeSet<Integer>();
		for (int element : elements) {
			treeSet.add(element);
		}
		return treeSet;
	}

	@Test
	public void combineSimple() {
		SortedSet<Integer> baseSet = makeTreeSet(0, 2, 3, 5);
		List<List<Integer>> emptySet = new ArrayList<List<Integer>>();
		emptySet.add(new ArrayList<Integer>());
		List<List<Integer>> combs = new ArrayList<List<Integer>>();
		combine(baseSet, emptySet, combs);
		System.out.println(combs);
	}
	
	@Test
	public void combineFromPawGraph() {
	    IntGraph g = new IntGraph("0:1,0:2,0:3,1:2");
		AugmentingGenerator generator = new AugmentingGenerator();
		List<SortedSet<Integer>> combs = generator.getOrbitCombinations(g);
		System.out.println(combs);
		Assert.assertTrue(combs.size() == 11);
	}
	
	@Test
	public void combineFrom2LineGraph() {
	    IntGraph g = new IntGraph("0:1");
		AugmentingGenerator generator = new AugmentingGenerator();
		List<SortedSet<Integer>> combs = generator.getOrbitCombinations(g);
		System.out.println(combs);
		Assert.assertTrue(combs.size() == 2);
	}
}
