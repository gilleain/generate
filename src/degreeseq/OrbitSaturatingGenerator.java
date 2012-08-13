package degreeseq;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import group.Partition;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import model.Graph;
import model.GraphDiscretePartitionRefiner;

public class OrbitSaturatingGenerator {
    
    private List<Stack<Integer>> degreeOrbits;
    
    private GeneratorHandler handler;
    
    public OrbitSaturatingGenerator() {
        this(new SystemOutHandler());
    }
    
    public OrbitSaturatingGenerator(GeneratorHandler handler) {
        this.handler = handler;
    }
    
    public void generate(int[] degSeq) {
        degreeOrbits = getOrbits(degSeq);
        generate(0, getOrbits(degSeq), new Graph(), degSeq);
        handler.finish();
    }
    
    public void generate(
            int orbit, List<Stack<Integer>> orbits, Graph parent, int[] degSeq) {
//        System.out.println("Orbit " + orbit + " of " + orbits + "\t" + parent);
        List<Graph> children = new ArrayList<Graph>();
        saturateOrbit(orbit, orbits, parent, children, degSeq);
        for (Graph child : children) {
            List<Stack<Integer>> nextOrbits = getOrbits(child, degSeq);
            int unsaturatedOrbit = getFirstUnsaturatedOrbit(child, nextOrbits, degSeq);
//            System.out.println(child + "\t" + nextOrbits + "\t" + unsaturatedOrbit);
            BitSet component = new BitSet();
            findComponent(child, 0, component);
            boolean isSSG = isSSG(component, child, degSeq); 
            if (isSSG) {
                if (component.cardinality() == degSeq.length) {
//                    if (isCanonical(child, degreeOrbits)) {
                    if (isPartitionCanonical(child, degreeOrbits)) {
//                    System.out.println("complete " + child 
//                                    + "\t" + child.esize()
//                                    + "\t" + getCert(child, new Permutation(child.vsize()))
////                                    + "\t" + isCanonical(child, degreeOrbits)
//                                    + "\t" + isPartitionCanonical(child, degreeOrbits)
//                    );
                        handler.handle(parent, child);
                    }
                } else {
//                    System.out.println("SSG " + child + "\t" + child.esize());
                }
            } else {
                generate(unsaturatedOrbit, nextOrbits, child, degSeq);
            }
        }
    }
    
    public void saturateOrbit(
            int orbitIndex, List<Stack<Integer>> orbits, Graph g, List<Graph> children, int[] degSeq) {
        Stack<Integer> orbit = orbits.get(orbitIndex); 
        if (orbit.isEmpty()) {
            children.add(g);
        } else {
//            System.out.println("Orbit " + orbitIndex + " now " + orbit);
            int v = orbit.pop();
            List<Graph> vchildren = new ArrayList<Graph>();
//            System.out.println("saturating vertex " + v);
            saturateVertex(v, v + 1, g, vchildren, orbits, degSeq);
            for (Graph h : vchildren) {
                saturateOrbit(orbitIndex, orbits, h, children, degSeq);
            }
        }
    }

    public void saturateVertex(int v, int start, Graph g, 
            List<Graph> children, List<Stack<Integer>> orbits, int[] degSeq) {
//        if (!isCanonical(g, orbits)) return;
        int degree = g.degree(v);
        if (degSeq[v] == degree) {
            children.add(g);
            return;
        } else {
            for (int w = start; w < degSeq.length; w++) {
                if (g.degree(w) < degSeq[w]) {
                    Graph h = g.makeNew(v, w);
                    saturateVertex(v, w + 1, h, children, orbits, degSeq);
                }
            }
        }
    }
    
    private boolean isPartitionCanonical(Graph g, List<Stack<Integer>> orbits) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        Partition p  = new Partition();
        for (Stack<Integer> orbit : orbits) {
            SortedSet<Integer> cell = new TreeSet<Integer>(orbit);
            p.addCell(cell);
        }
        refiner.getAutomorphismGroup(g, p);
        return refiner.firstIsIdentity();
    }
    
    // TODO : remove (curently used for debugging)
//    private String getCert(Graph g, Permutation p) {
//        StringBuffer cert = new StringBuffer();
//        int n = g.vsize();
//        for (int i = 0; i < n; i++) {
//            for (int j = i + 1; j < n; j++) {
//                if (g.hasEdge(p.get(i), p.get(j))) {
//                    cert.append("1");
//                } else {
//                    cert.append("0");
//                }
//            }
//        }
//        return cert.toString();
//    }

    private boolean isSSG(BitSet component, Graph g, int[] degSeq) {
        for (int i = 0; i < degSeq.length; i++) {
            if (component.get(i) && g.degree(i) < degSeq[i]) {
                return false;
            }
        }
        return true;
    }
    
    private void findComponent(Graph g, int i, BitSet seen) {
        if (seen.get(i)) {
            return;
        } else {
            seen.set(i);
            for (int n : g.getConnected(i)) {
                findComponent(g, n, seen);
            }
        }
    }
    
    private int getFirstUnsaturatedOrbit(Graph g, List<Stack<Integer>> orbits, int[] degSeq) {
        int orbitIndex = 0;
        while (orbitIndex < orbits.size()) {
            Stack<Integer> orbit = orbits.get(orbitIndex);
            int rep = orbit.get(0);
            if (g.degree(rep) < degSeq[rep]) {
                return orbitIndex;
            }
            orbitIndex++;
        }
        return orbitIndex;
    }
    
    private List<Stack<Integer>> getOrbits(Graph g, int[] degSeq) {
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
        Map<Integer, Stack<Integer>> orbitMap = new HashMap<Integer, Stack<Integer>>();
        for (int i = 0; i < n; i++) {
            int l = labels[i];
            Stack<Integer> orbit;
            if (orbitMap.containsKey(l)) {
                orbit = orbitMap.get(l);
            } else {
                orbit = new Stack<Integer>();
                orbitMap.put(l, orbit);
            }
            orbit.add(0, new Integer(i));
        }
        
        // split the orbits using the target degree sequence
        List<Stack<Integer>> orbits = new ArrayList<Stack<Integer>>();
        for (Stack<Integer> orbit : orbitMap.values()) {
            // UGH!
            Map<Integer, Stack<Integer>> degreeMap = new HashMap<Integer, Stack<Integer>>();
            for (int i : orbit) {
                int d = degSeq[i];
                Stack<Integer> subOrbit;
                if (degreeMap.containsKey(d)) {
                    subOrbit = degreeMap.get(d);
                } else {
                    subOrbit = new Stack<Integer>();
                    degreeMap.put(d, subOrbit);
                }
                subOrbit.add(i);
            }
            orbits.addAll(degreeMap.values());
        }
        
        Collections.sort(orbits, new Comparator<Stack<Integer>>() {

            @Override
            public int compare(Stack<Integer> a, Stack<Integer> b) {
                return a.get(a.size() - 1).compareTo(b.get(b.size() - 1));
            }
            
        });
        
        return orbits;
    }

    private List<Stack<Integer>> getOrbits(int[] degSeq) {
        List<Stack<Integer>> orbits = new ArrayList<Stack<Integer>>();
        Stack<Integer> currentOrbit = null;
        for (int i = 0; i < degSeq.length; i++) {
            if (i == 0) {
                currentOrbit = new Stack<Integer>();
                currentOrbit.add(0);
                orbits.add(currentOrbit);
            } else {
                if (degSeq[i] != degSeq[i - 1]) {
                    currentOrbit = new Stack<Integer>();
                    orbits.add(currentOrbit);
                }
                currentOrbit.add(0, new Integer(i));
            }
        }
        return orbits;
    }
}
