package scheme3;

import generate.handler.TimingHandler;
import graph.model.IntGraph;

import org.junit.Test;

public class TimingTests {
    
    public void testVertexSymmetryTime(int n) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, true, false, false);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
        System.out.println("Done V/Sym/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testVertexFilteringTime(int n) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, true, false, true);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
        System.out.println("Done V/Fil/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeSymmetryTime(int n) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, false, false);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Sym/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeFilteringTime(int n) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, false, true);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Fil/Conn " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testEdgeFilteringDiscTime(int n) {
        TimingHandler handler = new TimingHandler();
        GraphGenerator generator = new GraphGenerator(handler, false, true, true);
        generator.extend(new IntGraph("0:1"), n);
        handler.finish();
        System.out.println("Done E/Fil/Disc " + handler.getElapsedTime() + "ms " + handler.getCount());
    }
    
    public void testAll(int n) {
//        testEdgeFilteringTime(n);
//        testEdgeSymmetryTime(n);
        testVertexFilteringTime(n);
//        testVertexSymmetryTime(n);
//        testEdgeFilteringDiscTime(n);
    }
    
    @Test
    public void testFours() {
        testAll(4);
    }
    
    @Test
    public void testFives() {
        testAll(5);
    }
    
    @Test
    public void testSixes() {
        testAll(6);
    }
    
    @Test
    public void testSevens() {
        testAll(7);
    }
    
    @Test
    public void testEights() {
        testAll(8);
    }
    
}
