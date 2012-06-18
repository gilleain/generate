package filter;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

public class Filterer {
    
    private List<Filter> filters;
    
    public Filterer(Filter... filterArr) {
        this.filters = new ArrayList<Filter>();
        for (Filter filter : filterArr) {
            filters.add(filter);
        }
    }
    
    public List<Graph> filter(List<Graph> graphs) {
        List<Graph> filtered = new ArrayList<Graph>();
        for (Graph graph : graphs) {
            if (accept(graph)) {
                filtered.add(graph);
            }
        }
        return filtered;
    }
    
    private boolean accept(Graph g) {
        for (Filter filter : filters) {
            if (filter.filter(g)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    
}
