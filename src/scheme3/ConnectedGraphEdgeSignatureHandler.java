package scheme3;

import model.Edge;
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
public class ConnectedGraphEdgeSignatureHandler implements GraphSignatureHandler {
    
    public boolean isCanonicalAugmentation(Graph parent, Graph child) {
        GraphSignature childSig = new GraphSignature(child);
        Graph canonChild = getCanonicalForm(childSig);
        String parentLabel = new GraphSignature(parent).toCanonicalString();
        return isCanonicalAugmentation(parent, canonChild, childSig, child, parentLabel);
    }

    public boolean isCanonicalAugmentation(
            Graph g,
            Graph canonGPrime, GraphSignature gPrimeSig, Graph gPrime, String gCanonicalLabel) {
        int lastEdgeIndex = canonGPrime.esize() - 1;
        Edge lastEdge = canonGPrime.edges.get(lastEdgeIndex);
        int[] labels = getLabels(gPrimeSig);
        int lA = labels[lastEdge.a];
        int lB = labels[lastEdge.b];
        Graph gPrimeMinusE = gPrime.remove(gPrime.getEdge(lA, lB));
        String gPrimeMinusESignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusE));
        return gPrimeMinusESignatureString.equals(gCanonicalLabel);
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
