package fragments;

import graph.model.Edge;
import graph.model.Graph;

import java.util.HashMap;
import java.util.Map;

/**
 * A higher-level order to a graph, essentially just a tree of parts - blocks, trees - where each
 * high-level vertex is mapped to a low-level 2-connected block or 1-connected tree. With these 
 * mappings in place it only remains to make the attachments between free vertices of the parts.
 * 
 * @author maclean
 *
 */
public class MetaTree {
    
    private Graph tree;
    
    private Map<Integer, Graph> partMapping;
    
    /**
     * Map between the lower-numbered vertex of an edge of the meta-tree 
     * and a vertex of one of the parts.
     */
    private Map<Integer, Integer> lowerAttachments;

    /**
     * Map between the higher-numbered vertex of an edge of the meta-tree 
     * and a vertex of one of the parts.
     */
    private Map<Integer, Integer> upperAttachments;

    public MetaTree(Graph tree) {
        this.tree = tree;
        this.partMapping = new HashMap<Integer, Graph>();
        this.lowerAttachments = new HashMap<Integer, Integer>();
        this.upperAttachments = new HashMap<Integer, Integer>();
    }
    
    public void addPartMapping(int treeVertex, Graph part) {
        this.partMapping.put(treeVertex, part);
    }
    
    public void makeLowerAttachment(int edgeIndex, int partVertexIndex) {
        this.lowerAttachments.put(edgeIndex, partVertexIndex);
    }
    
    public void makeUpperAttachment(int edgeIndex, int partVertexIndex) {
        this.upperAttachments.put(edgeIndex, partVertexIndex);
    }
    
    public Graph getGraph() {
        Graph graph = new Graph();
        for (int edgeIndex = 0; edgeIndex < tree.esize(); edgeIndex++) {
            Edge edge = tree.edges.get(edgeIndex);
            int lower = (edge.a < edge.b)? edge.a : edge.b;
            int upper = (edge.a < edge.b)? edge.b : edge.a;
            
            Graph lowerPart = partMapping.get(lower);
            add(graph, lowerPart);
            
            Graph upperPart = partMapping.get(upper);
            add(graph, upperPart);
            
            int lowerPartAttachment = lowerAttachments.get(lower);
            int upperPartAttachment = upperAttachments.get(upper);
            graph.makeEdge(lowerPartAttachment, upperPartAttachment);
        }
        return graph;
    }
    
    // TODO : replace with graph method?
    private void add(Graph parent, Graph child) {
        int max = parent.getVertexCount();
        for (Edge e : child.edges) {
            parent.makeEdge(max + e.a, max + e.b);
        }
    }
    
}
