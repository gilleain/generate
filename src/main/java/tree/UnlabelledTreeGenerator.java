package tree;

import graph.model.IntGraph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnlabelledTreeGenerator {
	
	public static List<IntGraph> generate(int n) {
		List<IntGraph> unlabelledTrees = new ArrayList<IntGraph>();
		int maxRank = (int) Math.pow(n, n - 2);
		List<String> certificates = new ArrayList<String>();
		for (int rank = 0; rank < maxRank; rank++) {
		    IntGraph labelledTree = PruferGenerator.rankToTree(rank, n);
			String certificate = TreeCertificateMaker.treeToCertificate(labelledTree);
			if (certificates.contains(certificate)) {
				continue;
			} else {
				certificates.add(certificate);
				unlabelledTrees.add(labelledTree);
			}
		}
		return unlabelledTrees;
	}
	
	public static Map<String, IntGraph> generateWithCertificate(int n) {
		Map<String, IntGraph> unlabelledTrees = new HashMap<String, IntGraph>();
		int maxRank = (int) Math.pow(n, n - 2);
		for (int rank = 0; rank < maxRank; rank++) {
		    IntGraph labelledTree = PruferGenerator.rankToTree(rank, n);
			String certificate = TreeCertificateMaker.treeToCertificate(labelledTree);
			if (unlabelledTrees.containsKey(certificate)) {
				continue;
			} else {
				unlabelledTrees.put(certificate, labelledTree);
			}
		}
		return unlabelledTrees;
	}

}
