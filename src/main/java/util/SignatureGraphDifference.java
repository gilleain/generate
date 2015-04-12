package util;

import graph.model.GraphSignature;
import graph.model.IntGraph;

import java.util.HashMap;
import java.util.Map;

public class SignatureGraphDifference implements GraphDifference {
    
    private Map<String, IntGraph> signatureMap;
    
    public SignatureGraphDifference() {
        signatureMap = new HashMap<String, IntGraph>();
    }

    @Override
    public void add(IntGraph graph) {
        GraphSignature sig = new GraphSignature(graph);
        signatureMap.put(sig.toCanonicalString(), graph);
    }

    @Override
    public void compare(IntGraph otherGraph, Callback callback) {
        GraphSignature sig = new GraphSignature(otherGraph);
        String signatureString = sig.toCanonicalString();
        if (signatureMap.containsKey(signatureString)) {
            IntGraph graph = signatureMap.get(signatureString);
//            System.out.println(signatureString + " " + graphA + " = " + graph);
            callback.same(graph, otherGraph);
        } else {
            callback.different(null, otherGraph);
        }
        
    }
    
}
