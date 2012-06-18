package test.scheme3;

import model.Graph;

import org.junit.Test;

import scheme3.TreeGenerator;

public class TreeGeneratorTest {
	
	@Test
	public void test5FromSingle4() {
		Graph initial = new Graph("0:1,0:2,1:3");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 5);
	}
	
	@Test
	public void test6FromSingle4() {
		Graph initial = new Graph("0:1,0:2,1:3");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test6FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test7FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 7);
	}
	
	@Test
	public void test8FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 8);
	}
	
	@Test
	public void test9FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 9);
	}
	
	@Test
	public void test10FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 10);
	}
	
	@Test
	public void test11FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 11);
	}
	
	@Test
	public void test12FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 12);
	}
	
	@Test
	public void test13FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 13);
	}
	
	@Test
	public void test14FromSingleEdge() {
		Graph initial = new Graph("0:1");
		TreeGenerator generator = new TreeGenerator();
		generator.orderlyGenerationMcKay(initial, 14);
	}

}
