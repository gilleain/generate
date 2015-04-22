package util;

import graph.model.IntGraph;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestCutVertexCalculator {
    
    private void testGraph(String graphString, int... expectedVertices) {
        IntGraph graph = new IntGraph(graphString);
        List<Integer> cutVertices = CutVertexCalculator.getCutVertices(graph);
        for (int expectedVertex : expectedVertices) {
            assertTrue("Missing " + expectedVertex, cutVertices.contains(expectedVertex));
        }
    }
    
    @Test
    public void testSpiraFusedTriangles() {
        testGraph("0:1,0:2,0:3,0:4,1:2,3:4", 0);
    }
    
    @Test
    public void testDualStalkedSquare() {
        testGraph("0:1,0:3,1:2,1:4,2:3,2:5", 1, 2);
    }
    
    @Test
    public void testSingleBridgedTriangles() {
        testGraph("0:1,0:4,0:5,1:2,1:3,2:3,4:5", 0, 1);
    }
    
    @Test
    public void testTree() {
        testGraph("0:1,0:2,1:3,1:4,4:5", 0, 1, 4);
    }

}
