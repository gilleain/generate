package generate;

import graph.model.Graph;
import graph.model.GraphSignature;
import graph.model.IntGraph;
import graph.model.VertexSignature;
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

import signature.SymmetryClass;

public class SignatureGenerator {
    
    public class Orbit {
        
        public String signature;
        
        public SortedSet<Integer> vertexIndices;
       
        public int degree;
        
        public Orbit(String signature, int degree) {
            vertexIndices = new TreeSet<Integer>();
            this.signature = signature;
            this.degree = degree;
        }
        
        public boolean isUnsaturated(int maxDegree) {
            return degree < maxDegree;
        }
        
        public String toString() {
            return "[" + signature + " " + vertexIndices + "]";
        }
    }
    
    private int maxDegree = 4;
    
    /**
     * The target signature as a simple list of vertex signatures. It is
     * assumed that there is one for each vertex of the initial graph.
     */
    private VertexSignature[] hTau;
    
    private String[] hTauAsStrings;
    
    public List<IntGraph> enumerate(List<String> hTauAsStrings) {
        VertexSignature[] hTau = new VertexSignature[hTauAsStrings.size()];
        int i = 0;
        for (String signatureString : hTauAsStrings) {
            hTau[i] = VertexSignature.fromString(signatureString);
            i++;
        }
        return enumerate(hTau);
    }
    
    public List<IntGraph> enumerate(VertexSignature[] hTau) {
        IntGraph initial = new IntGraph(hTau.length);
        return enumerate(initial, hTau);
    }
    
    public List<IntGraph> enumerate(IntGraph initial, VertexSignature[] hTau) {
        this.hTau = hTau;
        hTauAsStrings = new String[hTau.length];
        for (int hTauIndex = 0; hTauIndex < hTau.length; hTauIndex++) {
            hTauAsStrings[hTauIndex] = hTau[hTauIndex].toCanonicalString();
        }
        List<IntGraph> s = new ArrayList<IntGraph>();
        enumerateMoleculeSig(initial, s);
        return s;
    }
    
    public List<IntGraph> enumerateMoleculeSig(IntGraph g, List<IntGraph> s) {
        if (connected(g) && signaturesEqualTarget(g)) {
            s.add(g);
            return s;
        }
        List<Orbit> orbits = partitionIntoOrbits(g);
        Orbit o = null;
        for (Orbit orbit : orbits) {
            if (orbit.isUnsaturated(maxDegree)) {
                o = orbit;
                break;
            }
        }
        
        List<IntGraph> sc = saturateOrbitSignature(o, g, new ArrayList<IntGraph>()); 
        for (IntGraph h : sc) {
            s.addAll(enumerateMoleculeSig(h, s));
        }
        return s;
    }

    public List<IntGraph> saturateOrbitSignature(Orbit o, IntGraph g, List<IntGraph> s) {
        if (o == null || o.vertexIndices.isEmpty()) {
            s.add(g);
            return s;
        }
        int x = o.vertexIndices.first();
        List<IntGraph> sv = saturateVertexSignature(x, g, new ArrayList<IntGraph>());
        o.vertexIndices.remove(x);
        for (IntGraph h : sv) {
            s.addAll(saturateOrbitSignature(o, h, s));
        }
        return s;
    }

    public List<IntGraph> saturateVertexSignature(int x, IntGraph g, List<IntGraph> s) {
        if (saturated(x, g)) {
            s.add(g);
            return s;
        }
        for (int y : getUnsaturated(g)) {
            IntGraph extended = g.makeNew(x, y);
            boolean compatibleXY = compatibleBondSig(x, y, g);
            boolean compatibleYX = compatibleBondSig(y, x, g);
            boolean canonical = isCanonical(g);
            boolean noSSGs = noSaturatedSubgraphs(g);
            if (compatibleXY && compatibleYX && canonical && noSSGs) {
                s.addAll(saturateVertexSignature(x, extended, s));
            }
        }
        
        return s;
    }

    public boolean noSaturatedSubgraphs(Graph g) {
        int n = g.getVertexCount();
        if (n == 0) return true;
        BitSet visited = new BitSet(n);
        int numberSaturatedInCurrentSubgraph = 0;
        int currentSubgraphSize = 0;
        Stack<Integer> toVisit = new Stack<Integer>();
        toVisit.push(0);
        int visitedCount = 0;
        while (visitedCount < n) {
            int i = toVisit.pop();
            visitedCount++;
            currentSubgraphSize++;
            if (visited.get(i)) {
                continue;
            } else {
                visited.set(i);
                if (saturated(i, g)) {
                    numberSaturatedInCurrentSubgraph++;
                }
                for (int j : g.getConnected(i)) {
                    if (!visited.get(j)) {
                        toVisit.push(j);
                    }
                }
            }
            if (toVisit.isEmpty()) {
                if (numberSaturatedInCurrentSubgraph == currentSubgraphSize) {
                    return false;
                }
                int nextStart = visited.nextClearBit(0);
                if (nextStart < n) {
                    toVisit.push(nextStart);
                    currentSubgraphSize = 0;
                    numberSaturatedInCurrentSubgraph++;
                }
            }
        }
        return numberSaturatedInCurrentSubgraph < currentSubgraphSize;
    }

    public boolean isCanonical(IntGraph g) {
        Partition orbits = getOrbitsAsPartition(g);
        return IntraOrbitLabeller.isCanonical(g, orbits);
    }
    
    public Partition getOrbitsAsPartition(IntGraph g) {
        Partition partition = new Partition();
        Map<String, Orbit> orbitMap = getOrbitMap(g);
        List<Orbit> sortedOrbits = new ArrayList<Orbit>();
        for (String signature : orbitMap.keySet()) {
            Orbit orbit = orbitMap.get(signature);
            sortedOrbits.add(orbit);
        }
        Collections.sort(sortedOrbits, new Comparator<Orbit>() {

            public int compare(Orbit o0, Orbit o1) {
                Integer f0 = o0.vertexIndices.first();
                Integer f1 = o1.vertexIndices.first();
                return f0.compareTo(f1);
            }
            
        });
        for (Orbit orbit : sortedOrbits) {
            partition.addCell(orbit.vertexIndices);
        }
        return partition;
    }

    public boolean compatibleBondSig(int x1, int x2, Graph g) {
        int n12 = 0;
        String x1Sig = getSig(x1);
        for (int y : getBonded(x1)) {
            if (getSig(x1, y).equals(x1Sig)) n12++;
        }
        if (n12 == 0) return false;
        int m12 = 0;
        String x2Sig = getSig(x2);
        for (int y : getBonded(x1, g)) {
            if (getSig(y).equals(x2Sig)) m12++;
        }
        return n12 - m12 >= 0;
    }
    
    public String getSig(int x) {
        return "";
    }
    
    public String getSig(int vertexIndex, int otherVertexIndex) {
        return "";
    }
    
    public List<Integer> getBonded(int vertexIndex) {
        List<Integer> bonded = new ArrayList<Integer>();
        return bonded;
    }
    
    public List<Integer> getBonded(int vertexIndex, Graph graph) {
        List<Integer> bonded = new ArrayList<Integer>();
        return bonded;
    }

    public List<Integer> getUnsaturated(IntGraph g) {
        List<Integer> unsaturated = new ArrayList<Integer>();
        int vertexCount = g.getVertexCount();
        Map<Integer, List<Integer>> table = g.getConnectionTable();
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
            if (table.get(vertexIndex).size() < maxDegree) {
                unsaturated.add(vertexIndex);
            }
        }
        return unsaturated;
    }

    public boolean saturated(int x, Graph g) {
        return g.getConnected(x).size() == maxDegree;
    }
    
    private Map<String, Orbit> getOrbitMap(IntGraph g) {
        Map<Integer, List<Integer>> cTable = g.getConnectionTable();
        GraphSignature graphSignature = new GraphSignature(g);
        Map<String, Orbit> orbitMap = new HashMap<String, Orbit>();
        int n = g.getVertexCount();
        for (int vIndex = 0; vIndex < n; vIndex++) {
            String signatureString;
            int degree = cTable.get(vIndex).size();
            if (degree == 0) {
                signatureString = hTauAsStrings[vIndex];
            } else {
                signatureString = graphSignature.signatureStringForVertex(vIndex);
            }
            if (orbitMap.containsKey(signatureString)) {
                orbitMap.get(signatureString).vertexIndices.add(vIndex);
            } else {
                Orbit o = new Orbit(signatureString, degree);
                o.vertexIndices.add(vIndex);
                orbitMap.put(signatureString, o);
            }
        }
        return orbitMap;
    }

    public List<Orbit> partitionIntoOrbits(IntGraph g) {
        List<Orbit> orbits = new ArrayList<Orbit>();
        Map<String, Orbit> orbitMap = getOrbitMap(g);
        for (String signature : orbitMap.keySet()) {
            orbits.add(orbitMap.get(signature));
        }
        // TODO : sort?
        return orbits;
    }

    public boolean signaturesEqualTarget(Graph g) {
        GraphSignature graphSig = new GraphSignature(g);
        List<SymmetryClass> symmetryClasses = graphSig.getSymmetryClasses();
        for (SymmetryClass symClass : symmetryClasses) {
            String signatureForClass = symClass.getSignatureString();
            for (int i : symClass) {
                if (hTauAsStrings[i].equals(signatureForClass)) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean connected(Graph g) {
        int n = g.getVertexCount();
        BitSet visited = new BitSet(n);
        Stack<Integer> next = new Stack<Integer>();
        next.push(0);
        while (!next.isEmpty()) {
            int i = next.pop();
            if (visited.get(i)) {
                continue;
            } else {
                visited.set(i);
                for (int j : g.getConnected(i)) {
                    if (!visited.get(j)) {
                        next.push(j);
                    }
                }
            }
        }
        return visited.cardinality() == n;
    }
}
