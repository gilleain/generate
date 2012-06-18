package generate.handler;

import model.Graph;

public class SystemOutHandler implements GeneratorHandler {
	
	private int count;
	
	private int size;
	
	private boolean rejectDisconnected;
	
	public SystemOutHandler() {
		this.count = 0;
		this.size = -1;
	}
	
	public SystemOutHandler(int size, boolean rejectDisconnected) {
		this();
		this.size = size;
		this.rejectDisconnected = rejectDisconnected;
	}

	public void handle(Graph parent, Graph graph) {
		if (size != -1 && graph.getVertexCount() != size) {
			return;
		}
		if (rejectDisconnected == false || graph.isConnected()) {
			System.out.println(count + "\t" + graph);
			count++;
		}
	}

	public void finish() {
	}

}
