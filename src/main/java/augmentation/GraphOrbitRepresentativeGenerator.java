package augmentation;

import graph.model.Graph;
import graph.model.IntGraph;

import java.util.SortedSet;

public class GraphOrbitRepresentativeGenerator extends OrbitRepresentativeGenerator<Graph> {

	private IntGraph graph;
	
	private int maxDegree;
	
	public GraphOrbitRepresentativeGenerator() {
		maxDegree = -1;
	}
	
	public GraphOrbitRepresentativeGenerator(int maxDegree) {
		this.maxDegree = maxDegree;
	}
	
	public void setGraph(IntGraph graph) {
		this.graph = graph;
	}
	
	@Override
	public boolean representativeIsCandidate(int orbitRep) {
		return maxDegree < 0 || graph.degree(orbitRep) < maxDegree;
	}
	
	@Override
	public boolean combinationIsValid(SortedSet<Integer> combination) {
		return maxDegree < 0 || combination.size() <= maxDegree;
	}

}
