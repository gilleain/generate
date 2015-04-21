package scheme3.signature;

import graph.model.Graph;
import graph.model.GraphBuilder;
import graph.model.GraphSignature;
import graph.model.IntEdge;
import graph.model.IntGraph;
import graph.model.VertexSignature;

import java.util.List;

import signature.ColoredTree;
import util.ChainDecomposition;

/**
 * Canonicalize only connected graphs, which is much simpler.
 * 
 * @author maclean
 *
 */
public class ConnectedEdgeSignatureHandler implements GraphSignatureHandler {
    
    @Override
    public boolean isCanonicalAugmentation(IntGraph g) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isCanonicalAugmentation(
            IntGraph g,
            IntGraph canonGPrime, GraphSignature gPrimeSig, IntGraph gPrime, String gCanonicalLabel) {
        ChainDecomposition chains = new ChainDecomposition(canonGPrime);
        List<IntEdge> bridges = chains.getBridges();
        int lastEdgeIndex = canonGPrime.esize() - 1;
        IntEdge lastEdge = null;
        while (lastEdgeIndex > 0) {
            lastEdge = canonGPrime.edges.get(lastEdgeIndex);
            if (bridges.contains(lastEdge)) {
                if (canonGPrime.degree(lastEdge.a) > 1 && canonGPrime.degree(lastEdge.b) > 1) {
                    lastEdgeIndex--;
                    continue;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        
        int[] labels = getLabels(gPrimeSig);
        int lA = labels[lastEdge.a];
        int lB = labels[lastEdge.b];
        IntGraph gPrimeMinusE = gPrime.remove(gPrime.getEdge(lA, lB));
        String gPrimeMinusESignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusE));
        return gPrimeMinusESignatureString.equals(gCanonicalLabel);
    }
    
    public boolean isCanonicalAugmentation(IntGraph parent, IntGraph child) {
        GraphSignature childSig = new GraphSignature(child);
        IntGraph canonChild = getCanonicalForm(childSig);
        String parentLabel = new GraphSignature(parent).toCanonicalString();
        return isCanonicalAugmentation(parent, canonChild, childSig, child, parentLabel);
    }

    public IntGraph getCanonicalForm(Graph g) {
        return getCanonicalForm(new GraphSignature(g));
    }
    
    public IntGraph getCanonicalForm(GraphSignature gSig) {
        return reconstruct(gSig.toCanonicalString());
    }
    
    public IntGraph reconstruct(String canonicalLabel) {
        ColoredTree tree = VertexSignature.parse(canonicalLabel);
        GraphBuilder builder = new GraphBuilder();
        builder.makeFromColoredTree(tree);
        return builder.getProduct();
    }
    
    public int[] getLabels(GraphSignature gSig) {
        IntGraph g = (IntGraph)gSig.getGraph();
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
