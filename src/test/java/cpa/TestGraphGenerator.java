package cpa;

import graph.model.IntGraph;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestGraphGenerator {
    
    private void testN(int n) {
        long start = System.nanoTime();
//        GenerationHandler handler = new PrintstreamHandler(System.out, true); 
        CountingHandler handler = new CountingHandler();
        GraphGenerator generator = new GraphGenerator(handler, n);
        generator.generate();
        long end = System.nanoTime();
        long t = (end - start) / 1000000L;  // time in ms
//        System.out.println("T = " + t);
        System.out.println(handler.getCount() + " in " + t + " ms ");
    }
    
    private void testG(int n, String gString) {
        IsomorphismHandler handler = new IsomorphismHandler();
        GraphGenerator generator = new GraphGenerator(handler, n);
        generator.generateFrom(new IntGraph(gString));
        Map<String, List<IntGraph>> map  = handler.getMap();
        for (String key : map.keySet()) {
            if (map.get(key).size() > 1) {
                System.out.println("- " + key);
                for (IntGraph h : map.get(key)) {
                    System.out.println("\t- " + h);
                }
            }
        }
    }
    
    @Test
    public void testFours() {
        testN(4);
    }
    
    @Test
    public void testFives() {
        testN(5);
    }
    
    @Test
    public void testSizes() {
        testN(6);
    }
    
    @Test
    public void testSevens() {
        testN(7);
    }
    
    @Test
    public void testEightFromAThree() {
        testG(8, "0:1, 0:2");
    }
    
    @Test
    public void testEightFromASeven() {
        testG(8, "0:1, 0:2, 1:2, 0:3, 1:3, 2:3, 0:4, 1:4, 2:4, 3:4, 0:5, 1:5, 2:5, 3:5, 4:5, 0:6");
    }
    
    @Test
    public void testEights() {
        testN(8);
    }
    
    @Test
    public void testNines() {
        testN(9);
    }

}
