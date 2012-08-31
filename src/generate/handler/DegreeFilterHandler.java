package generate.handler;

import model.Edge;
import model.Graph;

public class DegreeFilterHandler implements GeneratorHandler {
	
	/**
	 * Filter out graphs where any vertex is greater than this value
	 */
	private int maxDegree;
	
	// the number of vertices the graph should have
	private int size;	// TODO : factor out into separate class!
	
	private int count;
	
	private boolean rejectDisconnected;
	
	public DegreeFilterHandler(int maxDegree, int size) {
		this(maxDegree, size, false);
	}
	
	public DegreeFilterHandler(int maxDegree, int size, boolean rejectDisconnected) {
		this.maxDegree = maxDegree;
		this.size = size; // TODO : factor out into class
		this.count = 0;
		this.rejectDisconnected = rejectDisconnected;
	}

	public void handle(Graph parent, Graph graph) {
		if (graph.getVertexCount() != size) return;	// TODO : factor out into class
		for (int vertexIndex = 0; vertexIndex < graph.getVertexCount(); vertexIndex++) {
			if (graph.degree(vertexIndex) > maxDegree) {
				return;
			}
		}
		if (rejectDisconnected) {
			for (Edge e : graph.edges) {
				if (e.a == e.b) return;
			}
			if (!graph.isConnected()) {
				return;
			}
		}
//		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
//		refiner.getAutomorphismGroup(graph);
//		System.out.println(count + "\t" + refiner.getCertificate() + "\t" + graph);
		System.out.println(count + "\t" + graph);
		count++;
	}

	public void finish() {
	}

}
