package scheme3.lister;

import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
    @Override
    public List<IntGraph> list(IntGraph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        List<IntGraph> children = new ArrayList<IntGraph>();
        PermutationGroup autG = getAut(g);
        
        for (List<Integer> subset : getSubsetLister(l, g)) {
            if (degMax > 1 && subset.size() > degMax) {
                continue;
            } else {
                if (subset.size() > 0 && isMinimal(subset, autG)) {
                    IntGraph h = g.makeAll(subset, max);
                    children.add(h);
                }
            }
        }
        
        return children;
    }

    private SubsetLister<Integer> getSubsetLister(int l, IntGraph g) {
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
    
    private boolean isMinimal(List<Integer> subset, PermutationGroup autG) {
        Collections.sort(subset);
        String subsetAsStr = subset.toString();
        for (Permutation p : autG.all()) {
            List<Integer> psubset = permute(subset, p);
            Collections.sort(psubset);
            String permutedAsStr = psubset.toString(); 
            if (permutedAsStr.compareTo(subsetAsStr) < 0) {
//                System.out.println(permutedAsStr + " < " + subsetAsStr);
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
