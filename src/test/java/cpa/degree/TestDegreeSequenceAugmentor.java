package cpa.degree;

import graph.model.IntGraph;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cpa.Augmentation;

public class TestDegreeSequenceAugmentor {
    
    private void print(List<Augmentation<ResidualDegreeGraphPair>> children) {
        int i = 0;
        for (Augmentation<ResidualDegreeGraphPair> child : children) {
            IntGraph obj = child.getAugmentedObject().getGraph();
            System.out.println(i + "\t" + obj + "\t" + Arrays.toString(obj.degreeSequence(true)));
            i++;
        }
    }
    
    @Test
    public void testFromEmpty() {
        int[] target = new int[] { 3, 3, 2, 2, 1, 1 };
        DegreeSequenceAugmentor augmentor = new DegreeSequenceAugmentor(target);
        
        Augmentation<ResidualDegreeGraphPair> parent = new DegreeSequenceAugmentation(target);
        List<Augmentation<ResidualDegreeGraphPair>> children = augmentor.augment(parent);
        assertEquals("Expected 10 children", 10, children.size());
        
        print(children);
    }
    
    @Test
    public void testFromOneTwoThree() {
        int[] target    = new int[] { 3, 3, 2, 2, 1, 1 };
        int[] residuals = new int[] { 0, 2, 1, 1, 1, 1 };
        
        DegreeSequenceAugmentor augmentor = new DegreeSequenceAugmentor(target);
        IntGraph parentGraph = new IntGraph("0:1,0:2,0:3");
        Augmentation<ResidualDegreeGraphPair> parent = 
                new DegreeSequenceAugmentation(parentGraph, residuals);
        List<Augmentation<ResidualDegreeGraphPair>> children = augmentor.augment(parent);
        
        print(children);
    }
    
    @Test
    public void testFromOneTwoFour() {
        int[] target    = new int[] { 3, 3, 2, 2, 1, 1 };
        int[] residuals = new int[] { 0, 2, 1, 2, 0, 1 };
        
        DegreeSequenceAugmentor augmentor = new DegreeSequenceAugmentor(target);
        IntGraph parentGraph = new IntGraph("0:1,0:2,0:4");
        Augmentation<ResidualDegreeGraphPair> parent = 
                new DegreeSequenceAugmentation(parentGraph, residuals);
        List<Augmentation<ResidualDegreeGraphPair>> children = augmentor.augment(parent);
        
        print(children);
    }

}
