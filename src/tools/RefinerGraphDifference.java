package tools;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.GraphFileDiff.GraphDifference;

public class RefinerGraphDifference implements GraphDifference {
    
    private Map<BigInteger, Graph> graphsA;
    
    public RefinerGraphDifference() {
        this.graphsA = new HashMap<BigInteger, Graph>();
    }

    @Override
    public void add(Graph graph) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.getAutomorphismGroup(graph);
        graphsA.put(refiner.getCertificate(), graph);
    }

    @Override
    public void compare(Graph graph, List<Graph> difference) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.getAutomorphismGroup(graph);
        BigInteger cert = refiner.calculateCertificate(refiner.getBest());
        if (graphsA.containsKey(cert)) {
            Graph graphA = graphsA.get(cert);
            System.out.println(cert + " " + graphA + " = " + graph);
        } else {
            difference.add(graph);
        }
    }
    
}
