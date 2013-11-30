package tools;

import graph.model.Graph;
import graph.model.GraphSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignatureGraphDifference implements GraphFileDiff.GraphDifference {
    
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
    public void compare(Graph graph, List<Graph> difference) {
        GraphSignature sig = new GraphSignature(graph);
        String signatureString = sig.toCanonicalString();
        if (signatureMap.containsKey(signatureString)) {
            Graph graphA = signatureMap.get(signatureString);
            System.out.println(signatureString + " " + graphA + " = " + graph);
        } else {
            difference.add(graph);
        }
    }
    
}
