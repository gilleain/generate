package augmentation;

import graph.model.IntGraph;

public class LowerObject {
	
	private IntGraph graph;
	
	private int vertex;
	
	public LowerObject(IntGraph graph, int vertex) {
		this.graph = graph;
		this.vertex = vertex;
	}
	
	public IntGraph getGraph() {
		return this.graph;
	}
	
	public int size() {
		return this.graph.getVertexCount();
	}
	
	public String toString() {
		return "<" + graph + ", {" + vertex + "}>";
	}

}
