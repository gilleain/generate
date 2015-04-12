package generate.handler;

import graph.model.IntGraph;

public interface GeneratorHandler {
	
	public void handle(IntGraph parent, IntGraph graph);

	public void finish();

}
