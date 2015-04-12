package generate.handler;

import graph.model.IntGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputHandler implements GeneratorHandler {

	private File fileout;

	private BufferedWriter writer;
	
	// the number of vertices the graph should have
	private int size;	// TODO : factor out into separate class!
	
	private int degree;

	private boolean rejectDisconnected;

	public FileOutputHandler(String filepath, int size) {
		this(filepath, -1, size);
	}
	
	public FileOutputHandler(String filepath, int degree, int size) {
		this(filepath, degree, size, false);
	}
	
	public FileOutputHandler(String filepath, int degree, int size, boolean rejectDisconnected) {
		fileout = new File(filepath);
		writer = null;
		this.size = size;
		this.degree = degree;
		this.rejectDisconnected = rejectDisconnected;
	}

	public void handle(IntGraph parent, IntGraph graph) {
		if (graph.vsize() != size) return;
		if (degree > 0) {
			for (int vertexIndex = 0; vertexIndex < graph.getVertexCount(); vertexIndex++) {
				if (graph.degree(vertexIndex) > degree) {
					return;
				}
			}
		}
		if (rejectDisconnected) {
//			for (Graph.Edge edge : graph.edges) {
//				if (edge.a == edge.b) return;
//			}
			if (!graph.isConnected()) { 
				return;
			}
		}
		try {
			if (writer == null) {
				writer = new BufferedWriter(new FileWriter(fileout));
			}
//			GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
//			refiner.getAutomorphismGroup(graph);
//			writer.write(refiner.getCertificate() + "\t" + graph.getSortedEdgeString());
			writer.write(graph.getSortedEdgeString());
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void finish() {
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
