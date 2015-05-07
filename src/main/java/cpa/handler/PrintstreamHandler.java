package cpa.handler;

import graph.model.IntGraph;

import java.io.PrintStream;
import java.util.Arrays;

public class PrintstreamHandler implements GenerationHandler {
    
    private final PrintStream out;
    
    private final boolean printCount;
    
    private final boolean printDegreeSeq;
    
    private int count;
    
    public PrintstreamHandler(PrintStream out, boolean printCount) {
        this(out, printCount, false);
    }
    
    public PrintstreamHandler(PrintStream out, boolean printCount, boolean printDegreeSeq) {
        this.out = out;
        this.printCount = printCount;
        this.printDegreeSeq = printDegreeSeq;
        this.count = 0;
    }

    @Override
    public void handle(IntGraph graph) {
        if (printCount) {
            out.print(count + "\t");
            count++;
        }
        out.print(graph);
        if (printDegreeSeq) {
            out.print("\t" + Arrays.toString(graph.degreeSequence(true)));
        }
        out.println();
    }

}
