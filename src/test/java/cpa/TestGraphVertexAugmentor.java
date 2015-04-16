package cpa;

import graph.model.IntGraph;

import org.junit.Test;

public class TestGraphVertexAugmentor {
    
    private class CounterPair {
        public int total;
        public int canon;
        public String toString() {
            return total + "\t" + canon;
        }
    }

    public void generateFrom(String parentString) {
        generateFrom(new CounterPair(), parentString);
    }
    
    public void generateFrom(CounterPair counter, String parentString) {
        System.out.println("Generating from " + parentString);
        IntGraph parent = new IntGraph(parentString);
        GraphVertexAugmentor augmentor = new GraphVertexAugmentor();
        for (Augmentation<IntGraph> child : augmentor.augment(new GraphVertexAugmentation(parent))) {
            boolean isCanonical = child.isCanonical();
            counter.canon += (isCanonical? 1 : 0);
            System.out.println(counter + " " + child.getAugmentedObject() + " " + isCanonical);
            counter.total++;
        }
    }
    
    @Test
    public void generateFrom3Line() {
        generateFrom("0:1,0:2");
    }
    
    @Test
    public void generateFrom3Cycle() {
        generateFrom("0:1,0:2,1:2");
    }
    
    @Test
    public void generateFromAllFours() {
        CounterPair c = new CounterPair();
        generateFrom(c, "0:1, 0:2, 0:3");
        generateFrom(c, "0:1, 0:2, 1:3");
        generateFrom(c, "0:1, 0:2, 1:3, 2:3");
        generateFrom(c, "0:1, 0:2, 1:2, 0:3");
        generateFrom(c, "0:1, 0:2, 1:2, 0:3, 1:3");
        generateFrom(c, "0:1, 0:2, 1:2, 0:3, 1:3, 2:3");
    }
}
