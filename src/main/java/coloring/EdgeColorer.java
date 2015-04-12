package coloring;

import graph.model.IntGraph;

import java.util.List;

public interface EdgeColorer {
    
    public List<IntGraph> color(IntGraph graph);
    
}
