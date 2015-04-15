package cpa;

import graph.model.IntGraph;

import org.junit.Test;

public class TestGraphVertexAugmentor {

    public void generateFrom(IntGraph parent) {
        GraphVertexAugmentor augmentor = new GraphVertexAugmentor();
        int index = 0;
        for (Augmentation<IntGraph> child : augmentor.augment(new GraphVertexAugmentation(parent))) {
            System.out.println(index + " " + child.getAugmentedObject() + " " + child.isCanonical());
            index++;
        }
    }
    
    @Test
    public void generateFrom3Line() {
        generateFrom(new IntGraph("0:1,0:2"));
    }
    
    @Test
    public void generateFrom3Cycle() {
        generateFrom(new IntGraph("0:1,0:2,1:2"));
    }
}
