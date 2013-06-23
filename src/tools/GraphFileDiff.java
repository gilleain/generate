package tools;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphFileDiff {
	
	public static List<Graph> diff(String pathA, String pathB) throws IOException {
		List<Graph> difference = new ArrayList<Graph>();
		Map<Long, Graph> graphsA = new HashMap<Long, Graph>();
		BufferedReader readerA = new BufferedReader(new FileReader(new File(pathA)));
		String line;
		while ((line = readerA.readLine()) != null) {
			GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
			Graph graphA = new Graph(line); 
			refiner.getAutomorphismGroup(graphA);
			graphsA.put(refiner.getCertificate(), graphA);
		}
		readerA.close();
		BufferedReader readerB = new BufferedReader(new FileReader(new File(pathB)));
		while ((line = readerB.readLine()) != null) {
			Graph graphB = new Graph(line);
			GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
			refiner.getAutomorphismGroup(graphB);
			long certB = refiner.calculateCertificate(refiner.getFirst());
			if (graphsA.containsKey(certB)) {
				Graph graphA = graphsA.get(certB);
				System.out.println(certB + " " + graphA + " = " + graphB);
			} else {
				difference.add(graphB);
			}
		}
		readerB.close();
		return difference;
	}
	
	public static void main(String[] args) {
	    if (args.length > 1) {
	        try {
                for (Graph g : GraphFileDiff.diff(args[0], args[1])) {
                    System.out.println(g);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
	    }
	}

}
