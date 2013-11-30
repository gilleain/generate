package tools;

import graph.model.Graph;
import graph.model.GraphFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphFileDiff {
    
    public interface GraphDifference {
        public void add(Graph graph);
        public void compare(Graph graph, List<Graph> difference);
    }
    
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
		GraphFileReader readerB = new GraphFileReader(pathB);
		for (Graph graphB : readerB) {
			differ.compare(graphB, difference);
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
