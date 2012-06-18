package generate.handler;

import java.util.HashMap;
import java.util.Map;

import model.Graph;

public class DuplicateCountingHandler implements GeneratorHandler {
    
    private Map<Graph, Integer> counts;
    
    public DuplicateCountingHandler() {
        this.counts = new HashMap<Graph, Integer>();
    }
    
    public void handle(Graph parent, Graph graph) {
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
