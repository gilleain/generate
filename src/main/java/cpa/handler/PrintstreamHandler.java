package cpa.handler;

import graph.model.IntGraph;

import java.io.PrintStream;

public class PrintstreamHandler implements GenerationHandler {
    
    private final PrintStream out;
    
    private final boolean printCount;
    
    private int count;
    
    public PrintstreamHandler(PrintStream out, boolean printCount) {
        this.out = out;
        this.printCount = printCount;
        this.count = 0;
    }

    @Override
    public void handle(IntGraph graph) {
        if (printCount) {
            out.println(count + "\t" + graph);
            count++;
        } else {
            out.println(graph);
        }
    }

}
