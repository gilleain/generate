package scheme3;

import model.Graph;
import model.GraphSignature;

public interface GraphSignatureHandler {
    
    public boolean isCanonicalAugmentation(Graph g, 
                                           Graph canonGPrime, 
                                           GraphSignature gPrimeSig, 
                                           Graph gPrime, 
                                           String gCanonicalLabel);
    
    public String getCanonicalLabel(GraphSignature gSig);

    public Graph reconstruct(String canonLabel);
}
