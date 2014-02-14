package util;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.io.FileNotFoundException;
import java.util.List;

public class GraphDuplicateChecker {
    
    public static void checkForDuplicates(List<Graph> graphs) {
        GraphDuplicateChecker.checkForDuplicates(graphs, new RefinerGraphDifference());
    }
    
    public static void checkForDuplicates(List<Graph> graphs, GraphDifference differ) {
        
        GraphDifference.Callback callback = new GraphDifference.Callback() {
            int dupCount = 0;
            
            @Override
            public void same(Graph graphA, Graph graphB) {
                System.out.println(dupCount + "\t" + graphA + "\t" + graphB);
                dupCount++;
            }
            
            @Override
            public void different(Graph graphA, Graph graphB) {
                // don't care...
            }
        };
        for (int i = 0; i < graphs.size() - 1; i++) {
            Graph graph = graphs.get(i);
            differ.add(graph);
            int j = i + 1;
            Graph otherGraph = graphs.get(j);
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
