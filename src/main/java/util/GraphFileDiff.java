package util;

import graph.model.Graph;
import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphFileDiff {
    
    public static List<IntGraph> diff(String pathA, String pathB) throws IOException {
        return GraphFileDiff.diff(pathA, pathB, new RefinerGraphDifference());
    }
    
	public static List<IntGraph> diff(String pathA, String pathB, GraphDifference differ) throws IOException {
		List<IntGraph> difference = new ArrayList<IntGraph>();
		GraphFileReader readerA = new GraphFileReader(pathA);
		for (IntGraph graphA : readerA) {
			differ.add(graphA);
		}
		readerA.close();
		GraphDifference.Callback callback = new GraphDifference.Callback() {
            
            @Override
            public void same(IntGraph graphA, IntGraph graphB) {
//                System.out.println(dupCount + "\t" + graphB);
//                dupCount++;
            }
            
            @Override
            public void different(IntGraph graphA, IntGraph graphB) {
                System.out.println(graphB);
            }
        };
		GraphFileReader readerB = new GraphFileReader(pathB);
		for (IntGraph graphB : readerB) {
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
