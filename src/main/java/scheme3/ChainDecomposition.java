package scheme3;

import graph.model.Graph;
import graph.model.IntEdge;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<List<IntEdge>> cycleChains;
    
    /**
     * The number of edges that are part of a cycle-chain.
     */
    private int cycleChainEdges;
    
    /**
     * The chains of the graph that are paths.
     */
    private List<List<IntEdge>> pathChains;
    
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
    private int[] parentIndex;
    
    /**
     * The non-spanning tree edges, indexed by their low point.
     */
    private Map<Integer, List<IntEdge>> backEdges;
    
    /**
     * The edges that are not part of a chain are bridges.
     */
    private List<IntEdge> bridges;
    
    public ChainDecomposition(IntGraph g) {
        int vsize = g.getVertexCount();
        cycleChains = new ArrayList<List<IntEdge>>();
        cycleChainEdges = 0;
        
        pathChains = new ArrayList<List<IntEdge>>();
        pathChainEdges = 0;
        
        backEdges = new HashMap<Integer, List<IntEdge>>();
        
        dfi = new int[vsize];
        Arrays.fill(dfi, -1);
        
        parentIndex = new int[vsize];
        dfiIndex = 0;
        span(0, -1, g);
        
        findChains(g.getVertexCount());
        findBridges(g);
    }
    
    public List<IntEdge> getBridges() {
        return bridges;
    }
    
    private void findBridges(IntGraph g) {
        int esize = g.getEdgeCount();
        BitSet visitedEdges = new BitSet(esize);
        bridges = new ArrayList<IntEdge>();
        // if all the edges are covered, there can be no bridges
        // if there are no chains, graph is a tree
        int totalChainEdges = cycleChainEdges + pathChainEdges; 
        if (totalChainEdges == esize || totalChainEdges == 0) {
            return;
        } else {
            for (List<IntEdge> cycle : cycleChains) {
                setVisited(visitedEdges, cycle, g);
            }
            for (List<IntEdge> path : pathChains) {
                setVisited(visitedEdges, path, g);
            }
        }
        for (int i = visitedEdges.nextClearBit(0); i >= 0 && i < esize; i = visitedEdges.nextClearBit(i + 1)) {
            bridges.add(g.edges.get(i));
        }
    }
    
    private void setVisited(BitSet visitedEdges, List<IntEdge> chain, IntGraph g) {
        for (IntEdge e : chain) {
            int indexInGraph = g.edges.indexOf(e);
            visitedEdges.set(indexInGraph);
        }
    }

    private void span(int vertex, int parent, Graph g) {
        dfi[vertex] = dfiIndex;
        dfiIndex++;
        parentIndex[vertex] = parent;
        for (int neighbour : g.getConnected(vertex)) {
            if (neighbour == parent) continue;
            if (dfi[neighbour] == -1) { // neighbour not visited yet
                span(neighbour, vertex, g); 
            } else {
                IntEdge backedge = new IntEdge(neighbour, vertex);
                // assumes that dfi[neighbour] is always lower than dfi[vertex]
                int key = dfi[neighbour];
                List<IntEdge> edgesForVertex;
                boolean addEdge = true;
                if (backEdges.containsKey(key)) {
                    edgesForVertex = backEdges.get(key);
                    // relies on (v, w) = (w, v)
                    if (edgesForVertex.contains(backedge)) {
                        addEdge = false;
                    }
                } else {
                    edgesForVertex = new ArrayList<IntEdge>();
                    backEdges.put(key, edgesForVertex);
                }
                    
                if (backEdges.containsKey(dfi[vertex])) {
                    // relies on (v, w) = (w, v)
                    if (backEdges.get(dfi[vertex]).contains(backedge)) {
                        addEdge = false;
                    }
                }
                if (addEdge) {
                    edgesForVertex.add(backedge);
                }
            }
        }
    }
    
    private void findChains(int n) {
        BitSet visitedVertices = new BitSet(n);
        
        // run through the backedges starting with the lowest
        for (int v : backEdges.keySet()) {
            List<IntEdge> edges = backEdges.get(v);
            for (IntEdge e : edges) {
                List<IntEdge> chain = new ArrayList<IntEdge>();
                // start by adding the backedge itself
                visitedVertices.set(e.a);
                chain.add(new IntEdge(e.a, e.b));
                int current = e.b;
                while (current != e.a && !visitedVertices.get(current)) {
                    visitedVertices.set(current);
                    int next = parentIndex[current];
                    chain.add(new IntEdge(current, next));
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
    }
    
    public List<List<IntEdge>> getCycleChains() {
        return cycleChains;
    }
    
    public int getCycleChainEdgeCount() {
        return cycleChainEdges;
    }
    
    public List<List<IntEdge>> getPathChains() {
        return pathChains;
    }
    
    public int getPathChainEdgeCount() {
        return pathChainEdges;
    }
}
