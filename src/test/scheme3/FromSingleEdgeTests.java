package test.scheme3;

import java.util.Arrays;
import java.util.Map;

import generate.handler.GeneratorHandler;
import generate.handler.IsomorphCountingHandler;
import generate.handler.SystemOutHandler;

import model.Graph;

import org.junit.Test;

import scheme3.SimpleGraphGenerator;

public class FromSingleEdgeTests {
    
    public void testFromSingle(int n) {
        testFromSingle(new SystemOutHandler(), n);
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
    public void test5FromSingleEdgeCount() {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        testFromSingle(handler, 5);
        printIsomorphCounts(handler);
    }
    

    @Test
    public void test6FromSingleEdgeCount() {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        testFromSingle(handler, 6);
        printIsomorphCounts(handler);
    }
    
    @Test
    public void test4FromSingleEdge() {
        testFromSingle(4);
    }

    @Test
    public void test5FromSingleEdge() {
        testFromSingle(5);
    }
    
    @Test
    public void test6FromSingleEdge() {
        testFromSingle(6);
    }

    @Test
    public void test7FromSingleEdge() {
        testFromSingle(7);
    }

    @Test
    public void test8FromSingleEdge() {
        testFromSingle(8);
    }
    
    @Test
    public void test9FromSingleEdge() {
        testFromSingle(9);
    }
    
    @Test
    public void test10FromSingleEdge() {
        testFromSingle(10);
    }
    
    @Test
    public void test11FromSingleEdge() {
        testFromSingle(11);
    }
    
    @Test
    public void test12FromSingleEdge() {
        testFromSingle(12);
    }
    
    @Test
    public void test13FromSingleEdge() {
        testFromSingle(13);
    }
    
    @Test
    public void test14FromSingleEdge() {
        testFromSingle(14);
    }
}
