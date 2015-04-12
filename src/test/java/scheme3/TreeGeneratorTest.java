package scheme3;

import graph.model.IntGraph;

import org.junit.Test;

public class TreeGeneratorTest {
	
	@Test
	public void test5FromSingle4() {
		IntGraph initial = new IntGraph("0:1,0:2,1:3");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 5);
	}
	
	@Test
	public void test6FromSingle4() {
		IntGraph initial = new IntGraph("0:1,0:2,1:3");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test6FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test7FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 7);
	}
	
	@Test
	public void test8FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 8);
	}
	
	@Test
	public void test9FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 9);
	}
	
	@Test
	public void test10FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 10);
	}
	
	@Test
	public void test11FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 11);
	}
	
	@Test
	public void test12FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 12);
	}
	
	@Test
	public void test13FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 13);
	}
	
	@Test
	public void test14FromSingleEdge() {
		IntGraph initial = new IntGraph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 14);
	}

}
