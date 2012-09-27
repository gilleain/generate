package test.scheme3;

import generate.handler.FamilyCountingHandler;
import generate.handler.FileOutputHandler;
import generate.handler.GeneratorHandler;
import generate.handler.IsomorphCountingHandler;
import generate.handler.SystemOutHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphFileReader;

import org.junit.Test;

import scheme3.SimpleGraphGenerator;

public class SimpleGraphGeneratorTest {
    
    
    public void testFromSingle(Graph initial, int n) {
        SimpleGraphGenerator generator = new SimpleGraphGenerator();
        generator.extend(initial, n);
    }
    
    public void testFromSingleToFile(int n, String path) {
        FileOutputHandler handler = new FileOutputHandler(path, n);
        testFromSingle(handler, n);
        handler.finish();
    }
    
    public void testFromFileToFile(String inputFilepath, String outputFilepath, int n) throws FileNotFoundException {
        FileOutputHandler handler = new FileOutputHandler(outputFilepath, n);
        testFromFile(handler, inputFilepath, n);
        handler.finish();
    }
    
    public void testFromFile(GeneratorHandler handler, String filepath, int n) throws FileNotFoundException {
        GraphFileReader reader = new GraphFileReader(new FileReader(filepath));
        SimpleGraphGenerator generator = new SimpleGraphGenerator(handler);
        for (Graph g : reader) {
            Graph h = generator.getCanonicalForm(g);
            generator.extend(h, n);
        }
    }
    
    public void testFromSingle(GeneratorHandler handler, int n) {
        Graph initial = new Graph("0:1");
        SimpleGraphGenerator generator = new SimpleGraphGenerator(handler);
        generator.extend(initial, n);
    }
    
    public void printIsomorphCounts(IsomorphCountingHandler handler) {
        Map<Graph, Integer> map = handler.getNonIsomorphicGraphCount(); 
        int i = 0;
        for (Graph g : map.keySet()) {
            String gS = g.getSortedEdgeString();
            String gDetails = g.vsize() + "\t" + g.esize() + "\t" + Arrays.toString(g.degreeSequence(true));
            System.out.println(i + "\t" + gDetails + "\t" + map.get(g) + "\t" + gS);
            i++;
        }
    }
   
    @Test
    public void test4FromThree() throws FileNotFoundException {
        testFromSingle(new Graph("0:1,0:2"), 4);
        testFromSingle(new Graph("0:1,0:2,1:2"), 4);
    }

    @Test
    public void test4FromSingleEdgeToFile() {
        testFromSingleToFile(4, "output/scheme3/fours.txt");
    }
	
	@Test
	public void test5FromSingle4() {
		testFromSingle(new Graph("0:1,0:2,1:3"), 5);
	}
	
	@Test
	public void test6FromSingle4() {
	    testFromSingle(new Graph("0:1,0:2,1:3"), 6);
	}
	
	@Test
    public void test5FromNautyFour() throws FileNotFoundException {
	    IsomorphCountingHandler handler = new IsomorphCountingHandler();
        testFromFile(handler, "output/nauty/fours_nauty.txt", 5);
        printIsomorphCounts(handler);
    }
	
	@Test
    public void test6FromSingle5() {
	    testFromSingle(new Graph("0:1,0:2,0:3,0:4,1:2,1:3"), 6);
    }
	
	
	@Test
    public void test5FromSingleEdgeToFile() {
        testFromSingleToFile(5, "output/scheme3/fives.txt");
    }
	
	@Test
    public void test6FromNautyFive() throws FileNotFoundException {
        testFromFile(new SystemOutHandler(), "output/nauty/fives_nauty.txt", 6);
    }
	
	@Test
    public void test6FromNautyFiveToFile() throws FileNotFoundException {
        testFromFileToFile("output/nauty/fives_nauty.txt", "output/scheme3/nFivesToSixes.txt", 6);
    }
	
	@Test
    public void test6FromNautyFiveCount() throws FileNotFoundException {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
//        testFromFile(handler, "output/nauty/fives_nauty.txt", 6);
        testFromFile(handler, "output/scheme3/fours.txt", 6);
        printIsomorphCounts(handler);
    }
	
	
	
	@Test
    public void test6FromSingleEdgeToFile() {
        testFromSingleToFile(6, "output/scheme3/sixesFromE.txt");
    }
	
	
	@Test
	public void test6FromFives() throws FileNotFoundException {
		GraphFileReader reader = new GraphFileReader(new FileReader("output/scheme3/fives.txt"));
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		SimpleGraphGenerator generator = new SimpleGraphGenerator(new FamilyCountingHandler(counts));
		for (Graph initial : reader) {
			generator.extend(initial, 6);
		}
		for (Graph graph : counts.keySet()) {
			System.out.println(counts.get(graph).size() + "\t" + graph);
		}
	}
	
	@Test
	public void test7FromSixes() throws FileNotFoundException {
		GraphFileReader reader = new GraphFileReader(new FileReader("output/scheme3/sixes.txt"));
		final Map<Graph, List<Graph>> counts = new HashMap<Graph, List<Graph>>();
		SimpleGraphGenerator generator = new SimpleGraphGenerator(new FamilyCountingHandler(counts));
		for (Graph initial : reader) {
			generator.extend(initial, 7);
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
			generator.extend(initial, 7);
		}
		for (Graph graph : counts.keySet()) {
			System.out.println(counts.get(graph).size() + "\t" + graph);
		}
	}
}
