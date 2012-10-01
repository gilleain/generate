package test.scheme3;

import org.junit.Test;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import junit.framework.Assert;
import model.Graph;
import scheme3.GraphGenerator;

public class FromSingleVertexTest {
    
    public void testFromSingle(int n, int expected, boolean byVertex, boolean generateDisconnected) {
        SystemOutHandler handler = new SystemOutHandler(); 
        testFromSingle(handler, n, byVertex, generateDisconnected);
        Assert.assertEquals(expected, handler.getCount());
    }
    
    public void testFromSingle(
            GeneratorHandler handler, int n, boolean byVertex, boolean generateDisconnected) {
        Graph initial = new Graph("0:1");
        GraphGenerator generator = new GraphGenerator(handler, byVertex, generateDisconnected);
        generator.extend(initial, n);
    }
    
    @Test
    public void test4FromSingleEdgeConn() {
        testFromSingle(4, 6, true, false);
    }

    @Test
    public void test5FromSingleEdgeConn() {
        testFromSingle(5, 21, true, false);
    }
    
    @Test
    public void test6FromSingleEdgeConn() {
        testFromSingle(6, 112, true, false);
    }

    @Test
    public void test7FromSingleEdgeConn() {
        testFromSingle(7, 853, true, false);
    }

    @Test
    public void test8FromSingleEdgeConn() {
        testFromSingle(8, 11117, true, false);
    }
    
    @Test
    public void test4FromSingleEdgeDisc() {
        testFromSingle(4, 6, true, true);
    }

    @Test
    public void test5FromSingleEdgeDisc() {
        testFromSingle(5, 21, true, true);
    }
    
    @Test
    public void test6FromSingleEdgeDisc() {
        testFromSingle(6, 112, true, true);
    }

    @Test
    public void test7FromSingleEdgeDisc() {
        testFromSingle(7, 853, true, true);
    }

    @Test
    public void test8FromSingleEdgeDisc() {
        testFromSingle(8, 11117, true, true);
    }
}
    
