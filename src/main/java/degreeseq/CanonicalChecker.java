package degreeseq;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Partition;

public class CanonicalChecker {
    
    public static boolean isPartitionCanonical(IntGraph g, Partition p, int[] originalDegSeq) {
        if (g.vsize() < 3) return true;
        
//        System.out.println(g + "\t" + p);
//        int n = originalDegSeq.length;
//        int[] vertexMap = new int[n];
//        int counter = 0;
//        for (int i = 0; i < n; i++) {
//            int d = g.degree(i);
//            if (d == 0) {
//                vertexMap[i] = -1;
//            } else {
//                vertexMap[i] = counter;
//                counter++;
//            }
//        }
//        int[] deg = new int[counter];
//        counter = 0;
//        for (int i = 0; i < n; i++) {
//            if (vertexMap[i] == -1) {
//                continue;
//            } else {
//                deg[counter] = originalDegSeq[i];
//                counter++;
//            }
//        }
//        Graph h = new Graph();
//        for (Edge e : g.edges) {
//            h.makeEdge(vertexMap[e.a], vertexMap[e.b]);
//        }
//
//        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
//        
//        try {
//            refiner.setup(h);
////            refiner.refine(q);
//            refiner.refine(p);
//        } catch (Exception e) {
//            System.out.println("Error " + e.getStackTrace()[0]);
//            return true;
//        }
//
//        if (refiner.firstIsIdentity()) {
//            return true;
//        } else {
//            System.out.println("Fail " + h);
//            return false;
//        }
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        System.out.println("Checking " + g + " " + p);
        return refiner.isCanonical(g, p);
    }
    
}
