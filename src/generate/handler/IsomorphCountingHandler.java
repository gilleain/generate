package generate.handler;

import graph.model.Edge;
import graph.model.Graph;
import graph.model.GraphSignature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsomorphCountingHandler implements GeneratorHandler {
    
    private Map<String, Graph> signatures;
    
    private Map<String, Integer> counts;
    
    private boolean ignoreDisconnected;
    
    private boolean reset;
    
    private boolean filterLoops;
    
    public IsomorphCountingHandler() {
        this(false);
    }
        
    public IsomorphCountingHandler(boolean ignoreDisconnected) {
        this(ignoreDisconnected, false);
    }
    
    public IsomorphCountingHandler(boolean ignoreDisconnected, boolean filterLoops) {
        this.signatures = new HashMap<String, Graph>();
        this.counts = new HashMap<String, Integer>();
        this.ignoreDisconnected = ignoreDisconnected;
        this.filterLoops = filterLoops;
    }
    
    public void handle(Graph parent, Graph graph) {
        if (reset) {
            signatures.clear();
            counts.clear();
            reset = false;
        }
        if (ignoreDisconnected && !graph.isConnected()) {
            return;
        }
        
        Graph h;
        if (this.filterLoops) {
            h = loopFilter(graph);
        } else {
            h = graph;
        }
        
        String signature = new GraphSignature(h).toCanonicalString();
        if (signatures.containsKey(signature)) {
            counts.put(signature, counts.get(signature) + 1);
        } else {
            signatures.put(signature, h);
            counts.put(signature, 1);
        }
    }
    
    private Graph loopFilter(Graph g) {
        Graph h = new Graph();
        for (Edge e : g.edges) {
            if (e.a != e.b) {
                h.makeEdge(e.a, e.b);
            }
        }
        return h;
    }
    
    public Map<String, Graph> getSignatureMap() {
        return signatures;
    }
    
    public List<Graph> getNonIsomorphicGraphs() {
        return new ArrayList<Graph>(signatures.values());
    }
    
    public Map<Graph, Integer> getNonIsomorphicGraphCount() {
        Map<Graph, Integer> graphCounts = new HashMap<Graph, Integer>();
        for (String key : signatures.keySet()) {
            graphCounts.put(signatures.get(key), counts.get(key));
        }
        return graphCounts;
    }
    
    public void finish() {
        reset = true;
    }

    public int getTotalGraphCount() {
        int count = 0;
        for (Integer value : counts.values()) {
            count += value;
        }
        return count;
    }
    
}
