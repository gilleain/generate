package scheme3;

import group.SSPermutationGroup;

import java.util.Map;

import model.Graph;
import model.GraphDiscretePartitionRefiner;
import model.GraphSignature;

public class BaseSymmetryChildLister {
    
    protected GraphSignatureHandler signatureHandler;
    
    protected GraphDiscretePartitionRefiner refiner;
    
    public BaseSymmetryChildLister(GraphSignatureHandler signatureHandler) {
        refiner = new GraphDiscretePartitionRefiner();
        this.signatureHandler = signatureHandler;
    }
    
    protected SSPermutationGroup getAut(Graph g) {
        return refiner.getAutomorphismGroup(g);
    }

    protected void makeChild(Graph g, Graph h, Map<String, GraphSignature> children) {
        GraphSignature signature = new GraphSignature(h);
        String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
        children.put(canonicalLabel, signature);
    }
    
}
