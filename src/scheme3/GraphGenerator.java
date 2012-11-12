package scheme3;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.Map;

import scheme3.lister.ChildLister;
import scheme3.lister.ConnectedEdgeFilteringChildLister;
import scheme3.lister.ConnectedEdgeSymmetryChildLister;
import scheme3.lister.ConnectedVertexFilteringChildLister;
import scheme3.lister.ConnectedVertexSymmetryChildLister;
import scheme3.lister.DisconnectedEdgeFilteringChildLister;
import scheme3.signature.ConnectedEdgeSignatureHandler;
import scheme3.signature.ConnectedVertexSignatureHandler;
import scheme3.signature.DisconnectedEdgeSignatureHandler;
import scheme3.signature.GraphSignatureHandler;

import model.Graph;
import model.GraphSignature;

public class GraphGenerator {
    
    private GeneratorHandler handler;
    
    private GraphSignatureHandler signatureHandler;
    
    private ChildLister childLister;
    
    private boolean generateDisconnected;
    
    private boolean byVertex;
    
    private boolean doFilter;
    
    private int degMax;
    
    public GraphGenerator() {
        this(new SystemOutHandler());
    }
    
    public GraphGenerator(GeneratorHandler handler) {
        this(handler, false, false);
    }
    
    public GraphGenerator(GeneratorHandler handler, boolean byVertex, boolean generateDisconnected) {
        this(handler, byVertex, generateDisconnected, true);
    }
    
    public GraphGenerator(GeneratorHandler handler, boolean byVertex, boolean generateDisconnected, boolean doFilter) {
        this(handler, byVertex, generateDisconnected, doFilter, 0);
    }
    
    public GraphGenerator(GeneratorHandler handler, boolean byVertex, boolean generateDisconnected, boolean doFilter, int degMax) {
        this.handler = handler;
        this.generateDisconnected = generateDisconnected;
        this.byVertex = byVertex;
        this.doFilter = doFilter;
        this.degMax = degMax;
        
        if (generateDisconnected) {
            signatureHandler = new DisconnectedEdgeSignatureHandler();
            childLister = new DisconnectedEdgeFilteringChildLister(signatureHandler);
        } else {
            if (byVertex) {
                signatureHandler = new ConnectedVertexSignatureHandler();
                if (doFilter) {
                    childLister = new ConnectedVertexFilteringChildLister(signatureHandler);
                } else {
                    childLister = new ConnectedVertexSymmetryChildLister(signatureHandler);
                }
            } else {
                signatureHandler = new ConnectedEdgeSignatureHandler();
                if (doFilter) {
                    childLister = new ConnectedEdgeFilteringChildLister(signatureHandler);
                } else {
                    childLister = new ConnectedEdgeSymmetryChildLister(signatureHandler);
                }
            }
        }
        childLister.setMaxDegree(degMax);
    }
    
    public boolean isGenerateDisconnected() {
        return generateDisconnected;
    }
    
    public boolean isByVertex() {
        return byVertex;
    }
    
    public boolean isDoFilter() {
        return doFilter;
    }
    
    public int getDegMax() {
        return degMax;
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
                }
                if (!byVertex || (byVertex && gPrime.vsize() < n)) {
                    extend(gPrime, gPrimeSignature, gPrimeCanonLabel, n);
                }
            }
        }
    }
}
