package scheme3;

import graph.model.IntGraph;

import org.junit.Test;

import scheme3.signature.ConnectedEdgeSignatureHandler;

public class AugmentTest {
    
    @Test
    public void clawParents() {
        IntGraph triangle  = new IntGraph("0:1,0:2,1:2");
        IntGraph threeStar = new IntGraph("0:1,0:2,0:3");
        IntGraph fourLine  = new IntGraph("0:1,0:2,1:3");
        IntGraph claw      = new IntGraph("0:1,0:2,0:3,1:2");
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
//        IntGraph tandem = new IntGraph("0:1,2:3,3:4");
//        IntGraph nested = new IntGraph("0:4,1:2,2:3");
//        
//        DisconnectedEdgeSignatureHandler handler = new DisconnectedEdgeSignatureHandler();
//        
//        IntGraphSignature tandemSig = new IntGraphSignature(tandem);
//        IntGraphSignature nestedSig = new IntGraphSignature(nested);
//        
//        String tandemStr = handler.getCanonicalLabel(tandemSig);
//        String nestedStr = handler.getCanonicalLabel(nestedSig);
//        System.out.println(tandemStr + "\n" + nestedStr);
//        
//        int[] tandemLabels = handler.getLabels(tandemSig);
//        int[] nestedLabels = handler.getLabels(nestedSig);
//        System.out.println(Arrays.toString(tandemLabels) + "\n" + Arrays.toString(nestedLabels));
//        
//        IntGraph canonicalTandemForm = handler.getCanonicalForm(tandem);
//        IntGraph canonicalNestedForm = handler.getCanonicalForm(nested);
//        System.out.println(canonicalTandemForm + "\n" + canonicalNestedForm);
//        
//        Assert.assertEquals(tandemStr, nestedStr);
    }
}
