package test.scheme3;

import generate.handler.FamilyCountingHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphFileReader;

import org.junit.Test;

import scheme3.SimpleGraphGenerator;

public class SimpleGraphGeneratorTest {
	
	@Test
	public void test5FromSingle4() {
		Graph initial = new Graph("0:1,0:2,1:3");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 5);
	}
	
	@Test
	public void test6FromSingle4() {
		Graph initial = new Graph("0:1,0:2,1:3");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test5FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 5);
	}
	
	@Test
	public void test6FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 6);
	}
	
	@Test
	public void test6FromFives() throws FileNotFoundException {
		GraphFileReader reader = new GraphFileReader(new FileReader("output/scheme3/fives.txt"));
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		SimpleGraphGenerator generator = new SimpleGraphGenerator(new FamilyCountingHandler(counts));
//		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		for (Graph initial : reader) {
			generator.orderlyGenerationMcKay(initial, 6);
		}
		for (Graph graph : counts.keySet()) {
			System.out.println(counts.get(graph).size() + "\t" + graph);
		}
	}
	
	@Test
	public void test7FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 7);
	}
	
	@Test
	public void test7FromSixes() throws FileNotFoundException {
		GraphFileReader reader = new GraphFileReader(new FileReader("output/scheme3/sixes.txt"));
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		SimpleGraphGenerator generator = new SimpleGraphGenerator(new FamilyCountingHandler(counts));
		for (Graph initial : reader) {
			generator.orderlyGenerationMcKay(initial, 7);
		}
		for (Graph graph : counts.keySet()) {
			System.out.println(counts.get(graph).size() + "\t" + graph);
		}
	}
	
	@Test
	public void test7FromFives() throws FileNotFoundException {
		GraphFileReader reader = new GraphFileReader(new FileReader("output/scheme3/fives.txt"));
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		SimpleGraphGenerator generator = new SimpleGraphGenerator(new FamilyCountingHandler(counts));
		for (Graph initial : reader) {
			generator.orderlyGenerationMcKay(initial, 7);
		}
		for (Graph graph : counts.keySet()) {
			System.out.println(counts.get(graph).size() + "\t" + graph);
		}
	}
	
	@Test
	public void test8FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 8);
	}
	
	@Test
	public void test9FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 9);
	}
	
	@Test
	public void test10FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 10);
	}
	
	@Test
	public void test11FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 11);
	}
	
	@Test
	public void test12FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 12);
	}
	
	@Test
	public void test13FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 13);
	}
	
	@Test
	public void test14FromSingleEdge() {
		Graph initial = new Graph("0:1");
		SimpleGraphGenerator generator = new SimpleGraphGenerator();
		generator.orderlyGenerationMcKay(initial, 14);
	}

}
