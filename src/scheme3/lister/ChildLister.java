package scheme3.lister;

import java.util.List;

import model.Graph;

public interface ChildLister {
    
    public List<Graph> list(Graph g, int n);

    public void setMaxDegree(int degMax);
    
}
