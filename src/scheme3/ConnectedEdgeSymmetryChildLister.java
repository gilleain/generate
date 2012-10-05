package scheme3;

import group.Permutation;
import group.SSPermutationGroup;

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
public class ConnectedEdgeSymmetryChildLister extends BaseSymmetryChildLister implements ChildLister {
    
    private int degMax;
    
    public ConnectedEdgeSymmetryChildLister(GraphSignatureHandler signatureHandler) {
        super(signatureHandler);
    }
    
    public Map<String, GraphSignature> list(Graph g, int n) {
        int l = g.getVertexCount();
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        int max = Math.min(l, n - 1);
        SSPermutationGroup autG = getAut(g);
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
                        if (end < max && isMinimal(start, end, autG)) {
                            makeChild(g, g.makeNew(start, end), children);
                        } else if (end == max && isMinimal(start, autG)) {
                            makeChild(g, g.makeNew(start, end), children);
                        }
                    }
                }
            }
        }
        return children;
    }
    
    private boolean isMinimal(int start, SSPermutationGroup autG) {
        for (Permutation p : autG.all()) {
            if (p.get(start) < start) {
                return false;
            }
        }
        return true;
    }

    private boolean isMinimal(int start, int end, SSPermutationGroup autG) {
        String orig = start + ":" + end;
        for (Permutation p : autG.all()) {
            int pStart = p.get(start);
            int pEnd   = p.get(end);
            String perm = (pStart < pEnd)? pStart + ":" + pEnd : pEnd + ":" + pStart;
            if (perm.compareTo(orig) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
