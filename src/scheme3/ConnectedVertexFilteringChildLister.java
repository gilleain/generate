package scheme3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphSignature;

import combinatorics.MultiKSubsetLister;
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
    
    private String tmpToFind = new GraphSignature(new Graph("0:1, 0:4, 1:2, 2:3, 2:4")).toCanonicalString();
    
    public ConnectedVertexFilteringChildLister(GraphSignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }
    
    @Override
    public Map<String, GraphSignature> list(Graph g, int n) {
        return simpleMethod(g, n);
//        return nonSimpleMethod(g, n);
    }
    
    public Map<String, GraphSignature> simpleMethod(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < l; i++) { indices.add(i); }
        for (List<Integer> subset : new SubsetLister<Integer>(indices)) {
            Graph h = g.makeAll(subset, max);
//            System.out.println(g + " + " + indices + " -> " + h);
            makeChild(g, h, children);
        }
        
//        for (String canSig : children.keySet()) {
//            if (canSig.equals(tmpToFind)) {
//                System.out.println(g + " -> " + children.get(canSig).getGraph());
//            }
//        }
        return children;
    }
    
    public Map<String, GraphSignature> nonSimpleMethod(Graph g, int n) {
        int l = g.getVertexCount();
        int max = Math.min(l, n - 1);
        Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
        
        Map<String, List<Integer>> orbitMap = getOrbits(g);
        List<String> orbitLabels = new ArrayList<String>(orbitMap.keySet());
        for (int k = 1; k <= max; k++) {
            if (k == 1) {
                for (List<Integer> orbit : orbitMap.values()) {
                    int i = orbit.get(0);
                    Graph h = g.makeNew(i, max);
                    System.out.println(g + " + [" + i + "] -> " + h);
                    makeChild(g, h, children);
                }
            } else {
                // System.out.println("getting k=" + k + " multisets of " + orbitLabels);
                MultiKSubsetLister<String> subsetLister = new MultiKSubsetLister<String>(k, orbitLabels);
                for (List<String> mX : subsetLister) {
                    List<Integer> indices = multiSetToIndices(mX, orbitMap);
                    if (indices != null) {
                        Graph h = g.makeAll(indices, max);
                        System.out.println(g + " + " + indices + " -> " + h);
                        makeChild(g, h, children);
                    }
                }
            }
        }
        for (String canSig : children.keySet()) {
            if (canSig.equals(tmpToFind)) {
                System.out.println(g + " -> " + children.get(canSig).getGraph());
            }
//            GraphSignature gSig = children.get(canSig);
//            System.out.println(gSig.getGraph());
        }
        return children;
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
    
    private List<Integer> multiSetToIndices(
            List<String> labelMultiset, Map<String, List<Integer>> orbitMap) {
//        System.out.println("M = " + labelMultiset + " O = " + orbitMap);
        List<Integer> indices = new ArrayList<Integer>();
        int orbitIndex = 0;
        String prevLabel = null;
        for (String label : labelMultiset) {
            List<Integer> orbit = orbitMap.get(label);
            if (prevLabel == null || !prevLabel.equals(label)) {
                orbitIndex = 0;
            } else {
                orbitIndex++;
            }
            if (orbitIndex < orbit.size()) {
                indices.add(orbit.get(orbitIndex));
                prevLabel = label;
            } else {
                return null;    // XXX - must be a better way... 
            }
        }
        return indices;
    }
    
    private Map<String, List<Integer>> getOrbits(Graph g) {
        Map<String, List<Integer>> orbits = new HashMap<String, List<Integer>>();
        for (int i = 0; i < g.vsize(); i++) {
            String orbitLabel = String.valueOf(g.degree(i));
            List<Integer> symClass;
            if (orbits.containsKey(orbitLabel)) {
                symClass = orbits.get(orbitLabel);
            } else {
                symClass = new ArrayList<Integer>();
                orbits.put(orbitLabel, symClass);
            }
            symClass.add(i);
        }
        return orbits;
    }
    
}
