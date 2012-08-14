package degreeseq;

import group.Partition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import model.Graph;

public class MorganNumberPartitioner implements OrbitPartitioner {
    
    @Override
    public Partition getOrbitPartition(Graph g, int[] degSeq) {
        int n = g.getVertexCount();
        
        // use Morgan numbers to partition vertices
        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            int d = g.degree(i);
            labels[i] = d;
        }
        for (int round = 0; round < n; round++) {
            int[] newLabels = new int[n];
            for (int i = 0; i < n; i++) {
                for (int j : g.getConnected(i)) {
                    newLabels[i] += labels[j];
                }
            }
            labels = newLabels;
        }
        //        System.out.println("labels for " + g + " = " + Arrays.toString(labels));
        
        // convert these labels into orbits
        Map<Integer, List<Integer>> orbitMap = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < n; i++) {
            int l = labels[i];
            List<Integer> orbit;
            if (orbitMap.containsKey(l)) {
                orbit = orbitMap.get(l);
            } else {
                orbit = new ArrayList<Integer>();
                orbitMap.put(l, orbit);
            }
            orbit.add(i);
        }
        
        // split the orbits using the target degree sequence
        Partition orbits = new Partition();
        for (List<Integer> orbit : orbitMap.values()) {
            // UGH!
            Map<Integer, SortedSet<Integer>> degreeMap = new HashMap<Integer, SortedSet<Integer>>();
            for (int i : orbit) {
                int d = degSeq[i];
                SortedSet<Integer> subOrbit;
                if (degreeMap.containsKey(d)) {
                    subOrbit = degreeMap.get(d);
                } else {
                    subOrbit = new TreeSet<Integer>();
                    degreeMap.put(d, subOrbit);
                }
                subOrbit.add(i);
            }
            for (SortedSet<Integer> cell : degreeMap.values()) {
                orbits.addCell(cell);
            }
        }
        orbits.order();
        return orbits;
    }
    
}
