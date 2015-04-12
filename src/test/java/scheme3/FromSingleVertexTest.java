package scheme3;

import generate.handler.FileOutputHandler;
import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import graph.model.IntGraph;

import org.junit.Assert;
import org.junit.Test;

public class FromSingleVertexTest {
    
    public void testFromSingle(
            int n, int expected, boolean byVertex, boolean generateDisconnected, boolean doFilter) {
        SystemOutHandler handler = new SystemOutHandler();
        testFromSingle(handler, n, byVertex, generateDisconnected, doFilter);
        Assert.assertEquals(expected, handler.getCount());
    }
    
    public void testFromSingle(
            GeneratorHandler handler, int n, boolean byVertex, boolean generateDisconnected, boolean doFilter) {
        IntGraph initial = new IntGraph("0:1");
        GraphGenerator generator = new GraphGenerator(handler, byVertex, generateDisconnected, doFilter);
        generator.extend(initial, n);
    }
    
    public void testByVertexConnectedFrom(IntGraph initial, int n) {
        SystemOutHandler handler = new SystemOutHandler();
        GraphGenerator generator = new GraphGenerator(handler, true, false);
        generator.extend(initial, n);
    }
    
    public void testToFile(String outputFilepath, int n) {
        testToFile(outputFilepath, n, true, false, true);
    }
    
    public void testToFile(
            String outputFilepath, int n, boolean byVertex, boolean generateDisconnected, boolean doFilter) {
        FileOutputHandler handler = new FileOutputHandler(outputFilepath, n);
        GraphGenerator generator = new GraphGenerator(handler, byVertex, generateDisconnected, doFilter);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
    }
    
    @Test
    public void byVConn3Line() {
        testByVertexConnectedFrom(new IntGraph("0:1,0:2,1:3"), 5);
    }
    
    @Test
    public void test4FromSingleEdgeConn() {
        testFromSingle(4, 6, true, false, true);
    }

    @Test
    public void test5FromSingleEdgeConn() {
        testFromSingle(5, 21, true, false, true);
    }
    
    @Test
    public void test6FromSingleEdgeConn() {
        testFromSingle(6, 112, true, false, true);
    }

    @Test
    public void test7FromSingleEdgeConn() {
        testFromSingle(7, 853, true, false, true);
    }

    @Test
    public void test8FromSingleEdgeConn() {
        testFromSingle(8, 11117, true, false, true);
    }
    
    @Test
    public void test4FromSingleEdgeConnSym() {
        testFromSingle(4, 6, true, false, false);
    }
    
    @Test
    public void test5FromSingleEdgeConnSym() {
        testFromSingle(5, 21, true, false, false);
    }
    
    @Test
    public void test6FromSingleEdgeConnSym() {
        testFromSingle(6, 112, true, false, false);
    }
    
    @Test
    public void test7FromSingleEdgeConnSym() {
        testFromSingle(7, 853, true, false, false);
    }
    
    @Test
    public void test5FromSingleEdgeConnSymToFile() {
        testToFile("output/scheme3/fives_v_conn_sym.txt", 5, true, false, false);
    }
    
    @Test
    public void test4FromSingleEdgeDisc() {
        testFromSingle(4, 6, true, true, true);
    }

    @Test
    public void test5FromSingleEdgeDisc() {
        testFromSingle(5, 21, true, true, true);
    }
    
    @Test
    public void test6FromSingleEdgeDisc() {
        testFromSingle(6, 112, true, true, true);
    }

    @Test
    public void test7FromSingleEdgeDisc() {
        testFromSingle(7, 853, true, true, true);
    }

    @Test
    public void test8FromSingleEdgeDisc() {
        testFromSingle(8, 11117, true, true, true);
    }
    
    @Test
    public void test8ToFile() {
        testToFile("output/scheme3/eights_sym.txt", 8);
    }
}
    
