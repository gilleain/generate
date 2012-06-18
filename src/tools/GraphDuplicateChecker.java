package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Graph;
import model.GraphDiscretePartitionRefiner;

public class GraphDuplicateChecker {
	
	public void checkForDuplicates(List<Graph> graphs) {
		int n = graphs.size();
		long[] certs = new long[n];
		boolean firstPass = true;
		int uniq = n;
		for (int i = 0; i < n - 1; i++) {
			Graph graphI = graphs.get(i);
//			System.out.println("graph I " + graphI);
			long certForI;
			if (firstPass) {
				certForI = getCertFor(graphI);
				certs[i] = certForI;
			} else {
				certForI = certs[i];
			}
			boolean isUniq = true;
			List<Graph> dups = new ArrayList<Graph>();
			for (int j = i + 1; j < n; j++) {
				Graph graphJ = graphs.get(j);
				long certForJ;
				if (firstPass) {
					certForJ = getCertFor(graphJ);
					certs[j] = certForJ;
				} else {
					certForJ = certs[j];
				}
				if (certForI == certForJ) {
//					System.out.println((i + 1) + "\t" + certForI + "\t" + graphI + "\t" + 
//									   (j + 1) + "\t" + certForJ + "\t" + graphJ);
					isUniq = false;
					dups.add(graphJ);
				}
			}
			if (isUniq) {
				System.out.println("OK " + graphI);
			} else {
				uniq--;
				System.out.println("NO " + graphI + "\t" + dups);
			}
			if (firstPass) {
				firstPass = false;
			}
		}
		Arrays.sort(certs);
		System.out.println(certs.length + "\t" + uniq + "\t" + Arrays.toString(certs));
	}
	
	public long getCertFor(Graph g) {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(g);
		return refiner.getCertificate();
	}

}
