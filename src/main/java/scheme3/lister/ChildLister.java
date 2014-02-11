package scheme3.lister;

import graph.model.Graph;

import java.util.List;

public interface ChildLister {
    
    public List<Graph> list(Graph g, int n);

    public void setMaxDegree(int degMax);
    
}
