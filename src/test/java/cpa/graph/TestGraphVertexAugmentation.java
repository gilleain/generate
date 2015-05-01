package cpa.graph;

import graph.model.IntGraph;

import org.junit.Test;

import cpa.graph.GraphVertexAugmentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGraphVertexAugmentation {
    
    private void testPair(GraphVertexAugmentation toFail, GraphVertexAugmentation toPass) {
        assertFalse(toFail.getAugmentedObject() + " should NOT be canonical", toFail.isCanonical());
        assertTrue(toPass.getAugmentedObject() + " should be canonical", toPass.isCanonical());
    }
    
    @Test
    public void squareVsPawTest() {
        testPair(
                new GraphVertexAugmentation(new IntGraph("0:1,0:2,1:3,2:3"), 0, 1), // square
                new GraphVertexAugmentation(new IntGraph("0:1,0:2,0:3,1:2"), 1, 3)  // paw
        );
    }
    
    @Test
    public void fourLineVsPawTest() {
        testPair(
                new GraphVertexAugmentation(new IntGraph("0:1,0:2,1:3"), 0, 2),    // four line
                new GraphVertexAugmentation(new IntGraph("0:1,0:2,0:3,1:2"), 3)    // paw
        ); 
    }
}
