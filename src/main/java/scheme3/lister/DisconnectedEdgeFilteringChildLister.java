package scheme3.lister;

import graph.model.GraphSignature;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List candidate children of a graph, by adding edges - possibly disconnected 
 * ones - and filtering duplicates.
 *  
 * @author maclean
 *
 */
public class DisconnectedEdgeFilteringChildLister implements ChildLister {
    
    private static final String SIG_SEPARATOR = "+";
    
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
                        makeChild(g, h, children);
                    }
                }
            }
        }
        if (l < n - 2) {
            IntGraph h = g.makeNew(l, l + 1);
            makeChild(g, h, children);
        }
        return new ArrayList<IntGraph>(children.values());
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
    
    public String getCanonicalLabel(GraphSignature signature) {
        IntGraph g = (IntGraph) signature.getGraph();
        List<List<Integer>> components = g.getComponents();
        if (components.size() == 1) {
            return signature.toCanonicalString();
        } else {
            String[] labelArray = new String[components.size()];
            int componentIndex = 0;
            for (List<Integer> component : components) {
                String maxComponentLabel = null;
                for (int i : component) {
                    String sigStringForI = signature.signatureStringForVertex(i);
                    if (maxComponentLabel == null || maxComponentLabel.compareTo(sigStringForI) < 0) {
                        maxComponentLabel = sigStringForI;
                    }
                }
                labelArray[componentIndex] = maxComponentLabel;
                componentIndex++;
            }
            Arrays.sort(labelArray);
            StringBuffer fullLabel = new StringBuffer();
            for (String label : labelArray) {
                if (fullLabel.length() == 0) {
                    fullLabel.append(label);
                } else {
                    fullLabel.append(SIG_SEPARATOR).append(label);
                }
            }
            return fullLabel.toString();
        }
    }

    @Override
    public void setMaxDegree(int degMax) {
        this.degMax = degMax;
    }
}
