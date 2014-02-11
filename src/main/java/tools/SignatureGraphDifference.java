package tools;

import graph.model.Graph;
import graph.model.GraphSignature;

import java.util.HashMap;
import java.util.Map;

public class SignatureGraphDifference implements GraphDifference {
    
    private Map<String, Graph> signatureMap;
    
    public SignatureGraphDifference() {
        signatureMap = new HashMap<String, Graph>();
    }

    @Override
    public void add(Graph graph) {
        GraphSignature sig = new GraphSignature(graph);
        signatureMap.put(sig.toCanonicalString(), graph);
    }

    @Override
    public void compare(Graph otherGraph, Callback callback) {
        GraphSignature sig = new GraphSignature(otherGraph);
        String signatureString = sig.toCanonicalString();
        if (signatureMap.containsKey(signatureString)) {
            Graph graph = signatureMap.get(signatureString);
//            System.out.println(signatureString + " " + graphA + " = " + graph);
            callback.same(graph, otherGraph);
        } else {
            callback.different(null, otherGraph);
        }
        
    }
    
}
