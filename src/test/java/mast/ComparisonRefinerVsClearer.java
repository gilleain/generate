package mast;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import group.PermutationGroup;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import tree.WROMTreeGenerator;

public class ComparisonRefinerVsClearer {
	
	public void test(int n) {
		for (Graph tree : WROMTreeGenerator.generate(n)) {
			GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
	        PermutationGroup group = refiner.getAutomorphismGroup(tree);
	        BigInteger symRefiner = BigInteger.valueOf(group.orderAsLong());
			
			ClearerAlgorithm algorithm = new ClearerAlgorithm();
	        BigInteger symClearer = algorithm.getSym(tree);
	        
	        System.out.println(tree + "\t" + symClearer + "\t" + symRefiner);
	        Assert.assertEquals(symRefiner, symClearer);
		}
	}
	
	@Test
	public void fours() {
		test(4);
	}
	
	@Test
	public void fives() {
		test(5);
	}
	
	@Test
	public void sixes() {
		test(6);
	}
	
	@Test
	public void sevens() {
		test(7);
	}
	
	@Test
	public void eights() {
		test(8);
	}
	
	@Test
	public void nine() {
		test(9);
	}

	@Test
	public void ten() {
		test(10);
	}


}
