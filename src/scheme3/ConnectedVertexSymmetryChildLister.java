package scheme3;

import group.Permutation;
import group.SSPermutationGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphSignature;

import combinatorics.SubsetLister;

/**
 * List candidate children of a graph, by connecting a new vertex to a non-redundant
 * subset of the existing vertices..
 * 
 * @author maclean
 *
 */
public class ConnectedVertexSymmetryChildLister extends BaseSymmetryChildLister implements ChildLister {
    
    private int degMax;
    
    public ConnectedVertexSymmetryChildLister(GraphSignatureHandler signatureHandler) {
        super(signatureHandler);
    }
    
    @Override
    public Map<String, GraphSignature> list(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        SSPermutationGroup autG = getAut(g);
        
        for (List<Integer> subset : getSubsetLister(l, g)) {
            if (degMax > 1 && subset.size() > degMax) {
                continue;
            } else {
                if (isMinimal(subset, autG)) {
                    Graph h = g.makeAll(subset, max);
                    makeChild(g, h, children);
                }
            }
        }
        
        return children;
    }

    private SubsetLister<Integer> getSubsetLister(int l, Graph g) {
        if (degMax < 1) {
            return SubsetLister.getIndexLister(l);
        } else {
            List<Integer> indices = new ArrayList<Integer>();
            for (int i = 0; i < l; i++) {
                if (g.degree(i) < degMax) {
                    indices.add(i);
                }
            }
            return new SubsetLister<Integer>(indices);
        }
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

    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
  
}
