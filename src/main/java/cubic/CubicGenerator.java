package cubic;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Edge;
import graph.model.Graph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.List;

public class CubicGenerator {
    
    private GeneratorHandler handler;
    
    public CubicGenerator() {
        this(new SystemOutHandler());
    }
    
    public CubicGenerator(GeneratorHandler handler) {
        this.handler = handler;
    }
    
    
    public void generate(int n) {
        
    }
    
    public void generate(Graph prime, int n) {
        List<EdgePair> reps = getEdgePairReps(prime);
        System.out.println(reps);
        for (EdgePair pair : reps) {
            extend(prime, pair, n);
        }
    }
    
    public void extend(Graph parent, EdgePair pair, int n) {
        int m = parent.vsize();
        Graph extended = new Graph(parent);
        extended.edges.remove(pair.f);
        extended.edges.remove(pair.s);
        extended.makeEdge(pair.f.a, m);
        extended.makeEdge(pair.f.b, m);
        extended.makeEdge(pair.s.a, m + 1);
        extended.makeEdge(pair.s.b, m + 1);
        extended.makeEdge(m, m + 1);
        System.out.println("Extension " + pair + "\t" + extended.getSortedEdgeString());
        handler.handle(parent, extended);
    }

    private List<EdgePair> getEdgePairReps(Graph prime) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autGroup = refiner.getAutomorphismGroup(prime);
        List<EdgePair> edgePairs = new ArrayList<EdgePair>(); 
        for (int i = 0; i < prime.esize(); i++) {
            Edge f = prime.edges.get(i);
            for (int j = i + 1; j < prime.esize(); j++) {
                Edge s = prime.edges.get(j);
                edgePairs.add(new EdgePair(f, s));
            }
        }
//        System.out.println(edgePairs);
        
        List<EdgePair> reps = new ArrayList<EdgePair>();
        for (int i = 0; i < edgePairs.size(); i++) {
            EdgePair p = edgePairs.get(i);
            if (isRep(p, reps, autGroup)) {
                reps.add(p);
            }
        }
        
        return reps;
    }
    
    private boolean isRep(EdgePair p, List<EdgePair> reps, PermutationGroup autGroup) {
        for (Permutation pi : autGroup.all()) {
            EdgePair permutedPair = p.permute(pi);
            if (reps.contains(permutedPair)) {
                return false;
            }
        }
        return true;
    }
    
}
