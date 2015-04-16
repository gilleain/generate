package cpa;

import graph.model.IntGraph;

import org.junit.Test;

public class TestGraphVertexAugmentor {

    public int generateFrom(String parentString) {
        return generateFrom(0, parentString);
    }
    
    
    public int generateFrom(int counterStart, String parentString) {
        System.out.println("Generating from " + parentString);
        IntGraph parent = new IntGraph(parentString);
        GraphVertexAugmentor augmentor = new GraphVertexAugmentor();
        int index = counterStart;
        for (Augmentation<IntGraph> child : augmentor.augment(new GraphVertexAugmentation(parent))) {
            System.out.println(index + " " + child.getAugmentedObject() + " " + child.isCanonical());
            index++;
        }
        return index;
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
        int i = 0;
        i = generateFrom(i, "0:1, 0:2, 0:3");
        i = generateFrom(i, "0:1, 0:2, 1:3");
        i = generateFrom(i, "0:1, 0:2, 1:3, 2:3");
        i = generateFrom(i, "0:1, 0:2, 1:2, 0:3");
        i = generateFrom(i, "0:1, 0:2, 1:2, 0:3, 1:3");
        i = generateFrom(i, "0:1, 0:2, 1:2, 0:3, 1:3, 2:3");
    }
}
