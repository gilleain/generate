package augment;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.PermutationGroup;

import org.junit.Test;

import augmentation.GraphOrbitRepresentativeGenerator;

public class OrbitsTest {
	
	public void test(IntGraph g) {
		GraphOrbitRepresentativeGenerator generator = new GraphOrbitRepresentativeGenerator();
		generator.setGraph(g);
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		PermutationGroup autG = refiner.getAutomorphismGroup(g);
//		System.out.println(generator.getOrbitPartition(autG, g.getVertexCount()));
	}

	@Test
	public void testClaw() {
		test(new IntGraph("0:1,0:2,0:3"));
	}
	
	@Test
	public void testSquare() {
		test(new IntGraph("0:1,0:2,1:3,2:3"));
	}
	
	@Test
	public void testLine() {
		test(new IntGraph("0:1,0:2,1:3"));
	}
}
