package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Graph;
import model.GraphBuilder;
import model.GraphSignature;
import model.VertexSignature;
import signature.ColoredTree;

public class SimpleGraphGenerator {
	
	private GeneratorHandler handler;
	
	private int count;
	
	public SimpleGraphGenerator() {
		this(new SystemOutHandler());
	}
	
	public SimpleGraphGenerator(GeneratorHandler handler) {
		this.handler = handler;
	}
	
	public void orderlyGenerationMcKay(Graph g, int n) {
		orderlyGenerationMcKay(null, g, n);
	}
	
	public void orderlyGenerationMcKay(Graph parent, Graph g, int n) {
		int l = g.getVertexCount(); 
		if (l == n) {
			handler.handle(parent, g);
			count++;
		} 
		GraphSignature gSignature = new GraphSignature(g);
		String gCanonicalLabel = gSignature.toCanonicalString();
		Set<Graph> s = new HashSet<Graph>();
		int max = Math.min(l, n);
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (g.isConnected(start, end)) {
					continue;
				} else {
					s.add(g.makeNew(start, end));
				}
			}
		}
		Map<String, GraphSignature> dupMap = removeDuplicates(s);
		for (String gPrimeCanonLabel : dupMap.keySet()) {
			Graph canonGPrime = reconstruct(gPrimeCanonLabel);
			Graph gPrimeMinusE = canonGPrime.removeLastEdge();
			GraphSignature gPrimeMinusESignature = new GraphSignature(gPrimeMinusE);
			if (gPrimeMinusESignature.toCanonicalString().equals(gCanonicalLabel)) {
				GraphSignature gPrimeSignature = dupMap.get(gPrimeCanonLabel);
				Graph gPrime = gPrimeSignature.getGraph();
				orderlyGenerationMcKay(g, gPrime, n);
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
