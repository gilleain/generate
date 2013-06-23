package degreeseq;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;
import graph.model.Graph;
import group.Partition;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.SortedSet;

public class OrbitSaturatingGenerator {
    
    private int[] originalDegSeq;   // UGH
    
    private Partition degreeOrbits;
    
    private GeneratorHandler handler;
    
    private OrbitPartitioner partitioner;
    
    public OrbitSaturatingGenerator() {
        this(new SystemOutHandler());
    }
    
    public OrbitSaturatingGenerator(GeneratorHandler handler) {
        this.handler = handler;
//        this.partitioner = new MorganNumberPartitioner();
        this.partitioner = new SignatureOrbitPartitioner();
    }
    
    public void generate(int[] degSeq) {
        originalDegSeq = degSeq;
        degreeOrbits = getOrbits(degSeq);
        generate(0, getOrbits(degSeq), new Graph(), degSeq);
        handler.finish();
    }
    
    public void generate(int orbit, Partition orbits, Graph parent, int[] degSeq) {
//        System.out.println("Orbit " + orbit + " of " + orbits + "\t" + parent);
        List<Graph> children = new ArrayList<Graph>();
        saturateOrbit(orbit, orbits, parent, children, degSeq);
        for (Graph child : children) {
            Partition nextOrbits = partitioner.getOrbitPartition(child, degSeq);
            int unsaturatedOrbit = getFirstUnsaturatedOrbit(child, nextOrbits, degSeq);
//            System.out.println(child + "\t" + nextOrbits + "\t" + unsaturatedOrbit);
            BitSet component = new BitSet();
            findComponent(child, 0, component);
            boolean isSSG = isSSG(component, child, degSeq); 
            if (isSSG) {
                if (component.cardinality() == degSeq.length) {
//                    if (isCanonical(child, degreeOrbits)) {
//                    System.out.println("complete " + child 
//                                    + "\t" + child.esize()
//                                    + "\t" + getCert(child, new Permutation(child.vsize()))
//                                    + "\t" + isCanonical(child, degreeOrbits)
//                                    + "\t" + isPartitionCanonical(child, degreeOrbits)
//                    );
                    if (CanonicalChecker.isPartitionCanonical(child, degreeOrbits, originalDegSeq)) {
                        handler.handle(parent, child);
                    }
                } else {
//                    System.out.println("SSG " + child + "\t" + child.esize());
                }
            } else {
                if (!CanonicalChecker.isPartitionCanonical(child, degreeOrbits, originalDegSeq)) continue;
                generate(unsaturatedOrbit, nextOrbits, child, degSeq);
            }
        }
    }
    
    public void saturateOrbit(int orbitIndex, Partition orbits, Graph g, List<Graph> children, int[] degSeq) {
        SortedSet<Integer> orbit = orbits.getCell(orbitIndex); 
        if (orbit.isEmpty()) {
            children.add(g);
        } else {
//            System.out.println("Orbit " + orbitIndex + " now " + orbit);
            int v = pop(orbit);
            List<Graph> vchildren = new ArrayList<Graph>();
//            System.out.println("saturating vertex " + v);
            saturateVertex(v, v + 1, g, vchildren, orbits, degSeq);
            for (Graph h : vchildren) {
                saturateOrbit(orbitIndex, orbits, h, children, degSeq);
            }
        }
    }
    
    private int pop(SortedSet<Integer> orbit) {
        int item = orbit.first();
        orbit.remove(item);
        return item;
    }

    public void saturateVertex(int v, int start, Graph g, List<Graph> children, Partition orbits, int[] degSeq) {
//        if (!isPartitionCanonical(g, degreeOrbits)) return;
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
    
    private int getFirstUnsaturatedOrbit(Graph g, Partition orbits, int[] degSeq) {
        int orbitIndex = 0;
        while (orbitIndex < orbits.size()) {
            SortedSet<Integer> orbit = orbits.getCell(orbitIndex);
            int rep = orbit.first();
            if (g.degree(rep) < degSeq[rep]) {
                return orbitIndex;
            }
            orbitIndex++;
        }
        return orbitIndex;
    }
    
    private Partition getOrbits(int[] degSeq) {
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
