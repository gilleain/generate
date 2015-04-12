package augment;

import graph.model.IntGraph;

import org.junit.Test;

import augmentation.AugmentingGenerator;
import augmentation.UpperObject;

public class UpperObjectsTest {
	
	public void test(IntGraph g) {
		AugmentingGenerator generator = new AugmentingGenerator();
		for (UpperObject u : generator.getUpperObjects(g)) {
			System.out.println(u);
		}
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
