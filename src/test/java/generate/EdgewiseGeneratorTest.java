package generate;

import graph.model.Graph;
import graph.model.IntGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EdgewiseGeneratorTest {
    
    public Map<String, Integer> getAutMap(List<IntGraph> graphs) {
        Map<String, Integer> autMap = new HashMap<String, Integer>();
        for (IntGraph g : graphs) {
            String edgeString = g.getSortedEdgeString();
            if (autMap.containsKey(edgeString)) {
                autMap.put(edgeString, autMap.get(edgeString) + 1);
            } else {
                autMap.put(edgeString, 1);
            }
        }
        return autMap;
    }
    
    public void testForUniqueness(List<IntGraph> graphs) {
        Map<String, Integer> autMap = getAutMap(graphs);
        int index = 0;
        for (String edgeString : autMap.keySet()) {
            System.out.println(
                    index + " " + autMap.get(edgeString) + "x\t" + edgeString);
            index++;
        } 
    }
    
    public List<IntGraph> filter(List<IntGraph> graphs) {
        Map<String, Integer> autMap = getAutMap(graphs);
        List<IntGraph> uniq = new ArrayList<IntGraph>();
        for (String string : autMap.keySet()) {
            uniq.add(new IntGraph(string));
        }
        return uniq;
    }
    
    public void toFile(List<IntGraph> graphs, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            for (Graph graph : graphs) {
                writer.println(graph.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void eightRunUnique() {
        EdgewiseGenerator gen = new EdgewiseGenerator(9, 4);
        gen.generate();
        testForUniqueness(gen.graphs);
    }
    
    @Test
    public void fiveRunUnique() {
        EdgewiseGenerator gen = new EdgewiseGenerator(6, 4);
        gen.generate();
        testForUniqueness(gen.graphs);
    }
    
    @Test
    public void sevenRunUnique() {
        EdgewiseGenerator gen = new EdgewiseGenerator(8, 4);
        gen.generate();
        testForUniqueness(gen.graphs);
    }
    
    @Test
    public void sixRunUnique() {
        EdgewiseGenerator gen = new EdgewiseGenerator(7, 4);
        gen.generate();
        testForUniqueness(gen.graphs);
    }
    
    @Test
    public void fourRunUnique() {
        EdgewiseGenerator gen = new EdgewiseGenerator(5, 4);
        gen.generate();
        testForUniqueness(gen.graphs);
    }
    
    @Test
    public void fiveRunToFile() {
        EdgewiseGenerator gen = new EdgewiseGenerator(6, 4);
//        gen.generate(false);
//        toFile(filter(gen.graphs), new File("fives_filtered.txt"));
        
        gen.generate(true);
        toFile(gen.graphs, new File("fives.txt"));
    }
    
    @Test
    public void sixRunToFile() {
        EdgewiseGenerator gen = new EdgewiseGenerator(7, 4);
        gen.generate(true);
        toFile(filter(gen.graphs), new File("sixes.txt"));
//        gen.generate(false);
//        toFile(filter(gen.graphs), new File("sixes_filtered.txt"));
    }

    @Test
    public void sevenRunToFile() {
        EdgewiseGenerator gen = new EdgewiseGenerator(8, 4);
        gen.generate();
        toFile(gen.graphs, new File("sevens.txt"));
    }
    
    @Test
    public void eightRunToFile() {
        EdgewiseGenerator gen = new EdgewiseGenerator(9, 4);
        gen.generate();
        toFile(gen.graphs, new File("eights.txt"));
    }
    
    @Test
    public void nineRunToFile() {
        EdgewiseGenerator gen = new EdgewiseGenerator(10, 4);
        gen.generate();
        toFile(gen.graphs, new File("nines.txt"));
    }
    
    @Test
    public void testGraphRoundtrip() {
        IntGraph graph = new IntGraph("0:1,2:3");
        String gstring = graph.toString();
        IntGraph newGraph = new IntGraph(gstring);
        assertEquals(graph.getSortedEdgeString(), newGraph.getSortedEdgeString());
    }
    
    public static void main(String[] args) {
        new EdgewiseGeneratorTest().sixRunToFile();
    }

}
