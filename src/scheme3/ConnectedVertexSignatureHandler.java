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
public class ConnectedVertexSignatureHandler implements GraphSignatureHandler {
    
    private String tmpToFind = new GraphSignature(new Graph("0:1, 0:4, 1:2, 2:3, 2:4")).toCanonicalString();
    
    public boolean isCanonicalAugmentation(Graph parent, Graph child) {
        GraphSignature childSig = new GraphSignature(child);
        Graph canonChild = getCanonicalForm(childSig);
        String parentLabel = new GraphSignature(parent).toCanonicalString();
        return isCanonicalAugmentation(parent, canonChild, childSig, child, parentLabel);
    }

    public boolean isCanonicalAugmentation(
            Graph g,
            Graph canonGPrime, 
            GraphSignature gPrimeSig, 
            Graph gPrime, 
            String gCanonicalLabel) {
        int n = gPrime.vsize();
//        int[] labels = getLabels(gPrimeSig);
//        int mappedVertex = labels[n - 1];
        int mappedVertex = getCanonicalIndex(gPrimeSig);
//        boolean inv = invariantsTrue(g, gPrime, labels, mappedVertex);
//        if (inv) {
            Graph gPrimeMinusV = gPrime.minus(mappedVertex);
            String gPrimeMinusVSignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusV));
            boolean canon = gPrimeMinusVSignatureString.equals(gCanonicalLabel);
            if (canon) {
//                if (!inv) {
//                    System.out.println("!inv yet canon " + gPrime 
//                            + "\t" + mappedVertex + "\t" + g + "\t" + java.util.Arrays.toString(labels));
//                }
//                if (gPrimeSig.toCanonicalString().equals(tmpToFind)) {
//                    System.out.println("canon " + gPrime);
//                }
                return true; 
            } else {
                return false;
            }
//        } else {
//            return false;
//        }
    }
    
    public boolean invariantsTrue(Graph parent, Graph child, int[] labels, int canonicalVertex) {
        for (int i : child.getConnected(canonicalVertex)) {
//            int pD = parent.degree(find(i, labels));
            int pD = parent.degree(i);
            if (pD == 0) pD = parent.degree(find(i, labels));
            int cD = child.degree(i);
            if (pD == cD - 1) {
                continue;
            } else {
//                System.out.println("For i = " + i + " " + pD + " != " + cD + " in " + parent);
                return false;
            }
        }
        return true;
    }
    
    private int find(int j, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == j) return i;
        }
        return -1;
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
    
    private int getCanonicalIndex(GraphSignature gSig) {
        int[] extLabels = gSig.getCanonicalLabels();
        int n = gSig.getGraph().vsize();
        for (int i = 0; i < n; i++) {
            if (extLabels[i] == n - 1) return i;
        }
        return -1;
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
