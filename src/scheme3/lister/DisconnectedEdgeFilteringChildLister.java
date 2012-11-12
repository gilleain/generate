package scheme3.lister;

import java.util.HashMap;
import java.util.Map;

import scheme3.signature.GraphSignatureHandler;

import model.Graph;
import model.GraphSignature;

/**
 * List candidate children of a graph, by adding edges - possibly disconnected 
 * ones - and filtering duplicates.
 *  
 * @author maclean
 *
 */
public class DisconnectedEdgeFilteringChildLister implements ChildLister {
    
    private GraphSignatureHandler signatureHandler;
    
    private int degMax;
    
    public DisconnectedEdgeFilteringChildLister(GraphSignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }
    
    public Map<String, GraphSignature> list(Graph g, int n) {
        int l = g.getVertexCount();
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        int max = Math.min(l, n - 1);
        for (int start = 0; start < l; start++) {
            int dS = (degMax < 1)? -1 : g.degree(start);
            if (dS >= degMax) {
                continue;
            } else {
                for (int end = start + 1; end <= max; end++) {
                    int dE = (degMax < 1)? -1 : g.degree(end);
                    if (g.isConnected(start, end) || (dE > 1 && dE >= degMax)) {
                        continue;
                    } else {
                        Graph h = g.makeNew(start, end);
                        GraphSignature signature = new GraphSignature(h);
                        String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
                        if (children.containsKey(canonicalLabel)) {
                            continue;
                        } else {
                            children.put(canonicalLabel, signature);
                        }
                    }
                }
            }
        }
        if (l < n - 2) {
            Graph h = g.makeNew(l, l + 1);
            GraphSignature hSig = new GraphSignature(h);
            String canonicalLabel = signatureHandler.getCanonicalLabel(hSig);
            if (!children.containsKey(canonicalLabel)) {
                children.put(canonicalLabel, hSig);
            }
        }
        return children;
    }
    
    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
