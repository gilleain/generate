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
public class DisconnectedEdgeSignatureHandler implements GraphSignatureHandler {
    
    private static final String SIG_SEPARATOR = "+";
    
    public boolean isCanonicalAugmentation(
            Graph g,
            Graph canonGPrime, GraphSignature gPrimeSig, Graph gPrime, String gCanonicalLabel) {
        ChainDecomposition chains = new ChainDecomposition(canonGPrime);
        List<Edge> bridges = chains.getBridges();
        int lastEdgeIndex = canonGPrime.esize() - 1;
        Edge lastEdge = null;
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
        Graph gPrimeMinusE = gPrime.remove(gPrime.getEdge(lA, lB));
        String gPrimeMinusESignatureString = getCanonicalLabel(new GraphSignature(gPrimeMinusE));
        return gPrimeMinusESignatureString.equals(gCanonicalLabel);
    }
    
    public Graph getCanonicalForm(Graph g) {
        String canString = getCanonicalLabel(new GraphSignature(g));
        return reconstruct(canString);
    }
    
    public Graph reconstruct(String canonicalLabel) {
        if (canonicalLabel.contains(SIG_SEPARATOR)) {
            String[] parts = canonicalLabel.split("\\Q" + SIG_SEPARATOR + "\\E");
            int offset = 0;
            Graph wholeGraph = null;
            for (String part : parts) {
                Graph gPart = reconstructSingleSignatureString(part);
                if (offset == 0) {
                    wholeGraph = gPart;
                } else {
                    for (Edge e : gPart.edges) {
                        wholeGraph.makeEdge(e.a + offset, e.b + offset);
                    }
                }
                offset += gPart.vsize();
            }
            return wholeGraph;
        } else {
            return reconstructSingleSignatureString(canonicalLabel);
        }
    }
    
    private Graph reconstructSingleSignatureString(String sigString) {
        ColoredTree tree = VertexSignature.parse(sigString);
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
                    fullLabel.append(SIG_SEPARATOR).append(label);
                }
            }
            return fullLabel.toString();
        }
    }
    
}
