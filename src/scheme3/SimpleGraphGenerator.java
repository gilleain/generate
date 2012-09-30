package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.HashMap;
import java.util.Map;

import model.Graph;
import model.GraphSignature;

public class SimpleGraphGenerator {
	
	private GeneratorHandler handler;
	
	private GraphSignatureHandler signatureHandler;
	
	private int count;

    private boolean generateDisconnected;
	
	public SimpleGraphGenerator() {
		this(new SystemOutHandler());
	}
	
	public SimpleGraphGenerator(GeneratorHandler handler) {
		this(handler, false);
	}
	
	public SimpleGraphGenerator(GeneratorHandler handler, boolean generateDisconnected) {
        this.handler = handler;
        if (generateDisconnected) {
            signatureHandler = new DisconnectedGraphSignatureHandler();
        } else {
            signatureHandler = new ConnectedGraphSignatureHandler();
        }
        this.generateDisconnected = generateDisconnected;
    }
	
	public void extend(Graph g, int n) {
		int l = g.getVertexCount(); 

		GraphSignature gSignature = new GraphSignature(g);
		String gCanonicalLabel = signatureHandler.getCanonicalLabel(gSignature);
		Map<String, GraphSignature> children = new HashMap<String, GraphSignature>();
		int max = Math.min(l, n - 1);
		for (int start = 0; start < l; start++) {
			for (int end = start + 1; end <= max; end++) {
				if (g.isConnected(start, end)) {
					continue;
				} else {
				    Graph h = g.makeNew(start, end);
				    GraphSignature signature = new GraphSignature(h);
		            String canonicalLabel = signatureHandler.getCanonicalLabel(signature);
		            if (children.containsKey(canonicalLabel)) {
		                continue;
		            } else {
		                children.put(canonicalLabel, signature);
		            }
				}
			}
		}
		
		for (String gPrimeCanonLabel : children.keySet()) {
		    GraphSignature gPrimeSignature = children.get(gPrimeCanonLabel);
		    Graph gPrime = gPrimeSignature.getGraph();
		    Graph canonGPrime = signatureHandler.reconstruct(gPrimeCanonLabel);
			if (signatureHandler.isCanonicalAugmentation(
			        canonGPrime, gPrimeSignature, gPrime, gCanonicalLabel)) {
			    if (gPrime.getVertexCount() == n && (!generateDisconnected || g.isConnected())) {
			        handler.handle(g, gPrime);
		            count++;
			    }
			    extend(gPrime, n);
			}
		}
		
		if (generateDisconnected) {
		    if (l < n - 2) {
		        Graph h = g.makeNew(l, l + 1);
		        extend(h, n);
		    }
		}
	}
}
