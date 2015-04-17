package cpa;

import graph.model.GraphSignature;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsomorphismHandler implements GenerationHandler {
    
    private Map<String, List<IntGraph>> map;
    
    public IsomorphismHandler() {
        map = new HashMap<String, List<IntGraph>>();
    }

    @Override
    public void handle(IntGraph graph) {
        GraphSignature sig = new GraphSignature(graph);
        String canonicalForm = sig.getGraphSignature();
        List<IntGraph> isoClass;
        if (map.containsKey(canonicalForm)) {
            isoClass = map.get(canonicalForm);
        } else { 
            isoClass = new ArrayList<IntGraph>();
            map.put(canonicalForm, isoClass);
        }
        isoClass.add(graph);
    }
    
    public Map<String, List<IntGraph>> getMap() {
        return map;
    }

}
