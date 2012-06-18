package coloring;

import group.Permutation;
import group.SSPermutationGroup;
import model.Graph;
import model.Graph.Edge;

/**
 * Convert a permutation group of a graphs vertices into a permutation group of its edges.
 * 
 * @author maclean
 *
 */
public class VertexToEdgePermutationConverter {
    
    public static SSPermutationGroup convert(Graph graph, SSPermutationGroup vertexGroup) {
        int e = graph.edges.size();
        SSPermutationGroup edgeGroup = new SSPermutationGroup(e);
        
        // hmm - inefficient! can't we iterate through just the generators instead?
        for (Permutation pV : vertexGroup.all()) {
            int[] pE = new int[e];
            int eIndex = 0;
            for (Edge edge : graph.edges) {
                int pVA = pV.get(edge.a);
                int pVB = pV.get(edge.b);
                int pIndex = graph.getEdgeIndex(pVA, pVB);
                pE[eIndex] = pIndex;
                eIndex++;
            }
//            System.out.println(pV.toCycleString() + " -> " + new Permutation(pE).toCycleString());
            edgeGroup.enter(new Permutation(pE));
        }
        return edgeGroup;
    }
    
}
