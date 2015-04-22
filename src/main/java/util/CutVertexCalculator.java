package util;

import graph.model.IntEdge;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Determines the cut vertices of a graph. 
 * 
 * @author maclean
 *
 */
public class CutVertexCalculator {
    
    public static List<Integer> getCutVertices(IntGraph graph) {
       if (isTree(graph)) {
           return getForTree(graph); 
       } else {
           return getForCyclic(graph);
       }
    }
    
    private static List<Integer> getForTree(IntGraph graph) {
        List<Integer> cutVertices = new ArrayList<Integer>();
        List<Integer> leaves = getLeaves(graph);
        for (int x = 0; x < graph.getVertexCount(); x++) {
            if (!leaves.contains(x)) {
                cutVertices.add(x);
            }
        }
        return cutVertices;
    }
    
    private static List<Integer> getForCyclic(IntGraph graph) {
        List<Integer> cutVertices = new ArrayList<Integer>();
        List<Integer> leaves = getLeaves(graph);
        ChainDecomposition chainDecomposition = new ChainDecomposition(graph);
        List<IntEdge> bridges = chainDecomposition.getBridges();
        for (int x = 0; x < graph.getVertexCount(); x++) {
            if (!leaves.contains(x) && inBridges(x, bridges)) {
                cutVertices.add(x);
            }
        }
        return cutVertices;
    }
    
    private static boolean isTree(IntGraph graph) {
        // XXX assumes connected!
        return graph.getVertexCount() - 1 == graph.getEdgeCount(); 
    }
    
    private static boolean inBridges(int x, List<IntEdge> bridges) {
        for (IntEdge bridge : bridges) {
            if (bridge.contains(x)) {
                return true;
            }
        }
        return false;
    }
    
    private static List<Integer> getLeaves(IntGraph graph) {
        List<Integer> leaves = new ArrayList<Integer>();
        for (int x = 0; x < graph.getVertexCount(); x++) {
            if (graph.degree(x) == 1) {
                leaves.add(x);
            }
        }
        return leaves;
    }

}
