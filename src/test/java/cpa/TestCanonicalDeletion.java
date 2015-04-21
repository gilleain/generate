package cpa;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntEdge;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;

public class TestCanonicalDeletion {
    
    private void delete(String graphString) {
        IntGraph g = new IntGraph(graphString);
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autG = refiner.getAutomorphismGroup(g);
        Permutation best = refiner.getBest();
        int chosen = best.get(g.getVertexCount() - 1);
        IntGraph h = new IntGraph();
        for (IntEdge e : g.edges) {
            if (e.a == chosen || e.b == chosen) {
                continue;
            } else {
                h.makeEdge(e.a, e.b);
            }
        }
        System.out.println(h);
    }
    
    @Test
    public void testA() {
        delete("0:4, 0:7, 1:5, 1:8, 2:6, 2:7, 3:6, 3:8, 4:7, 5:8");
    }
    
    @Test
    public void testB() {
        delete("0:3, 0:5, 0:7, 1:4, 1:6, 1:8, 2:7, 2:8, 3:5, 3:7, 4:6, 4:8, 5:7, 6:8");
    }

}
