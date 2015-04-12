package scheme3.lister;

import graph.model.GraphSignature;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import combinatorics.SubsetLister;

/**
 * List candidate children of a graph, by connecting a new vertex to a subset of the existing 
 * vertices and filtering duplicates.
 * 
 * @author maclean
 *
 */
public class ConnectedVertexFilteringChildLister implements ChildLister {
    
    private int degMax;
    
    @Override
    public List<IntGraph> list(IntGraph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, IntGraph> children = new HashMap<String, IntGraph>();
        
        for (List<Integer> subset : getSubsetLister(l, g)) {
            if (degMax > 1 && subset.size() > degMax) {
                continue;
            } else {
                if (subset.size() > 0) {
                    IntGraph h = g.makeAll(subset, max);
                    makeChild(g, h, children);
                }
            }
        }
        return new ArrayList<IntGraph>(children.values());
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
    
    private void makeChild(IntGraph g, IntGraph h, Map<String, IntGraph> children) {
        GraphSignature signature = new GraphSignature(h);
        String canonicalLabel = signature.toCanonicalString();
        if (children.containsKey(canonicalLabel)) {
            return;
        } else {
            children.put(canonicalLabel, h);
        }
    }
    
    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
