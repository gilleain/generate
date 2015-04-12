package augment;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;

import org.junit.Test;

public class CanonicalTest {
	
	
	@Test
	public void pawGraphTest() {
	    IntGraph graphA = new IntGraph("0:1,0:2,0:3,1:3");
	    IntGraph graphB = new IntGraph("0:1,0:2,0:3,1:2");
		GraphDiscretePartitionRefiner refinerA = new GraphDiscretePartitionRefiner();
		refinerA.getAutomorphismGroup(graphA);
		System.out.println(refinerA.getCertificate());
		GraphDiscretePartitionRefiner refinerB = new GraphDiscretePartitionRefiner();
		refinerB.getAutomorphismGroup(graphB);
		System.out.println(refinerB.getCertificate());
	}

}
