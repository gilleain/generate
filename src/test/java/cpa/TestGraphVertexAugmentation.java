package cpa;

import graph.model.IntGraph;

import org.junit.Test;

public class TestGraphVertexAugmentation {
    
    @Test
    public void convergenceTest() {
        IntGraph g1 = new IntGraph("0:1,0:2,1:3,2:3");
        GraphVertexAugmentation a1 = new GraphVertexAugmentation(g1, 0, 1);
        
        IntGraph g2 = new IntGraph("0:1,0:2,0:3,1:2");
        GraphVertexAugmentation a2 = new GraphVertexAugmentation(g2, 1, 3);
        
        System.out.println(a1.getAugmentedObject() + " " + a1.isCanonical());
        System.out.println(a2.getAugmentedObject() + " " + a2.isCanonical());
    }
}
