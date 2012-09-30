package scheme3;

import java.util.Arrays;
import java.util.List;

import model.Edge;
import model.Graph;
import model.GraphBuilder;
import model.GraphSignature;
import model.VertexSignature;
import signature.AbstractVertexSignature;
import signature.ColoredTree;

/**
 * Canonicalize both disconnected and connected graphs, and reconstruct them from their signature strings.
 * 
 * @author maclean
 *
 */
public class DisconnectedGraphSignatureHandler implements GraphSignatureHandler {
    
    public boolean isCanonicalAugmentation(
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
        String canString = new GraphSignature(g).toCanonicalString();
        return reconstruct(canString);
    }
    
    public Graph reconstruct(String canonicalLabel) {
        ColoredTree tree = VertexSignature.parse(canonicalLabel);
        GraphBuilder builder = new GraphBuilder();
        builder.makeFromColoredTree(tree);
        return builder.getProduct();
    }
    
    public int[] getLabels(GraphSignature gSig) {
        Graph g = gSig.getGraph();
        List<List<Integer>> components = g.getComponents();
        if (components.size() == 1) {
            int[] extLabels = gSig.getCanonicalLabels();
            int[] labels = new int[g.vsize()];
            // XXX this is effectively reversing the permutation again - don't reverse in first place? 
            for (int i = 0; i < g.vsize(); i++) {
                labels[extLabels[i]] = i;
            }
            return labels;
        } else {
            int[] labels = new int[g.vsize()];
            int componentOffset = 0;
            for (List<Integer> component : components) {
                AbstractVertexSignature maxSigForComponent = null;
                String maxSigStrForComponent = null;
                for (int i : component) {
                    AbstractVertexSignature sigForI = gSig.signatureForVertex(i);
                    String sigForIStr = sigForI.toCanonicalString();
                    if (maxSigForComponent == null || maxSigStrForComponent.compareTo(sigForIStr) < 0) {
                        maxSigForComponent = sigForI;
                        maxSigStrForComponent = sigForIStr;
                    }
                }
                int[] componentLabels = maxSigForComponent.getCanonicalLabelling(g.vsize());
                for (int i : component) {
                    int j = componentLabels[i];
                    labels[componentOffset + j] = i;
                }
                componentOffset += component.size();
            }
//          System.out.println(java.util.Arrays.toString(labels));
            return labels;   
        }
    }
    
    public String getCanonicalLabel(GraphSignature gSig) {
        Graph g = gSig.getGraph();
        List<List<Integer>> components = g.getComponents();
        if (components.size() == 1) {
            return gSig.toCanonicalString();
        } else {
            String[] labelArray = new String[components.size()];
            int componentIndex = 0;
            for (List<Integer> component : components) {
                String maxComponentLabel = null;
                for (int i : component) {
                    String sigStringForI = gSig.signatureStringForVertex(i);
                    if (maxComponentLabel == null || maxComponentLabel.compareTo(sigStringForI) < 0) {
                        maxComponentLabel = sigStringForI;
                    }
                }
                labelArray[componentIndex] = maxComponentLabel;
                componentIndex++;
            }
            Arrays.sort(labelArray);
            StringBuffer fullLabel = new StringBuffer();
            for (String label : labelArray) {
                if (fullLabel.length() == 0) {
                    fullLabel.append(label);
                } else {
                    fullLabel.append("+").append(label);
                }
            }
            return fullLabel.toString();
        }
    }
    
}
