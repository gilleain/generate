package test.scheme3;

import java.util.Arrays;

import model.Graph;
import model.GraphSignature;

import org.junit.Assert;
import org.junit.Test;

import scheme3.ConnectedGraphSignatureHandler;
import scheme3.DisconnectedGraphSignatureHandler;

public class AugmentTest {
    
    @Test
    public void clawParents() {
        Graph triangle  = new Graph("0:1,0:2,1:2");
        Graph threeStar = new Graph("0:1,0:2,0:3");
        Graph fourLine  = new Graph("0:1,0:2,1:3");
        Graph claw      = new Graph("0:1,0:2,0:3,1:2");
        ConnectedGraphSignatureHandler handler = new ConnectedGraphSignatureHandler();
        boolean triangleToClaw = handler.isCanonicalAugmentation(triangle, claw);
        boolean threeStarToClaw = handler.isCanonicalAugmentation(threeStar, claw);
        boolean fourLineToClaw = handler.isCanonicalAugmentation(fourLine, claw);
        System.out.println("Triangle = " + triangleToClaw);
        System.out.println("Three Star = " + threeStarToClaw);
        System.out.println("Four Line = " + fourLineToClaw);
    }
    
    @Test
    public void canonicalStringForDisconnected() {
        Graph tandem = new Graph("0:1,2:3,3:4");
        Graph nested = new Graph("0:4,1:2,2:3");
        
        DisconnectedGraphSignatureHandler handler = new DisconnectedGraphSignatureHandler();
        
        GraphSignature tandemSig = new GraphSignature(tandem);
        GraphSignature nestedSig = new GraphSignature(nested);
        
        String tandemStr = handler.getCanonicalLabel(tandemSig);
        String nestedStr = handler.getCanonicalLabel(nestedSig);
        System.out.println(tandemStr + "\n" + nestedStr);
        
        int[] tandemLabels = handler.getLabels(tandemSig);
        int[] nestedLabels = handler.getLabels(nestedSig);
        System.out.println(Arrays.toString(tandemLabels) + "\n" + Arrays.toString(nestedLabels));
        
        Graph canonicalTandemForm = handler.getCanonicalForm(tandem);
        Graph canonicalNestedForm = handler.getCanonicalForm(nested);
        System.out.println(canonicalTandemForm + "\n" + canonicalNestedForm);
        
        Assert.assertEquals(tandemStr, nestedStr);
    }
}
