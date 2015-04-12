package generate.handler;

import graph.model.IntGraph;

import java.util.HashMap;
import java.util.Map;

public class DuplicateCountingHandler implements GeneratorHandler {
    
    private Map<IntGraph, Integer> counts;
    
    public DuplicateCountingHandler() {
        this.counts = new HashMap<IntGraph, Integer>();
    }
    
    public void handle(IntGraph parent, IntGraph graph) {
        if (counts.containsKey(graph)) {
            counts.put(graph, counts.get(graph) + 1);
        } else {
            counts.put(graph, 1);
        }
    }
    
    public void finish() {
        counts.clear();
    }
    
}
