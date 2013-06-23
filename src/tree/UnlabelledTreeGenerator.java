package tree;

import graph.model.Graph;
import graph.tree.TreeCertificateMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnlabelledTreeGenerator {
	
	public static List<Graph> generate(int n) {
		List<Graph> unlabelledTrees = new ArrayList<Graph>();
		int maxRank = (int) Math.pow(n, n - 2);
		List<String> certificates = new ArrayList<String>();
		for (int rank = 0; rank < maxRank; rank++) {
			Graph labelledTree = PruferGenerator.rankToTree(rank, n);
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
	
	public static Map<String, Graph> generateWithCertificate(int n) {
		Map<String, Graph> unlabelledTrees = new HashMap<String, Graph>();
		int maxRank = (int) Math.pow(n, n - 2);
		for (int rank = 0; rank < maxRank; rank++) {
			Graph labelledTree = PruferGenerator.rankToTree(rank, n);
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
