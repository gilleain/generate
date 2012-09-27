package test.scheme3;

import model.Graph;

import org.junit.Test;

import scheme3.SimpleGraphGenerator;

public class AugmentTest {
    
    @Test
    public void clawParents() {
        Graph triangle  = new Graph("0:1,0:2,1:2");
        Graph threeStar = new Graph("0:1,0:2,0:3");
        Graph fourLine  = new Graph("0:1,0:2,1:3");
        Graph claw      = new Graph("0:1,0:2,0:3,1:2");
        SimpleGraphGenerator gen = new SimpleGraphGenerator();
        boolean triangleToClaw = gen.isCanonicalAugmentation(triangle, claw);
        boolean threeStarToClaw = gen.isCanonicalAugmentation(threeStar, claw);
        boolean fourLineToClaw = gen.isCanonicalAugmentation(fourLine, claw);
        System.out.println("Triangle = " + triangleToClaw);
        System.out.println("Three Star = " + threeStarToClaw);
        System.out.println("Four Line = " + fourLineToClaw);
    }
}
