package generate.handler;

import graph.model.IntGraph;

public class TimingHandler implements GeneratorHandler {
    
    private boolean started;
    
    private long startTime;
    
    private long endTime;
    
    private int count;
    
    public TimingHandler() {
        started = false;
        count = 0;
    }

    @Override
    public void handle(IntGraph parent, IntGraph graph) {
        if (!started) {
            count = 0;
            startTime = System.currentTimeMillis();
            started = true;
        }
        count++;
    }
    
    public int getCount() {
        return count;
    }
    
    public long getElapsedTime() {
        return endTime - startTime;
    }
    

    @Override
    public void finish() {
        endTime = System.currentTimeMillis();
        started = false;
    }
    
}
