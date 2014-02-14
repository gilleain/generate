package scheme3;

import generate.handler.TimingHandler;
import graph.model.Graph;

import org.junit.Test;

import scheme3.GraphGenerator;

public class DegreeLimitedTimingTests {
    
    public void testVertexSymmetryTime(int n, int degMax) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, true, false, false, degMax);
        generator.extend(new Graph("0:1"), n);
        handler.finish();
        System.out.println("Done V/Sym/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testVertexFilteringTime(int n, int degMax) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, true, false, true, degMax);
        generator.extend(new Graph("0:1"), n);
        handler.finish();
        System.out.println("Done V/Fil/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeSymmetryTime(int n, int degMax) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, false, false, degMax);
        generator.extend(new Graph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Sym/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeFilteringTime(int n, int degMax) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, false, true, degMax);
        generator.extend(new Graph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Fil/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeFilteringDiscTime(int n, int degMax) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, true, true, degMax);
        generator.extend(new Graph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Fil/Disc " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testAllSingleRep(int n, int degMax) {
        testEdgeFilteringTime(n, degMax);
        testEdgeSymmetryTime(n, degMax);
        testVertexFilteringTime(n, degMax);
        testVertexSymmetryTime(n, degMax);
        testEdgeFilteringDiscTime(n, degMax);
    }
    
    public void testAll(int n, int degMax) {
        testAllSingleRep(n, degMax);
        System.out.println("--------------");
        testAllSingleRep(n, degMax);
        System.out.println("--------------");
        testAllSingleRep(n, degMax);
    }
    
    @Test
    public void testFours() {
        testAll(4, 4);
    }
    
    @Test
    public void testFives() {
        testAll(5, 4);
    }
    
    @Test
    public void testSixes() {
        testAll(6, 4);
    }
    
    @Test
    public void testSevens() {
        testAll(7, 4);
    }
    
    @Test
    public void testEights() {
        testAll(8, 4);
    }
    
    @Test
    public void testNines() {
        testAll(9, 4);
    }
    
}
