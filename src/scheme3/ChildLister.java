package scheme3;

import java.util.Map;

import model.Graph;
import model.GraphSignature;

public interface ChildLister {
    
    public Map<String, GraphSignature> list(Graph g, int n);

    public void setMaxDegree(int degMax);
    
}
