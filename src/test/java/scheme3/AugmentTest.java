package scheme3;

import graph.model.Graph;

import org.junit.Test;

import scheme3.signature.ConnectedEdgeSignatureHandler;

public class AugmentTest {
    
    @Test
    public void clawParents() {
        Graph triangle  = new Graph("0:1,0:2,1:2");
        Graph threeStar = new Graph("0:1,0:2,0:3");
        Graph fourLine  = new Graph("0:1,0:2,1:3");
        Graph claw      = new Graph("0:1,0:2,0:3,1:2");
        ConnectedEdgeSignatureHandler handler = new ConnectedEdgeSignatureHandler();
        boolean triangleToClaw = handler.isCanonicalAugmentation(triangle, claw);
        boolean threeStarToClaw = handler.isCanonicalAugmentation(threeStar, claw);
        boolean fourLineToClaw = handler.isCanonicalAugmentation(fourLine, claw);
        System.out.println("Triangle = " + triangleToClaw);
        System.out.println("Three Star = " + threeStarToClaw);
        System.out.println("Four Line = " + fourLineToClaw);
    }
    
    @Test
    public void canonicalStringForDisconnected() {
        // TODO : FIXME
//        Graph tandem = new Graph("0:1,2:3,3:4");
//        Graph nested = new Graph("0:4,1:2,2:3");
//        
//        DisconnectedEdgeSignatureHandler handler = new DisconnectedEdgeSignatureHandler();
//        
//        GraphSignature tandemSig = new GraphSignature(tandem);
//        GraphSignature nestedSig = new GraphSignature(nested);
//        
//        String tandemStr = handler.getCanonicalLabel(tandemSig);
//        String nestedStr = handler.getCanonicalLabel(nestedSig);
//        System.out.println(tandemStr + "\n" + nestedStr);
//        
//        int[] tandemLabels = handler.getLabels(tandemSig);
//        int[] nestedLabels = handler.getLabels(nestedSig);
//        System.out.println(Arrays.toString(tandemLabels) + "\n" + Arrays.toString(nestedLabels));
//        
//        Graph canonicalTandemForm = handler.getCanonicalForm(tandem);
//        Graph canonicalNestedForm = handler.getCanonicalForm(nested);
//        System.out.println(canonicalTandemForm + "\n" + canonicalNestedForm);
//        
//        Assert.assertEquals(tandemStr, nestedStr);
    }
}
