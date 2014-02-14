package util;

import graph.model.Graph;

public interface GraphDifference {
    public void add(Graph graph);
    public void compare(Graph graph, Callback callback);
    
    public interface Callback {
        public void same(Graph graphA, Graph graphB);
        public void different(Graph graphA, Graph graphB);
    }
}
