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
public class ConnectedVertexChildLister implements ChildLister {
    
    private GraphSignatureHandler signatureHandler;
    
    public ConnectedVertexChildLister(GraphSignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }
    
    @Override
    public Map<String, GraphSignature> list(Graph g, int n) {
        int l = g.getVertexCount();
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        
        // this seems unnecessary - surely this set will almost always be {0, ... , l} ?
        List<Integer> freeVertices = new ArrayList<Integer>();
        for (int i = 0; i < l; i++) {
            if (g.degree(i) < n - 1) {
                freeVertices.add(i);
            }
        }
        
        SubsetLister<Integer> subsetLister = new SubsetLister<Integer>(freeVertices);
        int max = Math.min(l, n - 1);
        for (List<Integer> X : subsetLister) {
            Graph h = g.makeAll(X, max);
            GraphSignature signature = new GraphSignature(h);
            String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
            if (children.containsKey(canonicalLabel)) {
                continue;
            } else {
                children.put(canonicalLabel, signature);
            }
        }
        return children;
    }
    
}
