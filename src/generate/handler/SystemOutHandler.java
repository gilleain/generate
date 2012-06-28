package generate.handler;

import model.Graph;

public class SystemOutHandler implements GeneratorHandler {
	
	private int count;
	
	private int size;
	
	private boolean rejectDisconnected;
	
	private boolean printDetails;
	
	public SystemOutHandler() {
		this.count = 0;
		this.size = -1;
	}
	
	public SystemOutHandler(int size, boolean rejectDisconnected) {
	    this(size, rejectDisconnected, false);
	}
	
	public SystemOutHandler(int size, boolean rejectDisconnected, boolean printDetails) {
		this();
		this.size = size;
		this.rejectDisconnected = rejectDisconnected;
		this.printDetails = printDetails;
	}

	public void handle(Graph parent, Graph graph) {
		if (size != -1 && graph.getVertexCount() != size) {
			return;
		}
		if (rejectDisconnected == false || graph.isConnected()) {
		    if (printDetails) {
		        System.out.println(count + "\t" + graph.vsize() + "\t" + graph.esize() + "\t" + graph);
		    } else {
		        System.out.println(count + "\t" + graph);
		    }
			count++;
		}
	}

	public void finish() {
	}

}
