package coloring;

import graph.model.Edge;
import graph.model.Graph;
import group.Permutation;
import group.PermutationGroup;

/**
 * Convert a permutation group of a graphs vertices into a permutation group of its edges.
 * 
 * @author maclean
 *
 */
public class VertexToEdgePermutationConverter {
    
    public static PermutationGroup convert(Graph graph, PermutationGroup vertexGroup) {
        int e = graph.edges.size();
        PermutationGroup edgeGroup = new PermutationGroup(e);
        
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
