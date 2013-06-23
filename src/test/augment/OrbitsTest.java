package test.augment;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import group.PermutationGroup;

import org.junit.Test;

import augmentation.GraphOrbitRepresentativeGenerator;

public class OrbitsTest {
	
	public void test(Graph g) {
		GraphOrbitRepresentativeGenerator generator = new GraphOrbitRepresentativeGenerator();
		generator.setGraph(g);
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		PermutationGroup autG = refiner.getAutomorphismGroup(g);
		System.out.println(generator.getOrbitPartition(autG, g.getVertexCount()));
	}

	@Test
	public void testClaw() {
		test(new Graph("0:1,0:2,0:3"));
	}
	
	@Test
	public void testSquare() {
		test(new Graph("0:1,0:2,1:3,2:3"));
	}
	
	@Test
	public void testLine() {
		test(new Graph("0:1,0:2,1:3"));
	}
}
