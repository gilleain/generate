package util;

import graph.model.IntGraph;

public interface GraphDifference {
    public void add(IntGraph graph);
    public void compare(IntGraph graph, Callback callback);
    
    public interface Callback {
        public void same(IntGraph graphA, IntGraph graphB);
        public void different(IntGraph graphA, IntGraph graphB);
    }
}
