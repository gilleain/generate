package augment;

import generate.handler.DegreeFilterHandler;
import generate.handler.FamilyCountingHandler;
import generate.handler.FileOutputHandler;
import generate.handler.GeneratorHandler;
import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import graph.model.GraphFileReader;
import group.Permutation;
import group.PermutationGroup;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.junit.Test;

import augmentation.AugmentingGenerator;


public class ScanTest {
	
	@Test
	public void fourGraphsFromEmpty() {
		Graph initial = new Graph();
		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(4, 4));
//		AugmentingGenerator generator = new AugmentingGenerator(new FileOutputHandler("output/mckay/four_x.txt", 4));
		generator.scan(initial, 4);
	}
	
	@Test
	public void fourGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(4, 4));
//		AugmentingGenerator generator = new AugmentingGenerator(new FileOutputHandler("output/mckay/four_x.txt", 4));
		generator.scan(initial, 4);
	}
	
	@Test
	public void fiveGraphsFromEmpty() {
		Graph initial = new Graph();
		AugmentingGenerator generator = new AugmentingGenerator(new DegreeFilterHandler(5, 5, true));
//		AugmentingGenerator generator = new AugmentingGenerator(new FileOutputHandler("output/mckay/five_x.txt", 5));
		generator.scan(initial, 5);
	}
	
	@Test
    public void sevenGraphsFromEmpty() {
        Graph initial = new Graph();
//        GeneratorHandler handler = new FileOutputHandler("output/mckay/seven_x.txt", -1, 7, true);
        GeneratorHandler handler = new FileOutputHandler("output/mckay/seven_four.txt", 4, 7, true);
        AugmentingGenerator generator = new AugmentingGenerator(handler);
        generator.scan(initial, 7);
    }
	
	@Test
	public void fiveGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
		AugmentingGenerator generator = new AugmentingGenerator(new DegreeFilterHandler(5, 5));
//		AugmentingGenerator generator = new AugmentingGenerator(new FileOutputHandler("output/mckay/five_x.txt", 5));
		generator.scan(initial, 5);
	}
	
	@Test
	public void fiveGraphsFromFours() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(4, new FamilyCountingHandler(counts));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/four_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 5);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void sixGraphsFromFives() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(6, new FamilyCountingHandler(counts, true));
//		AugmentingGenerator generator = new AugmentingGenerator(new SystemOutHandler(6, true));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/five_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 6);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void sevenGraphsFromFives_degreeAll() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(7, new FamilyCountingHandler(counts, true));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/five_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 7);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void sevenGraphsFromSixes_degreeAll() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(7, new FamilyCountingHandler(counts, true));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/six_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 7);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void eightGraphsFromFives_degreeAll() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(7, new FamilyCountingHandler(counts, true));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/five_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 8);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void nineGraphsFromEights() throws IOException {
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		
		AugmentingGenerator generator = new AugmentingGenerator(4, new FamilyCountingHandler(counts));
		GraphFileReader reader = new GraphFileReader(new FileReader("output/mckay/eight_x.txt"));
		for (Graph graph : reader) {
			generator.scan(graph, 9);
		}
		for (Graph parent : counts.keySet()) {
			System.out.println(counts.get(parent).size() + "\t" + parent);
		}
	}
	
	@Test
	public void sixGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
//		AugmentingGenerator generator = new AugmentingGenerator(4, new FileOutputHandler("output/mckay/six_4.txt", 4, 6, true));
		AugmentingGenerator generator = new AugmentingGenerator(6, new FileOutputHandler("output/mckay/six_x.txt", 6, 6, true));
		generator.scan(initial, 6);
	}
	
	@Test
	public void sevenGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
		AugmentingGenerator generator = new AugmentingGenerator(3, new DegreeFilterHandler(7, 7));
		generator.scan(initial, 7);
	}
	
	@Test
	public void eightGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
//		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(4, 8));
		AugmentingGenerator generator = new AugmentingGenerator(4, new FileOutputHandler("output/mckay/eight_x.txt", 4, 8));
		generator.scan(initial, 8);
	}
	
	@Test
	public void nineGraphsFromAnEight() throws IOException {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		Graph parent = new Graph("0:1,0:2,0:3,1:2,3:4,4:5,5:6,6:7");
		refiner.getAutomorphismGroup(parent);
		System.out.println(parent.getPermutedGraph(refiner.getBest().invert()).getSortedEdgeString());
		
		AugmentingGenerator generator = new AugmentingGenerator(new DegreeFilterHandler(9, 9));
		generator.scan(parent, 9);
		Graph child = new Graph("0:1,0:2,0:3,1:2,3:4,4:5,5:6,6:7,6:8,7:8");
		PermutationGroup autG = refiner.getAutomorphismGroup(child);
		System.out.println(child.getPermutedGraph(refiner.getBest().invert()).getSortedEdgeString());
		Permutation minPerm = refiner.getBest();
		SortedSet<Integer> orbit = generator.getOrbit(minPerm.get(8), autG);
		System.out.println(orbit);
	}
	
	@Test
	public void nineGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
//		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(9, 9, true));
//		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(9, 9, false));
		AugmentingGenerator generator = new AugmentingGenerator(4, new FileOutputHandler("output/mckay/nine_4_v2.txt", 4, 9, true));
		generator.scan(initial, 9);
	}
	
	@Test
	public void tenGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
//		AugmentingGenerator generator = new AugmentingGenerator(4, new DegreeFilterHandler(4, 10, true));
		AugmentingGenerator generator = new AugmentingGenerator(4, new FileOutputHandler("output/mckay/ten_3.txt", 4, 10));
		generator.scan(initial, 10);
	}
	
	@Test
	public void twelveGraphsFromSingleEdge() {
		Graph initial = new Graph("0:1");
//		AugmentingGenerator generator = new AugmentingGenerator(3, new DegreeFilterHandler(10, 10));
		AugmentingGenerator generator = new AugmentingGenerator(3, new FileOutputHandler("output/mckay/twelve_3.txt", 3, 12, true));
		generator.scan(initial, 12);
	}

}
