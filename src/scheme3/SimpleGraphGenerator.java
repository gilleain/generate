package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.HashMap;
import java.util.Map;

import model.Edge;
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
	
	public void extend(Graph g, int n) {
		int l = g.getVertexCount(); 

		GraphSignature gSignature = new GraphSignature(g);
		String gCanonicalLabel = gSignature.toCanonicalString();
		Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
		int max = Math.min(l, n - 1);
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (g.isConnected(start, end)) {
					continue;
				} else {
				    Graph h = g.makeNew(start, end);
				    GraphSignature signature = new GraphSignature(h);
		            String canonicalLabel = signature.toCanonicalString();
		            if (children.containsKey(canonicalLabel)) {
		                continue;
		            } else {
		                children.put(canonicalLabel, signature);
		            }
				}
			}
		}
		
		for (String gPrimeCanonLabel : children.keySet()) {
		    GraphSignature gPrimeSignature = children.get(gPrimeCanonLabel);
		    Graph gPrime = gPrimeSignature.getGraph();
		    Graph canonGPrime = reconstruct(gPrimeCanonLabel);
			if (isCanonicalAugmentation(canonGPrime, gPrimeSignature, gPrime, gCanonicalLabel)) {
			    if (gPrime.getVertexCount() == n && gPrime.isConnected()) {
			        handler.handle(g, gPrime);
		            count++;
			    }
			    extend(gPrime, n);
			}
		}
		if (l < n - 2) {
		    Graph h = g.makeNew(l, l + 1);
		    extend(h, n);
		}
	}
	
	public boolean isCanonicalAugmentation(Graph parent, Graph child) {
	    GraphSignature childSig = new GraphSignature(child);
	    Graph canonChild = getCanonicalForm(child);
	    String parentLabel = new GraphSignature(parent).toCanonicalString();
	    return isCanonicalAugmentation(canonChild, childSig, child, parentLabel);
	}
	
	public boolean isCanonicalAugmentation(
	        Graph canonGPrime, GraphSignature gPrimeSig, Graph gPrime, String gCanonicalLabel) {
	    Edge lastEdge = canonGPrime.edges.get(canonGPrime.esize() - 1);
	    int[] labels = gPrimeSig.getCanonicalLabels();
	    int lA = get(labels, lastEdge.a);
	    int lB = get(labels, lastEdge.b);
	    Graph gPrimeMinusE = gPrime.remove(gPrime.getEdge(lA, lB));
//	    System.out.println(gPrime + "\t" + Arrays.toString(labels) + "\t" + gPrimeMinusE);
        GraphSignature gPrimeMinusESignature = new GraphSignature(gPrimeMinusE);
        return gPrimeMinusESignature.toCanonicalString().equals(gCanonicalLabel);
	}
	
	private int get(int[] labels, int j) {
	    for (int i = 0; i < labels.length; i++) {
	        if (labels[i] == j) return i;
	    }
	    return -1;
	}
	
//	private int get(int[] labels, int j) {
//        return labels[j];
//    }
	
	public Graph getCanonicalForm(Graph g) {
	    String canString = new GraphSignature(g).toCanonicalString();
	    return reconstruct(canString);
	}
	
	public Graph reconstruct(String canonicalLabel) {
		ColoredTree tree = VertexSignature.parse(canonicalLabel);
		GraphBuilder builder = new GraphBuilder();
		builder.makeFromColoredTree(tree);
		return builder.getProduct();
	}

}
