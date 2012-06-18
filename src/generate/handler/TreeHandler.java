package generate.handler;

import model.Graph;

public class TreeHandler implements GeneratorHandler {
	
	private int size;
	
	private int count;
	
	public TreeHandler(int size) {
		this.size = size;
		this.count = 0;
	}

	public void handle(Graph parent, Graph graph) {
		if (graph.getVertexCount() == size) {
			System.out.println(count + "\t" + parent + "\t" + graph);
			count++;
		}
	}

	public void finish() {
		// TODO Auto-generated method stub
	}

}
