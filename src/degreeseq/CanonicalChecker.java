package degreeseq;

import group.Partition;
import model.Edge;
import model.Graph;
import model.GraphDiscretePartitionRefiner;

public class CanonicalChecker {
    
    public static boolean isPartitionCanonical(Graph g, Partition p, int[] originalDegSeq) {
        if (g.vsize() < 3) return true;
        
//        System.out.println(g + "\t" + p);
        int n = originalDegSeq.length;
        int[] vertexMap = new int[n];
        int counter = 0;
        for (int i = 0; i < n; i++) {
            int d = g.degree(i);
            if (d == 0) {
                vertexMap[i] = -1;
            } else {
                vertexMap[i] = counter;
                counter++;
            }
        }
//        System.out.println(java.util.Arrays.toString(vertexMap));
        int[] deg = new int[counter];
        counter = 0;
        for (int i = 0; i < n; i++) {
            if (vertexMap[i] == -1) {
                continue;
            } else {
                deg[counter] = originalDegSeq[i];
                counter++;
            }
        }
//        System.out.println(java.util.Arrays.toString(deg));
        Graph h = new Graph();
        for (Edge e : g.edges) {
            h.makeEdge(vertexMap[e.a], vertexMap[e.b]);
        }
//        Partition q = new SignatureOrbitPartitioner().getOrbitPartition(g, deg);
//        Partition q = getOrbits(deg);
//        System.out.println("Red " + h + "\t" + q);
        
        boolean useColorsInEq = false;
        boolean checkDiscon = false;
        GraphDiscretePartitionRefiner refiner = 
            new GraphDiscretePartitionRefiner(useColorsInEq, checkDiscon);
        
//        refiner.getAutomorphismGroup(g, p);
//        return refiner.firstIsIdentity();
//        System.out.print("checking " + g + "\t" + p);
        try {
            refiner.setup(h);
//            refiner.refine(q);
            refiner.refine(p);
        } catch (Exception e) {
            System.out.println("Error");
            return true;
        }
//        return refiner.getFirst().isIdentity() || 
//        System.out.println(g.getSortedEdgeString() + "\t" + refiner.firstIsIdentity());
//        System.out.println(g + "\t" + refiner.firstIsIdentity());
//        System.out.println("\t" + refiner.firstIsIdentity());
        if (refiner.firstIsIdentity()) {
            return true;
        } else {
            System.out.println("Fail " + h);
            return false;
        }
//        if (refiner.isCanonical(g, p)) {
////            System.out.println("T " + g + "\t" + p);
//            return true;
//        } else {
//            return false;
//        }
    }
    
    private static Partition getOrbits(int[] degSeq) {
        Partition orbits = new Partition();
        int currentOrbitIndex = 0;
        for (int i = 0; i < degSeq.length; i++) {
            if (i == 0) {
                orbits.addSingletonCell(i);
            } else {
                if (degSeq[i] != degSeq[i - 1]) {
                    currentOrbitIndex++;
                    orbits.addSingletonCell(i);
                } else {
                    orbits.addToCell(currentOrbitIndex, i);
                }
            }
        }
        return orbits;
    }
    
}
