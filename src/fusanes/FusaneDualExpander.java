package fusanes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import model.Graph;

public class FusaneDualExpander {
    
    public static Graph expand(FusaneInnerDual dual) {
        Graph g = new Graph();
        expand(0, dual, g, new BitSet(g.getVertexCount()), -1);
        return g;
    }
    
    private static void expand(int v, FusaneInnerDual dual, Graph g, BitSet visited, int bondIndex) {
//        System.out.println("expanding " + v  + " @ " + bondIndex + " " + dual.getLabelsFor(v) + " " + visited + " " + g);
        int nV = g.getVertexCount();
        
        visited.set(v);
        List<Integer> expansionPoints;
        List<Integer> labels = dual.getLabelsFor(v);
        if (nV == 0) {
            expansionPoints = makeSixCycle(g, labels);          // initial cycle
        } else {
            expansionPoints = makeLoop(g, bondIndex, labels);
        }
        
        List<Integer> neighbours = dual.getConnected(v);
        int expIndex = 0;
        for (int i = 0; i < neighbours.size(); i++) {
            int n = neighbours.get(i);
            if (visited.get(n)) {
                continue;
            } else {
                int nextBondIndex = expansionPoints.get(expIndex);
                expand(n, dual, g, visited, nextBondIndex);
                expIndex++;
            }
        }
    }

    public static List<Integer> makeLoop(Graph g, int attachmentBondIndex, List<Integer> labels) {
        int a     = g.edges.get(attachmentBondIndex).a;
        int b     = g.edges.get(attachmentBondIndex).b;
        int start = Math.min(a, b);
        int end   = Math.max(a, b);
        int index = g.maxVertexIndex;
        int startBondIndex = g.edges.size();
        g.makeEdge(start, index + 1);
        g.makeEdge(index + 1, index + 2);
        g.makeEdge(index + 2, index + 3);
        g.makeEdge(index + 3, index + 4);
        g.makeEdge(index + 4, end);
        
        List<Integer> expansionPoints = new ArrayList<Integer>();
        int labelIndex = 0;
        int bondIndex = startBondIndex + 1;
        while (bondIndex < startBondIndex + 6 && labelIndex < labels.size()) {
            expansionPoints.add(bondIndex);
            int label = labels.get(labelIndex);
            bondIndex += label + 1;
            labelIndex++;
        }
//        System.out.println("expansion points = " + expansionPoints);
        return expansionPoints;
    }

    public static List<Integer> makeSixCycle(Graph g, List<Integer> labels) {
        g.makeEdge(0, 1);
        g.makeEdge(1, 2);
        g.makeEdge(2, 3);
        g.makeEdge(3, 4);
        g.makeEdge(4, 5);
        g.makeEdge(5, 0);
        
        List<Integer> expansionPoints = new ArrayList<Integer>();
        int labelIndex = 0;
        int bondIndex = 0;
        while (bondIndex < 6 && labelIndex < labels.size()) {
            expansionPoints.add(bondIndex);
            int label = labels.get(labelIndex);
            bondIndex += label + 1;
            labelIndex++;
        }
        return expansionPoints;
    }
    
}
