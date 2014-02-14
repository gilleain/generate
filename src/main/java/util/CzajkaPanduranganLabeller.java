package util;

import graph.model.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unfortunately, this only works with some probability on random graphs. Or, to
 * put it another way, given an input graph, it may return a labelling, or it 
 * may fail.
 *  
 * @author maclean
 *
 */
public class CzajkaPanduranganLabeller {
    
    public static void label(Graph graph) {
        System.out.println(graph);
        // 1 Compute vertex degrees
        Map<Integer, List<Integer>> neighborhoods = graph.getConnectionTable();
        System.out.println(neighborhoods);
        
        // 2 Compute degree neighborhoods for each vertex
        List<List<Integer>> degreeNeighborhoods = new ArrayList<List<Integer>>();
//        for (List<Integer> neighborhood : neighborhoods) {
//            List<Integer> degreeNeighborhood = new ArrayList<Integer>();
//            for (int neighbor : neighborhood) {
//                degreeNeighborhood.add(neighborhoods.get(neighbor).size());
//            }
//            degreeNeighborhoods.add(degreeNeighborhood);
//            System.out.println(degreeNeighborhood);
//        }
        
        // 3 Sort vertices by degree neighborhood in lexicographic order
        
        // 4 If the degree neighborhoods are not distinct, FAIL
        
        // 5 Number the vertices in the sorted order
    }
    
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.makeEdge(0, 1);
        graph.makeEdge(1, 2);
        graph.makeEdge(2, 3);
        CzajkaPanduranganLabeller.label(graph);
    }

}
