package coloring;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntEdge;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.List;

public class AugmentingEdgeColorer implements EdgeColorer {
    
    public List<IntGraph> color(IntGraph graph) {
        List<IntGraph> coloredGraphs = new ArrayList<IntGraph>();
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup vGroup = refiner.getAutomorphismGroup(graph);
        PermutationGroup eGroup = VertexToEdgePermutationConverter.convert(graph, vGroup);
        color(graph, coloredGraphs, eGroup);
        return coloredGraphs;
    }
    
    public void color(IntGraph graph, List<IntGraph> coloredGraphs, PermutationGroup group) {
        if (valid(graph, group)) {
            coloredGraphs.add(graph);
            int next = getMax(graph) + 1;
            for (; next < graph.edges.size(); next++) {
                if (allowed(graph, next)) {
                    IntGraph child = new IntGraph(graph);
                    child.edges.get(next).o = 2;
                    color(child, coloredGraphs, group);
                }
            }
        }
    }
    
    private boolean allowed(IntGraph g, int next) {
        IntEdge eI = g.edges.get(next);
        for (int j = 0; j < g.edges.size(); j++) {
            IntEdge eJ = g.edges.get(j);
            if (eJ.o > 1 && eI.adjacent(eJ)) {
                return false;
            }
        }
        return true;
    }

    private int getMax(IntGraph graph) {
        int max = -1;
        int eIndex = 0;
        for (IntEdge e : graph.edges) {
            if (e.o > 1) {
                max = eIndex;
            }
            eIndex++;
        }
        return max;
    }

    public boolean valid(IntGraph graph, PermutationGroup group) {
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
    
    private String getColorString(IntGraph graph, Permutation p) {
        StringBuffer buffer = new StringBuffer();
        for (int edgeIndex = 0; edgeIndex < graph.edges.size(); edgeIndex++) {
            IntEdge e = graph.edges.get(p.get(edgeIndex));
            buffer.append(e.o);
        }
        return buffer.toString();
    }
    
}
