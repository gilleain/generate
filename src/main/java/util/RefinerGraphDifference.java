package util;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class RefinerGraphDifference implements GraphDifference {
    
    private Map<BigInteger, IntGraph> graphs;
    
    public RefinerGraphDifference() {
        this.graphs = new HashMap<BigInteger, IntGraph>();
    }

    @Override
    public void add(IntGraph graph) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.getAutomorphismGroup(graph);
        graphs.put(refiner.getCertificate(), graph);
    }

    @Override
    public void compare(IntGraph otherGraph, GraphDifference.Callback callback) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.getAutomorphismGroup(otherGraph);
        BigInteger cert = refiner.calculateCertificate(refiner.getBest());
        if (graphs.containsKey(cert)) {
            IntGraph graph = graphs.get(cert);
//            System.out.println(cert + " " + graph + " = " + otherGraph);
            callback.same(graph, otherGraph);
        } else {
            callback.different(null, otherGraph);   // TODO!
        }
    }
    
}
