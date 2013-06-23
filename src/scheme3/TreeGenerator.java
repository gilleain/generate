package scheme3;

import graph.model.Graph;
import graph.model.GraphBuilder;
import graph.model.GraphSignature;
import graph.model.VertexSignature;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import signature.ColoredTree;

public class TreeGenerator {
	
	private int count;
	
	public TreeGenerator() {
		count = 0;
	}
	
	public void orderlyGenerationMcKay(Graph g, int n) {
		int l = g.getVertexCount(); 
		if (l == n) {
			System.out.println(count + "\t" + g);
			count++;
		} else {
			GraphSignature gSignature = new GraphSignature(g);
			String gCanonicalLabel = gSignature.toCanonicalString();
			Set<Graph> s = new HashSet<Graph>();
			for (int start = 0; start < l; start++) {
				s.add(g.makeNew(start, l));
			}
			Map<String, GraphSignature> dupMap = removeDuplicates(s);
			for (String gPrimeCanonLabel : dupMap.keySet()) {
				Graph canonGPrime = reconstruct(gPrimeCanonLabel);
				Graph gPrimeMinusE = canonGPrime.removeLastEdge();
				GraphSignature gPrimeMinusESignature = new GraphSignature(gPrimeMinusE);
				if (gPrimeMinusESignature.toCanonicalString().equals(gCanonicalLabel)) {
					GraphSignature gPrimeSignature = dupMap.get(gPrimeCanonLabel);
					Graph gPrime = gPrimeSignature.getGraph();
					orderlyGenerationMcKay(gPrime, n);
				}
			}
		}
	}
	
	public Graph reconstruct(String canonicalLabel) {
		ColoredTree tree = VertexSignature.parse(canonicalLabel);
		GraphBuilder builder = new GraphBuilder();
		builder.makeFromColoredTree(tree);
		return builder.getProduct();
	}
	
	public Map<String, GraphSignature> removeDuplicates(Set<Graph> s) {
		Map<String, GraphSignature> canonicalGraphLabels = new HashMap<String, GraphSignature>();
		for (Graph g : s) {
			GraphSignature signature = new GraphSignature(g);
			String canonicalLabel = signature.toCanonicalString();
			if (canonicalGraphLabels.containsKey(canonicalLabel)) {
				continue;
			} else {
				canonicalGraphLabels.put(canonicalLabel, signature);
			}
		}
		return canonicalGraphLabels;
	}

}
