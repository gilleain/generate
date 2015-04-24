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
import util.CutVertexCalculator;

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
        int chosen = getChosen(new IntGraph(augmentedGraph), best);
        List<Integer> connected = augmentedGraph.getConnected(chosen);
        return connected.size() == verticesToAddTo.size() && 
               inOrbit(connected, verticesToAddTo, autH);
    }
   
    private int getChosen(IntGraph graph, Permutation p) {
        List<Integer> cutVertices = CutVertexCalculator.getCutVertices(graph);
        
        int n = graph.getVertexCount() - 1;
        int choice;
        for (choice = n; choice >= 0; choice--) {
            int pChoice = p.get(choice);
            if (cutVertices.contains(pChoice)) {
                continue;
            } else {
                return pChoice;
            }
        }
        // TODO : should never happen? only graph with all bridge edges is 2-line?
        return p.get(choice);
    }
    
    private boolean inOrbit(List<Integer> set, Set<Integer> toFind, PermutationGroup autH) {
        SetOrbit orbit = new BruteForcer().getInOrbit(set, autH);
        return orbit.contains(toFind);
    }
}
