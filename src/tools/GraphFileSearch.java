package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.Graph;
import model.GraphDiscretePartitionRefiner;

public class GraphFileSearch {
	
	public static boolean contains(Graph g, String filename) throws IOException {
		return GraphFileSearch.get(g, filename) != null;
	}
	
	public static Graph get(Graph g, String filename) throws IOException {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(g);
		long cert = refiner.getCertificate();
		System.out.println("getting " + cert);
		BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
		String line;
		while ((line = reader.readLine()) != null) {
			Graph other = new Graph(line);
			refiner = new GraphDiscretePartitionRefiner();
			refiner.getAutomorphismGroup(other);
			if (refiner.getCertificate() == cert) {
				return other;
			}
		}
		return null;
	}

}
