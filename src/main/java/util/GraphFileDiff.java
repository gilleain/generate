package util;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphFileDiff {
    
    public static List<Graph> diff(String pathA, String pathB) throws IOException {
        return GraphFileDiff.diff(pathA, pathB, new RefinerGraphDifference());
    }
    
	public static List<Graph> diff(String pathA, String pathB, GraphDifference differ) throws IOException {
		List<Graph> difference = new ArrayList<Graph>();
		GraphFileReader readerA = new GraphFileReader(pathA);
		for (Graph graphA : readerA) {
			differ.add(graphA);
		}
		readerA.close();
		GraphDifference.Callback callback = new GraphDifference.Callback() {
            
            @Override
            public void same(Graph graphA, Graph graphB) {
//                System.out.println(dupCount + "\t" + graphB);
//                dupCount++;
            }
            
            @Override
            public void different(Graph graphA, Graph graphB) {
                System.out.println(graphB);
            }
        };
		GraphFileReader readerB = new GraphFileReader(pathB);
		for (Graph graphB : readerB) {
			differ.compare(graphB, callback);
		}
		readerB.close();
		return difference;
	}
	
	public static void main(String[] args) {
	    if (args.length > 1) {
	        try {
	            GraphDifference differ;
	            if (args.length > 2) {
	                if (args[2].equals("SIG")) {
	                    differ = new SignatureGraphDifference();
	                } else {
	                    differ = new RefinerGraphDifference();
	                }
	            } else {
	                differ = new RefinerGraphDifference();
	            }
                for (Graph g : GraphFileDiff.diff(args[0], args[1], differ)) {
                    System.out.println(g);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
	    }
	}

}
