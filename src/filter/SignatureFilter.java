package filter;

import java.util.BitSet;
import java.util.List;

import model.Graph;
import model.GraphSignature;

public class SignatureFilter implements Filter {
    
    private List<String> signatureStrings;
    
    private int height;
    
    public SignatureFilter(List<String> signatureStrings, int height) {
        this.signatureStrings = signatureStrings;
        this.height = height;
    }
    
    public boolean filter(Graph graph) {
        BitSet discovered = new BitSet(signatureStrings.size());
        GraphSignature signature = new GraphSignature(graph);
        for (int vertexIndex = 0; vertexIndex < graph.getVertexCount(); vertexIndex++) {
            String signatureStringForI = 
                signature.signatureStringForVertex(vertexIndex, height);
            if (find(signatureStringForI, discovered)) {
                continue;
            } else {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean find(String signatureString, BitSet alreadyFound) {
        for (int listIndex = 0; listIndex < signatureStrings.size(); listIndex++) {
            if (alreadyFound.get(listIndex)) {
                continue;
            } else {
                if (signatureStrings.get(listIndex).equals(signatureString)) {
                    alreadyFound.set(listIndex);
                    return true;
                }
            }
        }
        return false;
    }

}
