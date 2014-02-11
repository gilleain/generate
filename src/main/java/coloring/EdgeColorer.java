package coloring;

import graph.model.Graph;

import java.util.List;

public interface EdgeColorer {
    
    public List<Graph> color(Graph graph);
    
}
