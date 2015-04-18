package cpa;

import graph.model.IntGraph;

public class CountingHandler implements GenerationHandler {
    
    private int count;
    
    public CountingHandler() {
        count = 0;
    }

    @Override
    public void handle(IntGraph graph) {
        count++;
    }
    
    public int getCount() {
        return count;
    }

}
