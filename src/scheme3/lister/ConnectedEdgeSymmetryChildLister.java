package scheme3.lister;

import graph.model.Graph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * List candidate children of a graph, by adding connected edges and filtering duplicates.
 * 
 * @author maclean
 *
 */
public class ConnectedEdgeSymmetryChildLister extends BaseSymmetryChildLister implements ChildLister {
    
    private int degMax;
    
    public List<Graph> list(Graph g, int n) {
        int l = g.getVertexCount();
        List<Graph> children = new ArrayList<Graph>();
        int max = Math.min(l, n - 1);
        PermutationGroup autG = getAut(g);
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
                            children.add(g.makeNew(start, end));
                        } else if (end == max && isMinimal(start, autG)) {
                            children.add(g.makeNew(start, end));
                        }
                    }
                }
            }
        }
        return children;
    }
    
    private boolean isMinimal(int start, PermutationGroup autG) {
        for (Permutation p : autG.all()) {
            if (p.get(start) < start) {
                return false;
            }
        }
        return true;
    }

    private boolean isMinimal(int start, int end, PermutationGroup autG) {
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
