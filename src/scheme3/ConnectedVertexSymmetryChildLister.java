package scheme3;

import group.Permutation;
import group.SSPermutationGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphDiscretePartitionRefiner;
import model.GraphSignature;

import combinatorics.SubsetLister;

/**
 * List candidate children of a graph, by connecting a new vertex to a non-redundant
 * subset of the existing vertices..
 * 
 * @author maclean
 *
 */
public class ConnectedVertexSymmetryChildLister implements ChildLister {
    
    private GraphSignatureHandler signatureHandler;
    
    public ConnectedVertexSymmetryChildLister(GraphSignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }
    
    @Override
    public Map<String, GraphSignature> list(Graph g, int n) {
        return simpleMethod(g, n);
    }
    
    public Map<String, GraphSignature> simpleMethod(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        SSPermutationGroup autG = getAut(g);
        
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < l; i++) { indices.add(i); }
        for (List<Integer> subset : new SubsetLister<Integer>(indices)) {
            if (isMinimal(subset, autG)) {
                Graph h = g.makeAll(subset, max);
                makeChild(g, h, children);
            }
        }
        
        return children;
    }
    
    private boolean isMinimal(List<Integer> subset, SSPermutationGroup autG) {
        String subsetAsStr = subset.toString();
        for (Permutation p : autG.all()) {
            String permutedAsStr = permute(subset, p).toString(); 
            if (permutedAsStr.compareTo(subsetAsStr) > 0) {
                return false;
            }
        }
        return true;
    }
    
    private List<Integer> permute(List<Integer> original, Permutation p) {
        List<Integer> permuted = new ArrayList<Integer>();
        for (int i : original) {
            permuted.add(p.get(i));
        }
        return permuted;
    }

    private SSPermutationGroup getAut(Graph g) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        return refiner.getAutomorphismGroup(g);
    }

    private void makeChild(Graph g, Graph h, Map<String, GraphSignature> children) {
        GraphSignature signature = new GraphSignature(h);
        String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
        children.put(canonicalLabel, signature);
    }
}
