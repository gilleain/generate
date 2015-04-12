package scheme3;

import graph.model.Graph;
import graph.model.GraphBuilder;
import graph.model.GraphSignature;
import graph.model.IntGraph;
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
	
	public void orderlyGenerationMcKay(IntGraph g, int n) {
		int l = g.getVertexCount(); 
		if (l == n) {
			System.out.println(count + "\t" + g);
			count++;
		} else {
			GraphSignature gSignature = new GraphSignature(g);
			String gCanonicalLabel = gSignature.toCanonicalString();
			Set<IntGraph> s = new HashSet<IntGraph>();
			for (int start = 0; start < l; start++) {
				s.add(g.makeNew(start, l));
			}
			Map<String, GraphSignature> dupMap = removeDuplicates(s);
			for (String gPrimeCanonLabel : dupMap.keySet()) {
			    IntGraph canonGPrime = reconstruct(gPrimeCanonLabel);
			    IntGraph gPrimeMinusE = canonGPrime.removeLastEdge();
			    GraphSignature gPrimeMinusESignature = new GraphSignature(gPrimeMinusE);
				if (gPrimeMinusESignature.toCanonicalString().equals(gCanonicalLabel)) {
					GraphSignature gPrimeSignature = dupMap.get(gPrimeCanonLabel);
					IntGraph gPrime = (IntGraph)gPrimeSignature.getGraph();
					orderlyGenerationMcKay(gPrime, n);
				}
			}
		}
	}
	
	public IntGraph reconstruct(String canonicalLabel) {
		ColoredTree tree = VertexSignature.parse(canonicalLabel);
		GraphBuilder builder = new GraphBuilder();
		builder.makeFromColoredTree(tree);
		return builder.getProduct();
	}
	
	public Map<String, GraphSignature> removeDuplicates(Set<IntGraph> s) {
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
