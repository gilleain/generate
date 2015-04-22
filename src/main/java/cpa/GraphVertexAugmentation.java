package cpa;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntEdge;
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
        return canon1(best, autH);
//        return canon2(best, autH);
    }
    
    private boolean canon1(Permutation best, PermutationGroup autH) {
//        int chosen = best.get(augmentedGraph.getVertexCount() - 1);
        int chosen = getChosen(new IntGraph(augmentedGraph), best);
        List<Integer> connected = augmentedGraph.getConnected(chosen);
        return connected.size() == verticesToAddTo.size() && 
               inOrbit(connected, verticesToAddTo, autH);
    }
    
    private boolean canon2(Permutation best, PermutationGroup autH) {
        IntGraph canonical = augmentedGraph.getPermutedGraph(best);
        int chosen = getChosen(canonical, best);
        List<Integer> connected = canonical.getConnected(chosen);
        Set<Integer> toFind = permute(best, verticesToAddTo);
        return connected.size() == verticesToAddTo.size() && 
               inOrbit(connected, toFind, autH);
    }
    
    private Set<Integer> permute(Permutation p, Set<Integer> set) {
        Set<Integer> pSet = new HashSet<Integer>();
        for (Integer i : set) {
            pSet.add(p.get(i));
        }
        return pSet;
    }
    
    private int getChosen(IntGraph graph, Permutation p) {
//        Permutation inv = p.invert();
        Permutation inv = p;
        List<Integer> cutVertices = CutVertexCalculator.getCutVertices(graph);
        
        int n = graph.getVertexCount() - 1;
        int choice;
        for (choice = n; choice >= 0; choice--) {
            int pChoice = inv.get(choice);
            if (cutVertices.contains(pChoice)) {
                continue;
            } else {
                return pChoice;
            }
        }
        System.out.println("hobson for " + graph);
        // TODO : should never happen? only graph with all bridge edges is 2-line?
        return inv.get(choice);
    }
    
    private boolean inOrbit(List<Integer> set, Set<Integer> toFind,  PermutationGroup autH) {
        SetOrbit orbit = new BruteForcer().getInOrbit(set, autH);
        return orbit.contains(toFind);
    }
}
