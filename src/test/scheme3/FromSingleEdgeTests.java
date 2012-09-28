package test.scheme3;

import java.util.Arrays;
import java.util.Map;

import junit.framework.Assert;

import generate.handler.GeneratorHandler;
import generate.handler.IsomorphCountingHandler;
import generate.handler.SystemOutHandler;

import model.Graph;

import org.junit.Test;

import scheme3.SimpleGraphGenerator;

public class FromSingleEdgeTests {
    
    public void testFromSingle(int n, int expected) {
        SystemOutHandler handler = new SystemOutHandler(); 
        testFromSingle(handler, n);
        Assert.assertEquals(expected, handler.getCount());
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
        testFromSingle(4, 6);
    }

    @Test
    public void test5FromSingleEdge() {
        testFromSingle(5, 21);
    }
    
    @Test
    public void test6FromSingleEdge() {
        testFromSingle(6, 112);
    }

    @Test
    public void test7FromSingleEdge() {
        testFromSingle(7, 853);
    }

    @Test
    public void test8FromSingleEdge() {
        testFromSingle(8, 11117);
    }
}
