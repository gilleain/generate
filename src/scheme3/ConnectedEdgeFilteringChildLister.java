package scheme3;

import java.util.HashMap;
import java.util.Map;

import model.Graph;
import model.GraphSignature;

/**
 * List candidate children of a graph, by adding connected edges and filtering duplicates.
 * 
 * @author maclean
 *
 */
public class ConnectedEdgeFilteringChildLister implements ChildLister {
    
    private GraphSignatureHandler signatureHandler;
    
    private int degMax;
    
    public ConnectedEdgeFilteringChildLister(GraphSignatureHandler signatureHandler) {
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
        return children;
    }

    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
