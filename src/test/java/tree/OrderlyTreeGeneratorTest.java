package tree;

import graph.model.Graph;

import org.junit.Assert;
import org.junit.Test;

public class OrderlyTreeGeneratorTest {
	
	public void testN(int n, int expected) {
		int count = 0;
		for (Graph tree : OrderlyTreeGenerator.generate(n))  {
			System.out.println(count + "\t" + tree.getSortedEdgeString());
			count++;
		}
		Assert.assertEquals(expected, count);
	}
	
	@Test
	public void test4() {
		testN(4, 2);
	}
	
	@Test
	public void test5() {
		testN(5, 3);
	}
	
	@Test
	public void test6() {
		testN(6, 6);
	}

}
