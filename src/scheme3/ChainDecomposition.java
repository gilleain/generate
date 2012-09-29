package scheme3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Edge;
import model.Graph;

/**
 * Structure used for finding bridges (or cut-edges) in a graph, based on Jens Schmidt's algorithm.
 * 
 * A chain is either a cycle or path. A graph is made up of a set of these chains connected by 0 
 * or more bridges. To put it another way, any edge not part of a chain is a bridge. 
 * 
 * @author maclean
 *
 */
public class ChainDecomposition {
    
    /**
     * The chains of the graph that are cycles.
     */
    private List<List<Edge>> cycleChains;
    
    /**
     * The number of edges that are part of a cycle-chain.
     */
    private int cycleChainEdges;
    
    /**
     * The chains of the graph that are paths.
     */
    private List<List<Edge>> pathChains;
    
    /**
     * The number of edges that are part of a path-chain.
     */
    private int pathChainEdges;
    
    /**
     * The index of the vertices in a pre-order traversal of the depth-first spanning tree.
     */
    private int[] dfi;
    private int dfiIndex;
    
    /**
     * The depth-first index of the parents of each vertex.
     */
    private int[] parentDFI;
    
    /**
     * The non-spanning tree edges, indexed by their low point.
     */
    private Map<Integer, Edge> backEdges;
    
    /**
     * The edges that are not part of a chain are bridges.
     */
    private List<Edge> bridges;
    
    public ChainDecomposition(Graph g) {
        cycleChains = new ArrayList<List<Edge>>();
        cycleChainEdges = 0;
        
        pathChains = new ArrayList<List<Edge>>();
        pathChainEdges = 0;
        
        backEdges = new HashMap<Integer, Edge>();
        
        dfi = new int[g.vsize()];
        Arrays.fill(dfi, -1);
        
        parentDFI = new int[g.vsize()];
        dfiIndex = 0;
        span(0, -1, g);
//        System.out.println("DFI : " + java.util.Arrays.toString(dfi));
//        System.out.println("pDFI : " + java.util.Arrays.toString(parentDFI));
        
        findChains(g.vsize());
        findBridges(g);
    }
    
    public List<Edge> getBridges() {
        return bridges;
    }
    
    private void findBridges(Graph g) {
        BitSet visitedEdges = new BitSet(g.esize());
        bridges = new ArrayList<Edge>();
        // if all the edges are covered, there can be no bridges. If there are no chains, graph is a tree
        int totalChainEdges = cycleChainEdges + pathChainEdges; 
        if (totalChainEdges == g.esize() || totalChainEdges == 0) {
            return;
        } else {
            for (List<Edge> cycle : cycleChains) {
                setVisited(visitedEdges, cycle, g);
            }
            for (List<Edge> path : pathChains) {
                setVisited(visitedEdges, path, g);
            }
        }
        for (int i = visitedEdges.nextClearBit(0); i >= 0 && i < g.esize(); i = visitedEdges.nextClearBit(i + 1)) {
            bridges.add(g.edges.get(i));
        }
    }
    
    private void setVisited(BitSet visitedEdges, List<Edge> chain, Graph g) {
        for (Edge e : chain) {
            int indexInGraph = g.edges.indexOf(e);
            visitedEdges.set(indexInGraph);
        }
    }

    private void span(int vertex, int parent, Graph g) {
//        System.out.println(parent + "->" + vertex + " " + java.util.Arrays.toString(dfi));
        dfi[vertex] = dfiIndex;
        dfiIndex++;
        parentDFI[dfi[vertex]] = (parent == -1) ? -1 : dfi[parent];
        for (int neighbour : g.getConnected(vertex)) {
//            System.out.println("v " + vertex + " n:" + neighbour + " p? " + (neighbour == parent));
            if (neighbour == parent) continue;
            if (dfi[neighbour] == -1) { // neighbour not visited yet
                span(neighbour, vertex, g); 
            } else {
                Edge backedge = new Edge(dfi[neighbour], dfi[vertex]);
                // relies on (v, w) = (w, v)
                if (!backEdges.values().contains(backedge)) {
//                    System.out.println("adding backedge " + dfi[neighbour] + ":" + dfi[vertex]);
                    backEdges.put(dfi[neighbour], backedge);
                }
            }
        }
    }
    
    private void findChains(int n) {
        BitSet visitedVertices = new BitSet(n);
        
        for (int x : backEdges.keySet()) {
            Edge e = backEdges.get(x);
            List<Edge> chain = new ArrayList<Edge>();
            // start by adding the backedge itself
            visitedVertices.set(e.a);
            chain.add(new Edge(lookupIndex(e.a), lookupIndex(e.b)));
            int current = e.b;
            while (current != e.a && !visitedVertices.get(current)) {
                visitedVertices.set(current);
                int next = parentDFI[current]; 
                chain.add(new Edge(lookupIndex(current), lookupIndex(next)));
//                chain.add(new Edge(dfi[current], dfi[next]));
                current = next;
            }
            if (current == e.a) {
                cycleChains.add(chain);
                cycleChainEdges += chain.size();
            } else {
                pathChains.add(chain);
                pathChainEdges += chain.size();
            }
        }
    }
    
    private int lookupIndex(int dfiValue) {
        for (int i = 0; i < dfi.length; i++) {
            if (dfiValue == dfi[i]) return i;
        }
        return -1;
    }
    
    public List<List<Edge>> getCycleChains() {
        return cycleChains;
    }
    
    public int getCycleChainEdgeCount() {
        return cycleChainEdges;
    }
    
    public List<List<Edge>> getPathChains() {
        return pathChains;
    }
    
    public int getPathChainEdgeCount() {
        return pathChainEdges;
    }
}
