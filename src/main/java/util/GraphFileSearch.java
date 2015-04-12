package util;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphFileSearch {
	
	public static boolean contains(IntGraph g, String filename) throws IOException {
		return GraphFileSearch.get(g, filename) != null;
	}
	
	public static IntGraph get(IntGraph g, String filename) throws IOException {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(g);
		BigInteger cert = refiner.getCertificate();
//		System.out.println("getting " + cert);
		GraphFileReader fileReader = new GraphFileReader(filename);
        for (IntGraph other : fileReader) {
			refiner = new GraphDiscretePartitionRefiner();
			refiner.getAutomorphismGroup(other);
			if (refiner.getCertificate() == cert) {
			    fileReader.close();
				return other;
			}
		}
		fileReader.close();
		return null;
	}
	
	public static List<Graph> get(int[] degreeSequence, String filename) throws IOException {
	    List<Graph> hits = new ArrayList<Graph>();
        GraphFileReader fileReader = new GraphFileReader(filename);
        for (IntGraph graph : fileReader) {
            int[] otherDegreeSeq = graph.degreeSequence(true);
            if (Arrays.equals(degreeSequence, otherDegreeSeq)) {
                hits.add(graph);
            }
        }
        fileReader.close();
        return hits;
	}
	
	// TODO : put into some utility class?
	public static int[] parse(String degreeSequenceString) {
        int end = degreeSequenceString.indexOf("]");
        String[] bits = degreeSequenceString.substring(1, end).split(","); 
        int[] degSeq = new int[bits.length];
        int i = 0;
        for (String bit : bits) {
            degSeq[i] = Integer.parseInt(bit.trim());
            i++;
        }
        return degSeq;
    }
	
	public static void main(String[] args) {
	    try {
	        if (args[1].equals("-g")) {
	            IntGraph g = new IntGraph(args[2]);
	            System.out.println(GraphFileSearch.get(g, args[0]));
	        } else if (args[1].equals("-d")) {
	            int[] degreeSequence = GraphFileSearch.parse(args[2]);
	            List<Graph> hits = GraphFileSearch.get(degreeSequence, args[0]);
	            if (hits.size() == 0) {
	                System.out.println("None found");
	            } else {
	                for (Graph hit : hits) {
	                    System.out.println(hit);
	                }
	            }
	        }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
