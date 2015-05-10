package cpa.degree;

import graph.model.IntGraph;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cpa.Augmentation;
import cpa.handler.AugmentationHandler;
import cpa.handler.GenerationHandler;
import cpa.handler.IsomorphismHandler;
import cpa.handler.PrintstreamHandler;

public class TestDegreeSequenceGenerator {
    
    private void all(int[] sequence, int expected) {
        GenerationHandler handler = new PrintstreamHandler(System.out, true, true);
        AugmentationHandler<ResidualDegreeGraphPair> augmentationHandler = 
                new AugmentationHandler<ResidualDegreeGraphPair>() {

                    @Override
                    public void handleCanonical(Augmentation<ResidualDegreeGraphPair> augmentation) {
                        System.out.println("Canonical " + augmentation.getAugmentedObject());
                    }

                    @Override
                    public void handleNonCanonical(Augmentation<ResidualDegreeGraphPair> augmentation) {
                        System.out.println("Non Canonical " + augmentation.getAugmentedObject());
                    }
        };
        DegreeSequenceGenerator gen = new DegreeSequenceGenerator(handler, augmentationHandler, sequence);
        gen.generate();
    }
    
    private void iso(int[] sequence, int expected) {
        IsomorphismHandler handler = new IsomorphismHandler();
        DegreeSequenceGenerator gen = new DegreeSequenceGenerator(handler, sequence);
        gen.generate();
        Map<String, List<IntGraph>> m = handler.getMap();
        int counter = 1;
        for (String key : m.keySet()) {
            IntGraph first = m.get(key).get(0); 
            System.out.println(counter + "\t" + first + "\t" + m.get(key).size());
            counter++;
        }
        assertEquals("Connected non-isomorphic count incorrect", expected, counter - 1);
    }
    
    @Test
    public void test3_2_2_2_1() {
        all(new int[] { 3, 2, 2, 2, 1 }, 2);
    }
    
    @Test
    public void test3_2_2_1_1_1() {
        all(new int[] { 3, 2, 2, 1, 1, 1 }, 1);
    }
    
    @Test
    public void test5_4_4_3_2_2() {
        all(new int[] { 5, 4, 4, 3, 2, 2 }, 1);
    }
    
    @Test
    public void test3_3_2_2_1_1() {
        iso(new int[] { 3, 3, 2, 2, 1, 1 }, 4);
    }
    
    @Test
    public void test2_2_2_2_2_2() {
        iso(new int[] { 2, 2, 2, 2, 2, 2 }, 1);
    }
    
    @Test
    public void test2_2_2_1_1_1_1() {
        iso(new int[] { 2, 2, 2, 1, 1, 1, 1 }, 0);
    }
    
    @Test
    public void test3_3_2_1_1_1_1() {
        iso(new int[] { 3, 3, 2, 1, 1, 1, 1 }, 2);
    }
    
    @Test
    public void test3_3_3_2_2_2_1() {
        iso(new int[] { 3, 3, 3, 2, 2, 2, 1 }, 11);
    }
    
    @Test
    public void test3_3_3_3_2_2_2() {
        iso(new int[] { 3, 3, 3, 3, 2, 2, 2 }, 10);
    }
    
    @Test
    public void test5_4_4_3_3_3_2() {
        iso(new int[] { 5, 4, 4, 3, 3, 3, 2 }, 20);
    }
    
    

}
