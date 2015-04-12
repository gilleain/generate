package scheme3.lister;

import graph.model.IntGraph;

import java.util.List;

public interface ChildLister {
    
    public List<IntGraph> list(IntGraph g, int n);

    public void setMaxDegree(int degMax);
    
}
