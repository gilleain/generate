package filter;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

public class Filterer {
    
    private List<Filter> filters;
    
    public Filterer(Filter... filterArr) {
        this.filters = new ArrayList<Filter>();
        for (Filter filter : filterArr) {
            filters.add(filter);
        }
    }
    
    public List<IntGraph> filter(List<IntGraph> graphs) {
        List<IntGraph> filtered = new ArrayList<IntGraph>();
        for (IntGraph graph : graphs) {
            if (accept(graph)) {
                filtered.add(graph);
            }
        }
        return filtered;
    }
    
    private boolean accept(IntGraph g) {
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
