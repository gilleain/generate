package scheme3.lister;

import graph.model.Graph;
import graph.model.GraphSignature;

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
    public List<Graph> list(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, Graph> children = new HashMap<String, Graph>();
        
        for (List<Integer> subset : getSubsetLister(l, g)) {
            if (degMax > 1 && subset.size() > degMax) {
                continue;
            } else {
                if (subset.size() > 0) {
                    Graph h = g.makeAll(subset, max);
                    makeChild(g, h, children);
                }
            }
        }
        return new ArrayList<Graph>(children.values());
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
    
    private void makeChild(Graph g, Graph h, Map<String, Graph> children) {
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
