package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.Map;

import model.Graph;
import model.GraphSignature;

public class GraphEdgewiseGenerator {
    
    private GeneratorHandler handler;
    
    private GraphSignatureHandler signatureHandler;
    
    private ChildLister childLister;
    
    private int count;
    
    private boolean generateDisconnected;
    
    public GraphEdgewiseGenerator() {
        this(new SystemOutHandler());
    }
    
    public GraphEdgewiseGenerator(GeneratorHandler handler) {
        this(handler, false);
    }
    
    public GraphEdgewiseGenerator(GeneratorHandler handler, boolean generateDisconnected) {
        this.handler = handler;
        if (generateDisconnected) {
            signatureHandler = new DisconnectedGraphSignatureHandler();
            childLister = new DisconnectedEdgeChildLister(signatureHandler);
        } else {
            signatureHandler = new ConnectedGraphSignatureHandler();
            childLister = new ConnectedEdgeChildLister(signatureHandler);
        }
        this.generateDisconnected = generateDisconnected;
    }
    
    public void extend(Graph g, int n) {
        GraphSignature gSignature = new GraphSignature(g);
        String gCanonicalLabel = signatureHandler.getCanonicalLabel(gSignature);
        extend(g, gSignature, gCanonicalLabel, n);
    }
    
    public void extend(Graph g, GraphSignature gSignature, String gCanonicalLabel, int n) {
        Map<String, GraphSignature> children = childLister.list(g, n);
        
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
                extend(gPrime, gPrimeSignature, gPrimeCanonLabel, n);
            }
        }
    }
}
