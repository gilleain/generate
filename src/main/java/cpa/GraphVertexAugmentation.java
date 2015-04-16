package cpa;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import setorbit.BruteForcer;
import setorbit.SetOrbit;

/**
 * Augment a graph by a vertex, added to a set of existing vertices. 
 * 
 * @author maclean
 *
 */
public class GraphVertexAugmentation implements Augmentation<IntGraph> {
    
    private IntGraph augmentedGraph;
    
    private Set<Integer> verticesToAddTo;
    
    public GraphVertexAugmentation(IntGraph graph, int... verticesToAddToArray) {
        this.verticesToAddTo = new HashSet<Integer>();
        for (int vertexIndex : verticesToAddToArray) {
            this.verticesToAddTo.add(vertexIndex);
        }
        augmentedGraph = make(graph, verticesToAddTo);
    }
    
    public GraphVertexAugmentation(IntGraph graph, Set<Integer> verticesToAddTo) {
        this.verticesToAddTo = verticesToAddTo;
        augmentedGraph = make(graph, verticesToAddTo);
    }
    
    private IntGraph make(IntGraph g, Set<Integer> verticesToAddTo) {
        IntGraph h = new IntGraph(g);
        int w = g.getVertexCount();
        for (int v : verticesToAddTo) {
            h.makeEdge(v, w);
        }
        return h;
    }

    @Override
    public IntGraph getAugmentedObject() {
        return augmentedGraph;
    }

    @Override
    public boolean isCanonical() {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autH = refiner.getAutomorphismGroup(augmentedGraph);
        Permutation best = refiner.getBest();
        int chosen = best.get(augmentedGraph.getVertexCount() - 1);
        List<Integer> connected = augmentedGraph.getConnected(chosen);
        return connected.size() == verticesToAddTo.size() && inOrbit(connected, autH);
    }
    
    private boolean inOrbit(List<Integer> set, PermutationGroup autH) {
        SetOrbit orbit = new BruteForcer().getInOrbit(set, autH);
        return orbit.contains(verticesToAddTo);
    }
}
