package coloring;

import group.Permutation;
import group.SSPermutationGroup;

import java.util.ArrayList;
import java.util.List;

import model.Edge;
import model.Graph;
import model.GraphDiscretePartitionRefiner;

public class AugmentingEdgeColorer implements EdgeColorer {
    
    public List<Graph> color(Graph graph) {
        List<Graph> coloredGraphs = new ArrayList<Graph>();
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        SSPermutationGroup vGroup = refiner.getAutomorphismGroup(graph);
        SSPermutationGroup eGroup = VertexToEdgePermutationConverter.convert(graph, vGroup);
        color(graph, coloredGraphs, eGroup);
        return coloredGraphs;
    }
    
    public void color(Graph graph, List<Graph> coloredGraphs, SSPermutationGroup group) {
        if (valid(graph, group)) {
            coloredGraphs.add(graph);
            int next = getMax(graph) + 1;
            for (; next < graph.edges.size(); next++) {
                if (allowed(graph, next)) {
                    Graph child = new Graph(graph);
                    child.edges.get(next).o = 2;
                    color(child, coloredGraphs, group);
                }
            }
        }
    }
    
    private boolean allowed(Graph g, int next) {
        Edge eI = g.edges.get(next);
        for (int j = 0; j < g.edges.size(); j++) {
            Edge eJ = g.edges.get(j);
            if (eJ.o > 1 && eI.adjacent(eJ)) {
                return false;
            }
        }
        return true;
    }

    private int getMax(Graph graph) {
        int max = -1;
        int eIndex = 0;
        for (Edge e : graph.edges) {
            if (e.o > 1) {
                max = eIndex;
            }
            eIndex++;
        }
        return max;
    }

    public boolean valid(Graph graph, SSPermutationGroup group) {
        String initial = getColorString(graph, new Permutation(graph.edges.size()));
        for (Permutation p : group.all()) {
            String permStr = getColorString(graph, p);
            if (permStr.compareTo(initial) > 0) {
//                System.out.println(permStr + " > " + initial + " " + graph.getSortedEdgeStringWithEdgeOrder());
                return false;
            } else {
//                System.out.println(permStr + " < " + initial + " " + graph.getSortedEdgeStringWithEdgeOrder());
            }
        }
//        System.out.println("accepted " + initial + "\t" + graph.getSortedEdgeStringWithEdgeOrder());
        return true;
    }
    
    private String getColorString(Graph graph, Permutation p) {
        StringBuffer buffer = new StringBuffer();
        for (int edgeIndex = 0; edgeIndex < graph.edges.size(); edgeIndex++) {
            Edge e = graph.edges.get(p.get(edgeIndex));
            buffer.append(e.o);
        }
        return buffer.toString();
    }
    
}
