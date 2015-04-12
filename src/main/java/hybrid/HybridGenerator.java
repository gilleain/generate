package hybrid;

import generate.handler.GeneratorHandler;
import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import graph.model.GraphSignature;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

public class HybridGenerator {
	
	private GeneratorHandler handler;
	
	public HybridGenerator(GeneratorHandler handler) {
		this.handler = handler;
	}
	
	public PermutationGroup getGroup(IntGraph g) {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		return refiner.getAutomorphismGroup(g);
	}
	
	public void generate(IntGraph graph, int n) {
		generate(null, graph, n);
		handler.finish();
	}
	
	public void generate(IntGraph parent, IntGraph graph, int n) {
		handler.handle(parent, graph);
		int l = graph.getVertexCount();
		if (l > n) return;
		int max = Math.min(l, n);
		PermutationGroup autG = getGroup(graph);
		GraphSignature gSignature = new GraphSignature(graph);
		String gCanonicalLabel = gSignature.toCanonicalString();
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (graph.isConnected(start, end)) {
					continue;
				} else {
//					System.out.println("checking " + graph + " + {" + start + ":" + end + "}");
					if (isMinimalInOrbit(start, end, l, graph, autG)) {
					    IntGraph gPrime = graph.makeNew(start, end);
						GraphSignature gPrimeSignature = new GraphSignature(gPrime);
						IntGraph canonGPrime = gPrime.getPermutedGraph(gPrimeSignature.getCanonicalLabels());
						IntGraph gPrimeMinusE = canonGPrime.removeLastEdge();
						GraphSignature gPrimeMinusESignature = new GraphSignature(gPrimeMinusE);
						String gPrimeMinusESigString = gPrimeMinusESignature.toCanonicalString();
//						System.out.println(gPrimeMinusESigString + " =? " + gCanonicalLabel 
//								+ " for C(G') = " + canonGPrime
//								+ " and C(G') - e = " + gPrimeMinusE);
						if (gPrimeMinusESigString.equals(gCanonicalLabel)) {
							generate(graph, gPrime, n);
						}
					}
				}
			}
		}
	}
	
	private boolean isMinimalInOrbit(int start, int end, int l, Graph g, PermutationGroup autG) {
		for (Permutation p : autG.all()) {
			int startPrime = p.get(start);
			if (end == l) {
				if (startPrime < start) {
//					System.out.println(g + " " + start + ":" + end + " not minimal compared to ");
					return false;
				}
			} else {
				int endPrime = p.get(end);
				if (startPrime < start || (startPrime <= start && endPrime < end)) {
//				System.out.println(g + " " + start + ":" + end + " not minimal compared to " + startPrime + ":" + endPrime);
					return false;
				}
			}
		}
		return true;
	}

}
