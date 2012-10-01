package test.scheme3;

import java.util.Arrays;
import java.util.Map;

import junit.framework.Assert;

import generate.handler.FileOutputHandler;
import generate.handler.GeneratorHandler;
import generate.handler.IsomorphCountingHandler;
import generate.handler.SystemOutHandler;

import model.Graph;

import org.junit.Test;

import scheme3.GraphEdgewiseGenerator;

public class FromSingleEdgeTests {
    
    public void testFromSingle(int n, int expected, boolean generateDisconnected) {
        SystemOutHandler handler = new SystemOutHandler(); 
        testFromSingle(handler, n, generateDisconnected);
        Assert.assertEquals(expected, handler.getCount());
    }
    
    public void testFromSingle(GeneratorHandler handler, int n, boolean generateDisconnected) {
        Graph initial = new Graph("0:1");
        GraphEdgewiseGenerator generator = new GraphEdgewiseGenerator(handler);
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
    
    public void testFromSingleToFile(int n, String path) {
        FileOutputHandler handler = new FileOutputHandler(path, n);
        testFromSingle(handler, n, false);
        handler.finish();
    }
    
    @Test
    public void test4FromSingleEdgeToFile() {
        testFromSingleToFile(4, "output/scheme3/fours.txt");
    }

    @Test
    public void test5FromSingleEdgeToFile() {
        testFromSingleToFile(5, "output/scheme3/fives.txt");
    }
    
    @Test
    public void test6FromSingleEdgeToFile() {
        testFromSingleToFile(6, "output/scheme3/sixes.txt");
    }

    @Test
    public void test7FromSingleEdgeToFile() {
        testFromSingleToFile(7, "output/scheme3/sevens.txt");
    }
    
    @Test
    public void test5FromSingleEdgeCount() {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        testFromSingle(handler, 5, false);
        printIsomorphCounts(handler);
    }
    

    @Test
    public void test6FromSingleEdgeCount() {
        IsomorphCountingHandler handler = new IsomorphCountingHandler();
        testFromSingle(handler, 6, false);
        printIsomorphCounts(handler);
    }
    
    @Test
    public void test4FromSingleEdgeConn() {
        testFromSingle(4, 6, false);
    }

    @Test
    public void test5FromSingleEdgeConn() {
        testFromSingle(5, 21, false);
    }
    
    @Test
    public void test6FromSingleEdgeConn() {
        testFromSingle(6, 112, false);
    }

    @Test
    public void test7FromSingleEdgeConn() {
        testFromSingle(7, 853, false);
    }

    @Test
    public void test8FromSingleEdgeConn() {
        testFromSingle(8, 11117, false);
    }
    
    @Test
    public void test4FromSingleEdgeDisc() {
        testFromSingle(4, 6, true);
    }

    @Test
    public void test5FromSingleEdgeDisc() {
        testFromSingle(5, 21, true);
    }
    
    @Test
    public void test6FromSingleEdgeDisc() {
        testFromSingle(6, 112, true);
    }

    @Test
    public void test7FromSingleEdgeDisc() {
        testFromSingle(7, 853, true);
    }

    @Test
    public void test8FromSingleEdgeDisc() {
        testFromSingle(8, 11117, true);
    }
}
