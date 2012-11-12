package scheme3.signature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Graph;
import model.GraphSignature;
import model.VertexSignature;

/**
 * Canonicalize only connected graphs, which is much simpler.
 * 
 * @author maclean
 *
 */
public class ConnectedVertexSignatureHandler implements GraphSignatureHandler {
    
    public boolean isCanonicalAugmentation(Graph child) {
        GraphSignature childSig = new GraphSignature(child);

        // for speed reasons, find both the labels and the orbits at the same time
        Map<String, List<Integer>> orbits = new HashMap<String, List<Integer>>();
        String maxSig = null;
        VertexSignature maxVertexSig = null;
        for (int i = 0; i < child.vsize(); i++) {
            VertexSignature sigForI = (VertexSignature) childSig.signatureForVertex(i);
            String sigStringForI = sigForI.toCanonicalString();
            if (maxSig == null || sigStringForI.compareTo(maxSig) < 0) {
                maxSig = sigStringForI;
                maxVertexSig = sigForI;
            }
            if (orbits.containsKey(sigStringForI)) {
                orbits.get(sigStringForI).add(i);
            } else {
                List<Integer> orbit = new ArrayList<Integer>();
                orbits.put(sigStringForI, orbit);
                orbit.add(i);
            }
        }
        // TODO : implement a method in AbstVertSig to get the inverse labeling
        int[] labels = maxVertexSig.getCanonicalLabelling(child.vsize());

//        for (Orbit o : orbits) { System.out.println(o); }
        int size = labels.length - 1;
        int del = find(labels, size);
//        System.out.println("found " + del + " in " + java.util.Arrays.toString(labels));
        return del == size || inOrbit(size, del, orbits.values());
    }

    private boolean inOrbit(int i, int j, Collection<List<Integer>> orbits) {
        for (List<Integer> o : orbits) {
            if (o.contains(i) && o.contains(j)) {
                return true;
            }
        }
        return false;
    }

    private int find(int[] labels, int j) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] == j) return i;
        }
        return -1;
    }
}
