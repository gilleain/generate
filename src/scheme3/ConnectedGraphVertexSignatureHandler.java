package scheme3;

import model.Graph;
import model.GraphBuilder;
import model.GraphSignature;
import model.VertexSignature;
import signature.ColoredTree;

/**
 * Canonicalize only connected graphs, which is much simpler.
 * 
 * @author maclean
 *
 */
public class ConnectedGraphVertexSignatureHandler implements GraphSignatureHandler {
    
    public boolean isCanonicalAugmentation(Graph parent, Graph child) {
        GraphSignature childSig = new GraphSignature(child);
        Graph canonChild = getCanonicalForm(childSig);
        String parentLabel = new GraphSignature(parent).toCanonicalString();
        return isCanonicalAugmentation(canonChild, childSig, child, parentLabel);
    }

    public boolean isCanonicalAugmentation(
            Graph canonGPrime, GraphSignature gPrimeSig, Graph gPrime, String gCanonicalLabel) {
        int n = gPrime.vsize();
        int[] labels = getLabels(gPrimeSig);
        int mappedVertex = labels[n - 1];
        Graph gPrimeMinusV = gPrime.minus(mappedVertex);
        String gPrimeMinusVSignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusV));
        return gPrimeMinusVSignatureString.equals(gCanonicalLabel);
    }
    
    public Graph getCanonicalForm(Graph g) {
        return getCanonicalForm(new GraphSignature(g));
    }
    
    public Graph getCanonicalForm(GraphSignature gSig) {
        return reconstruct(gSig.toCanonicalString());
    }
    
    public Graph reconstruct(String canonicalLabel) {
        ColoredTree tree = VertexSignature.parse(canonicalLabel);
        GraphBuilder builder = new GraphBuilder();
        builder.makeFromColoredTree(tree);
        return builder.getProduct();
    }
    
    public int[] getLabels(GraphSignature gSig) {
        Graph g = gSig.getGraph();
        int[] extLabels = gSig.getCanonicalLabels();
        int[] labels = new int[g.vsize()];
        // XXX this is effectively reversing the permutation again - don't reverse in first place? 
        for (int i = 0; i < g.vsize(); i++) {
            labels[extLabels[i]] = i;
        }
        return labels;
    }
    
    public String getCanonicalLabel(GraphSignature gSig) {
        return gSig.toCanonicalString();
    }
    
}
