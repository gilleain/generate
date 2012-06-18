package hybrid;

import generate.handler.GeneratorHandler;
import group.Permutation;
import group.SSPermutationGroup;
import model.Graph;
import model.GraphDiscretePartitionRefiner;
import model.GraphSignature;

public class HybridGenerator {
	
	private GeneratorHandler handler;
	
	public HybridGenerator(GeneratorHandler handler) {
		this.handler = handler;
	}
	
	public SSPermutationGroup getGroup(Graph g) {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		return refiner.getAutomorphismGroup(g);
	}
	
	public void generate(Graph graph, int n) {
		generate(null, graph, n);
		handler.finish();
	}
	
	public void generate(Graph parent, Graph graph, int n) {
		handler.handle(parent, graph);
		int l = graph.getVertexCount();
		if (l > n) return;
		int max = Math.min(l, n);
		SSPermutationGroup autG = getGroup(graph);
		GraphSignature gSignature = new GraphSignature(graph);
		String gCanonicalLabel = gSignature.toCanonicalString();
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (graph.isConnected(start, end)) {
					continue;
				} else {
//					System.out.println("checking " + graph + " + {" + start + ":" + end + "}");
					if (isMinimalInOrbit(start, end, l, graph, autG)) {
						Graph gPrime = graph.makeNew(start, end);
						GraphSignature gPrimeSignature = new GraphSignature(gPrime);
						Graph canonGPrime = gPrime.getPermutedGraph(gPrimeSignature.getCanonicalLabels());
						Graph gPrimeMinusE = canonGPrime.removeLastEdge();
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
	
	private boolean isMinimalInOrbit(int start, int end, int l, Graph g, SSPermutationGroup autG) {
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
