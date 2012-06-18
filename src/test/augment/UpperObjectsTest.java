package test.augment;

import model.Graph;

import org.junit.Test;

import augmentation.AugmentingGenerator;
import augmentation.UpperObject;

public class UpperObjectsTest {
	
	public void test(Graph g) {
		AugmentingGenerator generator = new AugmentingGenerator();
		for (UpperObject u : generator.getUpperObjects(g)) {
			System.out.println(u);
		}
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
