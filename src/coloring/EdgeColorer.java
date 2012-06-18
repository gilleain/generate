package coloring;

import java.util.List;

import model.Graph;

public interface EdgeColorer {
    
    public List<Graph> color(Graph graph);
    
}
