package util;

import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.FileNotFoundException;
import java.util.List;

public class GraphDuplicateChecker {
    
    public static void checkForDuplicates(List<IntGraph> graphs) {
        GraphDuplicateChecker.checkForDuplicates(graphs, new RefinerGraphDifference());
    }
    
    public static void checkForDuplicates(List<IntGraph> graphs, GraphDifference differ) {
        
        GraphDifference.Callback callback = new GraphDifference.Callback() {
            int dupCount = 0;
            
            @Override
            public void same(IntGraph graphA, IntGraph graphB) {
                System.out.println(dupCount + "\t" + graphA + "\t" + graphB);
                dupCount++;
            }
            
            @Override
            public void different(IntGraph graphA, IntGraph graphB) {
                // don't care...
            }
        };
        for (int i = 0; i < graphs.size() - 1; i++) {
            IntGraph graph = graphs.get(i);
            differ.add(graph);
            int j = i + 1;
            IntGraph otherGraph = graphs.get(j);
            differ.compare(otherGraph, callback);
        }
    }
	
	public static void main(String[] args) throws FileNotFoundException {
	    GraphDifference differ;
	    if (args.length > 1) {
	        if (args[1].equals("SIG")) {
	            differ = new SignatureGraphDifference();
	        } else {
	            differ = new RefinerGraphDifference();
	        }
	    } else {
	        differ = new RefinerGraphDifference();
	    }
	    GraphDuplicateChecker.checkForDuplicates(GraphFileReader.readAll(args[0]), differ);
	}

}
