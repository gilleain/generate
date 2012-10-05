package scheme3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphSignature;

import combinatorics.SubsetLister;

/**
 * List candidate children of a graph, by connecting a new vertex to a subset of the existing 
 * vertices and filtering duplicates.
 * 
 * @author maclean
 *
 */
public class ConnectedVertexFilteringChildLister implements ChildLister {
    
    private GraphSignatureHandler signatureHandler;
    
    private int degMax;
    
    public ConnectedVertexFilteringChildLister(GraphSignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }
    
    @Override
    public Map<String, GraphSignature> list(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        
        for (List<Integer> subset : getSubsetLister(l, g)) {
            Graph h = g.makeAll(subset, max);
            makeChild(g, h, children);
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
    
    private void makeChild(Graph g, Graph h, Map<String, GraphSignature> children) {
        GraphSignature signature = new GraphSignature(h);
        String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
        if (children.containsKey(canonicalLabel)) {
            return;
        } else {
            children.put(canonicalLabel, signature);
        }
    }
    
    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
