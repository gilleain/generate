package scheme3.lister;

import graph.model.GraphSignature;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List candidate children of a graph, by adding connected edges and filtering duplicates.
 * 
 * @author maclean
 *
 */
public class ConnectedEdgeFilteringChildLister implements ChildLister {
    
    private int degMax;
    
    public List<IntGraph> list(IntGraph g, int n) {
        int l = g.getVertexCount();
        Map<String, IntGraph> children = new HashMap<String, IntGraph>();
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
                        IntGraph h = g.makeNew(start, end);
                        GraphSignature signature = new GraphSignature(h);
                        String canonicalLabel = getCanonicalLabel(signature);
                        if (children.containsKey(canonicalLabel)) {
                            continue;
                        } else {
                            children.put(canonicalLabel, h);
                        }
                    }
                }
            }
        }
        return new ArrayList<IntGraph>(children.values());
    }
    
    public String getCanonicalLabel(GraphSignature gSig) {
        return gSig.toCanonicalString();
    }

    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
