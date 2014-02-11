package augmentation;

import graph.model.Graph;

public class LowerObject {
	
	private Graph graph;
	
	private int vertex;
	
	public LowerObject(Graph graph, int vertex) {
		this.graph = graph;
		this.vertex = vertex;
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public int size() {
		return this.graph.getVertexCount();
	}
	
	public String toString() {
		return "<" + graph + ", {" + vertex + "}>";
	}

}
