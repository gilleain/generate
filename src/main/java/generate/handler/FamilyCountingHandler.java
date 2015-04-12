package generate.handler;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FamilyCountingHandler implements GeneratorHandler {
	
	private Map<IntGraph, List<IntGraph>> families;
	
	private boolean rejectDisconnected;
	
	public FamilyCountingHandler(Map<IntGraph, List<IntGraph>> families) {
		this.families = families;
	}
	
	public FamilyCountingHandler(Map<IntGraph, List<IntGraph>> families, boolean rejectDisconnected) {
		this(families);
		this.rejectDisconnected = rejectDisconnected;
	}

	public void handle(IntGraph parent, IntGraph child) {
		if (parent == null || (rejectDisconnected && !child.isConnected())) {
//			List<Graph> newFamily = new ArrayList<Graph>();
//			families.put(child, newFamily);
			return;
		}
		if (families.containsKey(parent)) {
			families.get(parent).add(child);
			return;
		} else {
			for (IntGraph existingParent : families.keySet()) {
				List<IntGraph> family = families.get(existingParent);
				if (family.contains(parent)) {
					family.add(child);
					return;
				}
			}
		}
		List<IntGraph> newFamily = new ArrayList<IntGraph>();
		newFamily.add(child);
		families.put(parent, newFamily);
	}

	public void finish() {
		// TODO Auto-generated method stub

	}

}
