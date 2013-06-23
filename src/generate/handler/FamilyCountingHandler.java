package generate.handler;

import graph.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FamilyCountingHandler implements GeneratorHandler {
	
	private Map<Graph, List<Graph>> families;
	
	private boolean rejectDisconnected;
	
	public FamilyCountingHandler(Map<Graph, List<Graph>> families) {
		this.families = families;
	}
	
	public FamilyCountingHandler(Map<Graph, List<Graph>> families, boolean rejectDisconnected) {
		this(families);
		this.rejectDisconnected = rejectDisconnected;
	}

	public void handle(Graph parent, Graph child) {
		if (parent == null || (rejectDisconnected && !child.isConnected())) {
//			List<Graph> newFamily = new ArrayList<Graph>();
//			families.put(child, newFamily);
			return;
		}
		if (families.containsKey(parent)) {
			families.get(parent).add(child);
			return;
		} else {
			for (Graph existingParent : families.keySet()) {
				List<Graph> family = families.get(existingParent);
				if (family.contains(parent)) {
					family.add(child);
					return;
				}
			}
		}
		List<Graph> newFamily = new ArrayList<Graph>();
		newFamily.add(child);
		families.put(parent, newFamily);
	}

	public void finish() {
		// TODO Auto-generated method stub

	}

}
