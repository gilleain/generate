package test.scheme3;

import model.Graph;
import model.GraphSignature;

import org.junit.Assert;
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
    
    @Test
    public void canonicalStringForDisconnected() {
        Graph tandem = new Graph("0:1,2:3,3:4");
        Graph nested = new Graph("0:4,1:2,2:3");
        SimpleGraphGenerator gen = new SimpleGraphGenerator();
        String tandemStr = gen.getCanonicalLabel(new GraphSignature(tandem));
        String nestedStr = gen.getCanonicalLabel(new GraphSignature(nested));
        System.out.println(tandemStr + "\n" + nestedStr);
        Assert.assertEquals(tandemStr, nestedStr);
    }
}
