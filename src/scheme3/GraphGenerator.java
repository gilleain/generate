package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.Map;

import model.Graph;
import model.GraphSignature;

public class GraphGenerator {
    
    private GeneratorHandler handler;
    
    private GraphSignatureHandler signatureHandler;
    
    private ChildLister childLister;
    
    private int count;
    
    private boolean generateDisconnected;
    
    private boolean byVertex;
    
    public GraphGenerator() {
        this(new SystemOutHandler());
    }
    
    public GraphGenerator(GeneratorHandler handler) {
        this(handler, false, false);
    }
    
    public GraphGenerator(GeneratorHandler handler, boolean byVertex, boolean generateDisconnected) {
        this.handler = handler;
        this.byVertex = byVertex;
        if (generateDisconnected) {
            signatureHandler = new DisconnectedEdgeSignatureHandler();
            childLister = new DisconnectedEdgeFilteringChildLister(signatureHandler);
        } else {
            if (byVertex) {
                signatureHandler = new ConnectedVertexSignatureHandler();
//                childLister = new ConnectedVertexFilteringChildLister(signatureHandler);
                childLister = new ConnectedVertexSymmetryChildLister(signatureHandler);
            } else {
                signatureHandler = new ConnectedEdgeSignatureHandler();
                childLister = new ConnectedEdgeFilteringChildLister(signatureHandler);
            }
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
                    g, canonGPrime, gPrimeSignature, gPrime, gCanonicalLabel)) {
                if (gPrime.getVertexCount() == n && (!generateDisconnected || g.isConnected())) {
                    handler.handle(g, gPrime);
                    count++;
                }
                if (!byVertex || (byVertex && gPrime.vsize() < n)) {
                    extend(gPrime, gPrimeSignature, gPrimeCanonLabel, n);
                }
            }
        }
    }
}
