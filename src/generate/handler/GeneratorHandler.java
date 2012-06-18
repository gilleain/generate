package generate.handler;

import model.Graph;

public interface GeneratorHandler {
	
	public void handle(Graph parent, Graph graph);

	public void finish();

}
